package org.study.grabyou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.study.grabyou.entity.Status;
import org.study.grabyou.entity.User;
import org.study.grabyou.enums.EventType;
import org.study.grabyou.service.Impl.RiskControllService;
import org.study.grabyou.service.UserAccessService;
import org.study.grabyou.utils.ServletUtil;

@Controller
public class LoginController {
  @Autowired
  UserAccessService userAccessService;
  @Autowired
  RiskControllService riskControllService;
  /**
   * 请求 /login 网页
   * @return
   */
  @GetMapping(value = "/login")
  public String loginPage(){
    return "login";
  }

  /**
   * 前端在/login网页输入用户名和密码进行登录
   *
   * @return 登录状态
   */
  @PostMapping(value = "/login_name")
  @ResponseBody
  public Status LoginByPassword(String username, String password){
    User requestUser = new User(username, password);
    int analysisResult = riskControllService.analysis(requestUser.getUsername(), EventType.LOGIN, ServletUtil.getIp(), ServletUtil.getDeviceID(), null);
    Status status = userAccessService.loginByNameAndPassword(requestUser);
    userAccessService.judgeDecisionType(analysisResult, status);
    // 记录登录态
    if(analysisResult != 3){
      requestUser = userAccessService.getUserByNameAndPassword(requestUser);
//      System.out.println(requestUser);
      userAccessService.saveStatus(ServletUtil.getSessionID(), requestUser);
    }
    return status;
  }

  /**
   * 前端在/login网页输入手机号和验证码进行登录
   *
   * @param PhoneNumber 手机号
   * @param code 验证码
   * @return 登录状态
   */
  @PostMapping(value = "/login_phone")
  @ResponseBody
  public Status LoginByPhoneNumber(String PhoneNumber, String code){
    Status status = userAccessService.loginByPhone(PhoneNumber, code);
    int analysisResult = riskControllService.analysis(null, EventType.LOGIN, ServletUtil.getIp(), ServletUtil.getDeviceID(), PhoneNumber);
    userAccessService.judgeDecisionType(analysisResult, status);
    // 记录登录态
    if(analysisResult != 3){
      User user = userAccessService.getUserByPhone(PhoneNumber);
      userAccessService.saveStatus(ServletUtil.getSessionID(), user);
    }
    return status;
  }

}