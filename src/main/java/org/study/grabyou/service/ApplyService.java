package org.study.grabyou.service;


import org.study.grabyou.entity.Status;

public interface ApplyService {

  String setCode(String phonenum, String ip, String deviceid);

  int verifyCode(String phonenum, String ip, String deviceid, String code);

  Status verifyCode(String phonenum, String ip, String deviceid, String code, Status status);
}
