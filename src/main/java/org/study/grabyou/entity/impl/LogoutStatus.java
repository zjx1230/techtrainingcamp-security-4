package org.study.grabyou.entity.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.study.grabyou.entity.Status;

/**
 * 注销时的状态
 */
@Component
public class LogoutStatus extends Status {

  public LogoutStatus(){
    super();
    message = "登出成功";
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
