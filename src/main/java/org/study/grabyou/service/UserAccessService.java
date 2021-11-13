package org.study.grabyou.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.grabyou.dao.RedisDao;
import org.study.grabyou.entity.Status;
import org.study.grabyou.entity.impl.RegisterStatus;
import org.study.grabyou.entity.User;
import org.study.grabyou.enums.MessageEnum;
import org.study.grabyou.mapper.UserDao;

@Service
public class UserAccessService {

  @Autowired
  private UserDao userDao;
  @Autowired
  private RedisDao redisDao;

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
   * 如果 status.code == 0，尝试进行用户注册。 如果注册失败，则将注册状态标记失败。
   *
   * @param user   待注册的用户
   * @param status 注册状态
   * @return 注册状态
   */
  public Status insertUser(User user, Status status) {
    if (status.getCode() != 0) {
      return status;
    }
    int result = userDao.insertUser(user);
    if (result == 0) {
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
    User user = null;
    try{
      user = (User)JSON.parse(value);
      int result = userDao.deleteUserByNameOrPhone(user);
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
    return (User)JSON.parse(value);
  }
}
