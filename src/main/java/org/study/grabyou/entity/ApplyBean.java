package org.study.grabyou.entity;
import java.sql.Timestamp;

public class ApplyBean {

  private String phonenum;
  private String ip;
  private String deviceid;
  private String code;
  private Timestamp expiretime;

  public String getPhonenum() {
    return phonenum;
  }

  public void setPhonenum(String phonenum) {
    this.phonenum = phonenum;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getDeviceid() {
    return deviceid;
  }

  public void setDeviceid(String deviceid) {
    this.deviceid = deviceid;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Timestamp getExpiretime() {
    return expiretime;
  }

  public void setExpiretime(Timestamp expiretime) {
    this.expiretime = expiretime;
  }

}
