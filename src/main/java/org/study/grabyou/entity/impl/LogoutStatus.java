package org.study.grabyou.entity.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.study.grabyou.entity.Status;

/**
 * 注销时的状态
 */
@Component
public class LogoutStatus implements Status {

  @Value("0")
  private int code;
  @Value("注销成功")
  private String message;
  private String sessionID;
  @Value("60")
  private int expireTime; //60 minutes
  @Value("0")
  private int decisionType;


  @Override
  public int getCode() {
    return 0;
  }

  @Override
  public void setCode(int code) {

  }

  @Override
  public String getMessage() {
    return null;
  }

  @Override
  public void setMessage(String message) {

  }

  @Override
  public String getSessionID() {
    return null;
  }

  @Override
  public void setSessionID(String sessionID) {

  }

  @Override
  public int getExpireTime() {
    return 0;
  }

  @Override
  public void setExpireTime(int expireTime) {

  }

  @Override
  public int getDecisionType() {
    return 0;
  }

  @Override
  public void setDecisionType(int decisionType) {

  }

  @Override
  public void setErrorMessage(String message) {

  }

  @Override
  public String toString() {
    return "LogoutStatus{" +
        "code=" + code +
        ", message='" + message + '\'' +
        ", sessionID='" + sessionID + '\'' +
        ", expireTime=" + expireTime +
        ", decisionType=" + decisionType +
        '}';
  }
}
