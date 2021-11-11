package org.study.grabyou.entity.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.study.grabyou.entity.Status;

@Component
public class RegisterStatus implements Status {


  @Value("0")
  private int code;
  @Value("注册成功")
  private String message;
  private String sessionID;
  @Value("60")
  private int expireTime; //60 minutes
  @Value("0")
  private int decisionType;

  public RegisterStatus(){
    message = "注册成功";
  }

  @Override
  public int getCode() {
    return code;
  }

  @Override
  public void setCode(int code) {
    this.code = code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String getSessionID() {
    return sessionID;
  }

  @Override
  public void setSessionID(String sessionID) {
    this.sessionID = sessionID;
  }

  @Override
  public int getExpireTime() {
    return expireTime;
  }

  @Override
  public void setExpireTime(int expireTime) {
    this.expireTime = expireTime;
  }

  @Override
  public int getDecisionType() {
    return decisionType;
  }

  @Override
  public void setDecisionType(int decisionType) {
    this.decisionType = decisionType;
  }

  @Override
  public void setErrorMessage(String message) {
    this.code = 1;
    this.message = message;
  }

  @Override
  public String toString() {
    return "RegisterStatus{" +
        "code=" + code +
        ", message='" + message + '\'' +
        ", sessionID='" + sessionID + '\'' +
        ", expireTime=" + expireTime +
        ", decisionType=" + decisionType +
        '}';
  }
}
