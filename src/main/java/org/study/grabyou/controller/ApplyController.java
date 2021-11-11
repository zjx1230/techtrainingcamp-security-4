package org.study.grabyou.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.study.grabyou.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ApplyController {

  @Autowired
  @Qualifier("applyServiceImp")
  ApplyService applyService;

  @RequestMapping("/apply")
  public String show() {
    return "apply";
  }

  @RequestMapping(value = "/doApply", method = RequestMethod.POST)
  public String apply(String phonenum, String ip, String deviceid) {
    //获取验证码
    String code = applyService.getCode(phonenum, ip, deviceid);
    //输出以调试
    System.out.println(code);
    return "verify";
  }

  @RequestMapping(value = "/doVerify", method = RequestMethod.POST)
  public String verify(String phonenum, String ip, String deviceid, String code) {
    boolean res = applyService.verifyCode(phonenum, ip, deviceid, code);
    if (res) {
      return "success";
    } else {
      return "error";
    }
  }
}
