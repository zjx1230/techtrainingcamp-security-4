package org.study.grabyou.entity.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.study.grabyou.entity.Status;

/**
 * 注册时状态
 */
@Component
public class RegisterStatus extends Status {

  public RegisterStatus(){
    super();
    message = "注册成功";
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
