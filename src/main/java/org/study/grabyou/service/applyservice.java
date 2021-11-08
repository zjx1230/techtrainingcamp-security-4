package org.study.grabyou.service;

import org.study.grabyou.bean.applybean;


public interface applyservice {
  String getCode(String phonenum,String ip,String deviceid);
  boolean verifyCode(String phonenum,String ip,String deviceid,String code);
}
