package org.study.grabyou.service;

import com.alibaba.fastjson.JSON;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.grabyou.dao.RedisDao;
import org.study.grabyou.entity.Status;
import org.study.grabyou.entity.impl.LoginStatus;
import org.study.grabyou.entity.impl.RegisterStatus;
import org.study.grabyou.entity.User;
import org.study.grabyou.enums.EventType;
import org.study.grabyou.enums.MessageEnum;
import org.study.grabyou.mapper.UserDao;
import org.study.grabyou.service.Impl.ApplyServiceImp;
import org.study.grabyou.service.Impl.RiskControllService;
import org.study.grabyou.utils.ServletUtil;

@Service
public class UserAccessService {

  @Autowired
  private UserDao userDao;
  @Autowired
  private RedisDao redisDao;
  @Autowired
  private RiskControllService riskControllService;
  @Autowired
  private ApplyService applyService;


  /**
   * 根据用户名、密码、手机号、验证码，返回注册状态
   *
   * @param username 用户名
   * @param password 密码
   * @param phone    手机号
   * @param code     验证码
   * @return 注册状态
   */
  public Status userRegister(String username, String password, String phone,
      String code){
    String ip = ServletUtil.getIp();
    String device = ServletUtil.getDeviceID();
    //  String device = ServletUtil.getFullDeviceID();
    User user = new User(phone, username, password);
    RegisterStatus status = new RegisterStatus();
    // 1 - DecisionType
    int decisiontype = riskControllService.analysis(username, EventType.REGISTER, ip, device, phone);
    judgeDecisionType(decisiontype, status);
    // 风控直接停止下一步操作，更新情况
    if(decisiontype == 3){
      ServletUtil.getSession().invalidate();
    }
    if(decisiontype > 0){
      return status;
    }
    // 2 - 生成message
    // 2.1 判断是否存在用户名相同的人
    judgeUserByName(user, status);
    // 2.2 判断是否存在手机相同的人
    judgeUserByPhone(user, status);
    // 2.3 判断验证码问题
    applyService.verifyCode(phone, ip, device, code, status);
    // 3 - sessionID & ExpireTime
    HttpSession httpSession = ServletUtil.getSession();
    String sessionID = httpSession.getId();
    status.setSessionID(sessionID);
    // 4 - 尝试对用户进行注册
    insertUser(user, status);
    // 注册成功，则写入缓存
    if(status.getCode() == 1){
      redisDao.saveKeyValue(sessionID, JSON.toJSONString(user), 5, TimeUnit.MINUTES);
      status.setExpireTime(60*5);
    }
    return status;
  }


  /**
   * 根据用户名判断是否存在相同用户名的人
   * 存在，则注册状态更改为注册失败，并添加错误信息
   *
   * @param user 用户
   * @param status 注册状态
   * @return
   */
  public Status judgeUserByName(User user, Status status) {
    // 根据用户名搜索用户，判断是否有相同的用户名
    User searchUser = new User();
    searchUser.setUsername(user.getUsername());
    Object res = userDao.selectUserByNameOrPhone(searchUser);
    if (res != null) {
      status.setErrorMessage(MessageEnum.SameUsername.getMessage());
    }
    return status;
  }

  /**
   * 根据用户手机号判断是否存在相同手机号的人
   * 存在，则注册状态更改为注册失败，并添加错误信息
   *
   * @param user 用户
   * @param status 注册状态
   * @return 注册状态
   */
  public Status judgeUserByPhone(User user, Status status) {
    // 根据手机号搜索用户，判断是否有相同的手机号
    User searchUser = new User();
    searchUser.setPhoneNumber(user.getPhoneNumber());
    Object res = userDao.selectUserByNameOrPhone(searchUser);
    if (res != null) {
      status.setErrorMessage(MessageEnum.SamePhone.getMessage());
    }
    return status;
  }

  /**
   * 根据判断指标来更新注册状态
   *
   * @param decisiontype
   * @param status
   * @return
   */
  public Status judgeDecisionType(int decisiontype, Status status) {
    status.setDecisionType(decisiontype);
    switch (decisiontype) {
      case 0:
        break;
      case 1:
        status.setErrorMessage(MessageEnum.RiskMessge1.getMessage());
        break;
      case 2:
        status.setErrorMessage(MessageEnum.RiskMessage2.getMessage());
        break;
      case 3:
        status.setErrorMessage(MessageEnum.RiskMessage3.getMessage());
        break;
      default:
        status.setErrorMessage(MessageEnum.Wrong.getMessage());
        break;
    }
    return status;
  }

