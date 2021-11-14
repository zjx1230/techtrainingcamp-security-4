package org.study.grabyou.entity.impl;

import org.springframework.stereotype.Component;
import org.study.grabyou.entity.Status;

/**
 * 登录状态
 */
@Component
public class LoginStatus extends Status {

  public LoginStatus() {
    super();
    message = "登录成功";
  }

  @Override
  public String toString() {
    return "LoginStatus{" +
        "code=" + code +
        ", message='" + message + '\'' +
        ", sessionID='" + sessionID + '\'' +
        ", expireTime=" + expireTime +
        ", decisionType=" + decisionType +
        '}';
  }
}