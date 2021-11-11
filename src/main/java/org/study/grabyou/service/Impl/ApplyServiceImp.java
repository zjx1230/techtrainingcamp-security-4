package org.study.grabyou.service.Impl;

import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;
import org.study.grabyou.entity.ApplyBean;
import org.study.grabyou.entity.Status;
import org.study.grabyou.enums.MessageEnum;
import org.study.grabyou.mapper.ApplyMapper;
import org.study.grabyou.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplyServiceImp implements ApplyService {

  @Autowired
  private ApplyMapper applyMapper;

  @Override
  public String getCode(String phonenum, String ip, String deviceid) {
    Date date = new Date();
    Random ran = new Random(date.getTime());
    String code = String.valueOf(ran.nextInt(1000000));

    //计算过期时间
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    calendar.add(Calendar.HOUR_OF_DAY, 1);//过期时间1小时
    date = calendar.getTime();
    Timestamp ExpireTime = new Timestamp(date.getTime());


    ApplyBean applyBean = new ApplyBean();
    applyBean.setCode(code);
    applyBean.setPhonenum(phonenum);
    applyBean.setIp(ip);
    applyBean.setDeviceid(deviceid);
    applyBean.setExpiretime(ExpireTime);
    applyMapper.addApply(applyBean);
    return code;
  }

  @Override
  public boolean verifyCode(String phonenum, String ip, String deviceid, String code) {
    ApplyBean searchBean = new ApplyBean();
    searchBean.setPhonenum(phonenum);
    searchBean.setIp(ip);
    searchBean.setDeviceid(deviceid);
    ApplyBean resultBean = applyMapper.getApply(searchBean);

    if (resultBean == null || resultBean.getExpiretime().before(new Date())) {
      return false;
    }
    return resultBean.getCode().equals(code);
  }

  @Override
  public Status verifyCode(String phonenum, String ip, String deviceid, String code,
      Status status) {
    boolean verify = verifyCode(phonenum, ip, deviceid, code);
    if(!verify){
      status.setErrorMessage(MessageEnum.WrongCode.getMessage());
    }
    return status;
  }
}
