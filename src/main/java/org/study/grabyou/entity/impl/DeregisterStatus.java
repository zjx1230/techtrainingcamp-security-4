package org.study.grabyou.entity.impl;

import org.study.grabyou.entity.Status;

public class DeregisterStatus extends Status {

  public DeregisterStatus(){
    super();
    message = "注销成功";
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
