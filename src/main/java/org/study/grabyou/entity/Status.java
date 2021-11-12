package org.study.grabyou.entity;

/**
 * 注册、登录、登出、注销时的状态，即返回内容
 */
public interface Status {

  int getCode();

  void setCode(int code);

  String getMessage();

  void setMessage(String message);

  String getSessionID();

  void setSessionID(String sessionID);

  int getExpireTime();

  void setExpireTime(int expireTime);

  int getDecisionType();

  void setDecisionType(int decisionType);

  void setErrorMessage(String message);
}
