package org.study.grabyou.controller;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.study.grabyou.entity.User;
import org.study.grabyou.entity.impl.RegisterStatus;
import org.study.grabyou.enums.EventType;
import org.study.grabyou.service.ApplyService;
import org.study.grabyou.service.IRiskControllService;
import org.study.grabyou.service.Impl.RiskControllService;
import org.study.grabyou.service.UserAccessService;
import org.study.grabyou.utils.ServletUtil;

@Controller
public class RegisterController {

  @Autowired
  RiskControllService riskControllService;
  @Autowired
  ApplyService applyService;
  @Autowired
  UserAccessService registerService;

  private static Logger logger = LoggerFactory.getLogger(RegisterController.class);

  /**
   * 请求 /register 网页
   * @return
   */
  @GetMapping(value = "/register")
  public String signupPage() {
    return "register";
  }


  /**
   * 前端在/register网页中，填入用户名、密码、手机号、验证码后，点击submit请求后，后端执行函数
   * 后端根据前端传来的数据，返回注册状态，标识能否进行注册
   *
   * @param username 用户名
   * @param password 密码
   * @param phone    手机号
   * @param code     验证码
   * @return 注册状态
   */
  @PostMapping(value = "/register")
  @ResponseBody
  public RegisterStatus getRegisterStatus(String username, String password, String phone,
      String code) {
    String ip = ServletUtil.getIp();
    String device = ServletUtil.getDeviceID();
//    String device = ServletUtil.getFullDeviceID();
    User user = new User(phone, username, password);
    RegisterStatus status = new RegisterStatus();
    // 1 - 生成message
    // 1.1 判断是否存在用户名相同的人
    registerService.judgeUserByName(user, status);
    // 1.2 判断是否存在手机相同的人
    registerService.judgeUserByPhone(user, status);
    // 1.3 判断验证码问题
//    applyService.verifyCode(phone, ip, device, code, status);
    // 2 - sessionID & ExpireTime
    HttpSession httpSession = ServletUtil.getSession();
    String sessionID = httpSession.getId();
    httpSession.setMaxInactiveInterval(60); //60秒
    status.setSessionID(sessionID);
    status.setExpireTime(10);
    // 3 - DecisionType
    int decisiontype = riskControllService.analysis(username, EventType.REGISTER, ip, device, phone);
    registerService.judgeDecisionType(decisiontype, status);
    // 4 - 尝试对用户进行注册
    registerService.insertUser(user, status);
    return status;
  }
}
