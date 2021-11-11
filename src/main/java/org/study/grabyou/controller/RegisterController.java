package org.study.grabyou.controller;

import javax.servlet.http.HttpSession;
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
import org.study.grabyou.service.RegisterService;
import org.study.grabyou.utils.ServletUtil;

@Controller
public class RegisterController {

  @Autowired
  IRiskControllService riskControllService;
  @Autowired
  ApplyService applyService;
  @Autowired
  RegisterService registerService;


  @GetMapping(value = "/register")
  public String signupPage() {
    return "register";
  }


  /**
   * 前端填入用户名、密码、手机号、验证码来申请注册
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
    String device = ServletUtil.getFullDeviceID();
    System.out.println(ip + " " + device);
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
    httpSession.setMaxInactiveInterval(10);
    status.setSessionID(sessionID);
    status.setExpireTime(10);

    // 3 - DecisionType
    int decisiontype = riskControllService
        .analysis(username, EventType.REGISTER, ip, device, phone);
    System.out.println(username+" "+EventType.REGISTER +" "+ip+" "+ device+" "+phone);
    System.out.println(decisiontype);
    registerService.judgeDecisionType(decisiontype, status);

    registerService.insertUser(user, status);

    return status;
  }

  /**
   * 根据手机号发送验证码。验证码会在服务器控制台输出，并返回到前端通过alert展示（当作通过短信发送到手机上）。
   *
   * @param phone 需要接受验证码的手机号
   * @return 验证码
   */
  @PostMapping(value = "/sendCode")
  @ResponseBody
  public String sendCode(String phone) {
    String ip = ServletUtil.getIp();
    String device = ServletUtil.getFullDeviceID();
    String code = applyService.getCode(phone, ip, device);
    System.out.println("==================");
    System.out.println(phone + "对应的验证码为" + code + "!!!!");
    System.out.println("==================");
    return code;
  }
//
//
//  @PostMapping(value = "/signup_username")
//  public String signupWithUsername(String username, String password) {
//    SignUpStatus signUpStatus = SignUpProcess.judgeUserByName(username, password);
//    // 通过查看用户名，发现没有问题，那么尝试插入到数据库中
//    if (signUpStatus.getStatus_code() == 0) {
//      HttpSession session = getSession();
//      String phone = (String) session.getAttribute("phone");
//      signUpStatus = SignUpProcess.insertUser(phone, username, password);
//    }
//    // 输出注册结果
//    System.out.println(signUpStatus.getMessage());
//    // 根据注册结果，返回不同的页面
//    if (signUpStatus.getStatus_code() == 0) {
//      return "success";
//    } else {
//      return "signup_username";
//    }
//  }


}
