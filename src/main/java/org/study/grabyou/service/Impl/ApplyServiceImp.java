package org.study.grabyou.service.Impl;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.study.grabyou.dao.RedisDao;
import org.study.grabyou.entity.Status;
import org.study.grabyou.enums.MessageEnum;
import org.study.grabyou.mapper.ApplyMapper;
import org.study.grabyou.service.ApplyService;

@Service
public class ApplyServiceImp implements ApplyService {

  @Autowired
  private ApplyMapper applyMapper;

  @Autowired
  private RedisDao redisDao;


  /**
   * 通过手机号、IP、设备号生成唯一值 Key
   *
   * @param phonenum 手机号
   * @param ip       IP地址
   * @param deviceid 设备号
   * @return Key值
   */
  private String createKey(String phonenum, String ip, String deviceid) {
    // StringBuilder 拼接速度快一点
    StringBuilder sb = new StringBuilder(phonenum).append("---").append(ip).append("---")
        .append(deviceid);
    return sb.toString();
  }

  /**
   * 根据用户输入的验证码和存储的验证码进行匹配，返回匹配状态
   *
   * @param codeTmp 缓存中存储的验证码
   * @param code    用户输入验证码
   * @return 匹配状态，0代表匹配成功，1代表验证码失效 or 根本没发送验证码，2代表验证码错误
   */
  private int getMatchStatus(String codeTmp, String code) {
    int status = 0;
    if (StringUtils.isEmpty(codeTmp) || codeTmp == null) {
      status = 1;
    } else if (!codeTmp.equals(code)) {
      status = 2;
    }
    return status;
  }


  /**
   * 生成随机的验证码值。
   * 如果缓存中验证码没过期，则不生成验证码，返回 ""。
   * 如果缓存中验证码不存在，则生成验证码，并将 key - 验证码 存储到Redis缓存中。
   *
   * @param phonenum 手机号
   * @param ip       IP地址
   * @param deviceid 设备号
   * @return 生成的随机验证码 or ""
   */
  @Override
  public String setCode(String phonenum, String ip, String deviceid) {
    String key = createKey(phonenum, ip, deviceid);
    String codeTmp = redisDao.getValue(key);
    String code = "";
    if(codeTmp == null){
      Random ran = new Random(new Date().getTime());
      code = String.format("%06d", ran.nextInt(1000000));
      redisDao.saveKeyValue(key, code, 5, TimeUnit.MINUTES);
    }
    return code;
  }


  /**
   * 从缓存中获得用户对应的验证码，判断并返回匹配状态
   *
   * @param phone  手机号
   * @param ip     IP号
   * @param device 设备号
   * @param code   验证码
   * @return 匹配状态
   */
  @Override
  public int verifyCode(String phone, String ip, String device, String code) {
    String key = createKey(phone, ip, device);
    String codeTmp = redisDao.getValue(key);
    return getMatchStatus(codeTmp, code);
  }

  /**
   * 根据验证码，返回状态码
   *
   * @param phonenum 手机号
   * @param ip       ip号
   * @param deviceid 设备号
   * @param code     用户输入验证码
   * @param status   状态码
   * @return
   */
  @Override
  public Status verifyCode(String phonenum, String ip, String deviceid, String code,
      Status status) {
    int verify = verifyCode(phonenum, ip, deviceid, code);
    switch (verify) {
      case 1:
        status.setErrorMessage(MessageEnum.OutDateCode.getMessage());
        break;
      case 2:
        status.setErrorMessage(MessageEnum.WrongCode.getMessage());
        break;
      default:
        break;
    }
    return status;
  }

//  @Override
//  public String setCode(String phonenum, String ip, String deviceid) {
//    Date date = new Date();
//    Random ran = new Random(date.getTime());
//    String code = String.valueOf(ran.nextInt(1000000));
//
//    //计算过期时间
//    Calendar calendar = new GregorianCalendar();
//    calendar.setTime(date);
//    calendar.add(Calendar.HOUR_OF_DAY, 1);//过期时间1小时
//    date = calendar.getTime();
//    Timestamp ExpireTime = new Timestamp(date.getTime());
//
//    ApplyBean applyBean = new ApplyBean();
//    applyBean.setCode(code);
//    applyBean.setPhonenum(phonenum);
//    applyBean.setIp(ip);
//    applyBean.setDeviceid(deviceid);
//    applyBean.setExpiretime(ExpireTime);
//    applyMapper.addApply(applyBean);
//    return code;
//  }

//  @Override
//  public boolean verifyCode(String phonenum, String ip, String deviceid, String code) {
//    ApplyBean searchBean = new ApplyBean();
//    searchBean.setPhonenum(phonenum);
//    searchBean.setIp(ip);
//    searchBean.setDeviceid(deviceid);
//    ApplyBean resultBean = applyMapper.getApply(searchBean);
//
//    if (resultBean == null || resultBean.getExpiretime().before(new Date())) {
//      return false;
//    }
//    return resultBean.getCode().equals(code);
//  }

}
