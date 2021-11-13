package org.study.grabyou.enums;

/**
 * 传递到前端时用到的message字符串，
 * 需要修改错误问题对应的信息时，直接改这里即可，不用再全局找出问题的字符串了
 */
public enum MessageEnum {
  SameUsername("Username has been registered. "),
  SamePhone("Phone has been registered. "),
  RiskMessge1("Limit 1. "),
  RiskMessage2("Limit 2. "),
  RiskMessage3("Limit 3. "),
  Wrong("Get data Error, pls try latter. "),
  WrongPassword("Password Error. "),
  SessionEnd("SessionID cant get. "),
  OutDateCode("code out date. "),
  WrongCode("Code Error." ),
  SuccessCode("Code Success. ");


  private String message;

  MessageEnum(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
