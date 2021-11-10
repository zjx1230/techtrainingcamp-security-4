package org.study.grabyou.service;


public interface ApplyService {
  String getCode(String phonenum,String ip,String deviceid);
  boolean verifyCode(String phonenum,String ip,String deviceid,String code);
}
