package org.study.grabyou.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class User {

  private String username;
  private String password;
  private String phoneNumber;
  private Integer tag = 0;

  public User(String phone) {
    this.phoneNumber = phone;
  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public User(String phone, String username, String password) {
    this.phoneNumber = phone;
    this.username = username;
    this.password = password;
  }

  public User() {
    username = "";
    password = "";
    phoneNumber = "";

  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public Integer getTag() {
    return tag;
  }

  public void setTag(Integer tag) {
    this.tag = tag;
  }

  @Override
  public String toString() {
    return "User{" +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", tag=" + tag +
        '}';
  }
}
