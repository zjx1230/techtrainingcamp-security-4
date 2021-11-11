package org.study.grabyou.enums;

public enum MessageEnum {
  SameUsername("Username has been registered"),
  SamePhone("Phone has been registered"),
  RiskMessge1("Limit 1"),
  RiskMessage2("Limit 2"),
  RiskMessage3("Limit 3"),
  Wrong("Get data Error, pls try latter"),
  WrongPassword("Password Error"),
  WrongCode("Code Error");


  private String message;
  MessageEnum(String message) {
    this.message = message;
  }
  public String getMessage(){
    return message;
  }
}
