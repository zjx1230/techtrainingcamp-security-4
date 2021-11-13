package org.study.grabyou.entity.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.study.grabyou.entity.Status;

@Component
public class ApplyStatus extends Status {

  public ApplyStatus() {
    message = "验证码正确";
  }

  @Override
  public String toString() {
    return "ApplyStatus{" +
        "code=" + code +
        ", message='" + message + '\'' +
        ", sessionID='" + sessionID + '\'' +
        ", expireTime=" + expireTime +
        ", decisionType=" + decisionType +
        '}';
  }
}
