package org.study.grabyou.entity;

/**
 * 注册、登录、登出、注销时的状态，即返回内容
 */

public abstract class Status {

  protected int code; // 1 - 注册成功 0 - 注册失败
  protected String message;
  protected String sessionID;
  protected int expireTime; // seconds 单位
  protected int decisionType;

  public Status() {
    this.code = 1;
    this.expireTime = 5 * 60;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getSessionID() {
    return sessionID;
  }

  public void setSessionID(String sessionID) {
    this.sessionID = sessionID;
  }

  public int getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(int expireTime) {
    this.expireTime = expireTime;
  }

  public int getDecisionType() {
    return decisionType;
  }

  public void setDecisionType(int decisionType) {
    this.decisionType = decisionType;
  }

  /**
   * 设置错误信息。将code直接更换为1，标注注册失败，并添加注册信息
   *
   * @param message
   */
  public void setErrorMessage(String message) {
    if (this.code == 1) {
      this.code = 0;
      this.message = message;
    } else {
      this.message = this.message + message;
    }
  }

  @Override
  public String toString() {
    return "Status{" +
        "code=" + code +
        ", message='" + message + '\'' +
        ", sessionID='" + sessionID + '\'' +
        ", expireTime=" + expireTime +
        ", decisionType=" + decisionType +
        '}';
  }
}