  /**
   * 如果 status.code == 1，尝试进行用户注册。 如果注册失败，则将注册状态标记失败。
   *
   * @param user   待注册的用户
   * @param status 注册状态
   * @return 注册状态
   */
  public Status insertUser(User user, Status status) {
    if (status.getCode() == 0) {
      return status;
    }
    try{
      int result = userDao.insertUser(user);
      if (result == 0) {
        status.setErrorMessage(MessageEnum.Wrong.getMessage());
      }
    } catch (Exception e){
      status.setErrorMessage(MessageEnum.Wrong.getMessage());
    }

    return status;
  }


  /**
   * 用户登出即为session注销，因此从redis中删除对应session
   * @param sessionID
   */
  public void logoutUser(String sessionID, Status status){
    redisDao.deleteKeyValue(sessionID);
  }


  /**
   * 如果 status.code == 0，尝试进行用户注销。 如果注销失败，则将注销状态标记失败。
   * @param sessionID sessionID
   * @param status 状态
   * @return 状态
   */
  public Status deleteUser(String sessionID, Status status){
    String value = redisDao.getValue(sessionID);
    System.out.println(value);
    User user = null;
    try{
      user = JSON.parseObject(value, User.class);;
      System.out.println("获得user"+user);
      int result = userDao.deleteUserByNameOrPhone(user);
      System.out.println("delete"+result);
      if (result == 0) {
        status.setErrorMessage(MessageEnum.Wrong.getMessage());
      }else{
        logoutUser(sessionID, status);
      }
    } catch (Exception e){
      status.setErrorMessage(MessageEnum.Wrong.getMessage());
    }
    return status;
  }

  /**
   * 根据 sessionID 从缓存中获取用户信息
   * @param sessionID sessionID
   * @return 返回用户信息
   */
  public User getUser(String sessionID){
    String value = redisDao.getValue(sessionID);
    System.out.println(value);
    User user = new User();
    try{
      user = JSON.parseObject(value, User.class);
    }catch (Exception e){
      //
    }
    return user;
  }

  /**
   * 用户使用用户名和密码登录，判断用户名和密码是否存在且匹配
   *
   * @param user 用户
   * @return
   */
  public Status loginByNameAndPassword(User user){
    Status status = new LoginStatus();
    status.setSessionID(ServletUtil.getSessionID());
    User searchUser = userDao.selectUserByName(user.getUsername()); // 首先查找用户名是否存在
    if(searchUser == null){
      status.setErrorMessage("该用户名不存在");
    }else{
      searchUser = getUserByNameAndPassword(user);
      if(searchUser == null){
        status.setErrorMessage("密码错误");
      }
    }
    return status;
  }

  /**
   * 从数据库中获取用户信息
   * @param user
   * @return
   */
  public User getUserByNameAndPassword(User user){
    return userDao.selectUserByNameAndPassword(user);
  }

  /**
   * 用户使用手机号和验证码登录，判断手机号是否已注册，判断验证码是否正确
   *
   * @param PhoneNumber 手机号
   * @param code 验证码
   * @return
   */
  public Status loginByPhone(String PhoneNumber, String code){
    Status status = new LoginStatus();
    status.setSessionID(ServletUtil.getSessionID());
    User searchUser = getUserByPhone(PhoneNumber);
    if(searchUser == null){
      status.setErrorMessage("该手机号未注册");
    }else{
      applyService.verifyCode(PhoneNumber, ServletUtil.getIp(), ServletUtil.getDeviceID(), code, status);
    }
    return status;
  }

  /**
   * 从数据库中获取用户信息
   * @return
   */
  public User getUserByPhone(String phone){
    return userDao.selectUserByPhoneNumber(phone);
  }

  /**
   * 将用户写入缓存，记录登录态
   * @param sessionID
   * @param user
   */
  public void saveStatus(String sessionID, User user){
    System.out.println("保存Redis缓存中"+JSON.toJSONString(user));
    redisDao.saveKeyValue(sessionID, JSON.toJSONString(user), 5, TimeUnit.MINUTES);
  }
}
