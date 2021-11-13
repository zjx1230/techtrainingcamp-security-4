package org.study.grabyou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.study.grabyou.entity.impl.ApplyStatus;
import org.study.grabyou.service.ApplyService;
import org.study.grabyou.utils.ServletUtil;

/**
 * 验证码模块。
 * 1. 访问验证码测试页面/apply  public String show()。
 * 2. 请求发送验证码函数  public String sendCode(String phone)。
 * 3. 验证码判断是否正确函数  public String verify(String phonenum, String code)。
 */
@Controller
public class ApplyController {

  @Autowired
  @Qualifier("applyServiceImp")
  ApplyService applyService;

  /**
   * 通过 /apply 访问发送验证码页面
   *
   * @return 验证码测试网页 apply.html
   */
  @GetMapping("/apply")
  public String show() {
    return "apply";
  }

//  @PostMapping(value = "/doApply")
//  public String apply(String phonenum, String ip, String deviceid) {
//    //获取验证码
//    String code = applyService.getCode(phonenum, ip, deviceid);
//    //输出以调试
//    System.out.println(code);
//    return "verify";
//  }


  /**
   * 前端点击发送验证码后，会将手机号传到后端。
   * 后端产生验证码，验证码会在服务器控制台输出，并返回到前端通过alert展示（当作通过短信发送到手机上）。
   *
   * @param phone 需要接受验证码的手机号
   * @return 验证码
   */
  @PostMapping(value = "/sendCode")
  @ResponseBody
  public String sendCode(String phone) {
    String ip = ServletUtil.getIp();
    String device = ServletUtil.getFullDeviceID();
    String code = applyService.setCode(phone, ip, device);
    if(code != ""){
      System.out.println("==================");
      System.out.println(phone + "对应的验证码为" + code + "!!!!");
      System.out.println("==================");
    }
    return code;
  }


  /**
   * 前端点击提交后，后端通过手机号、IP、Device、时间等来判断验证码是否正确
   *
   * @param phone 手机号
   * @param code  前端传来的验证码
   * @return 是否成功
   */
  @PostMapping(value = "/doVerify")
  @ResponseBody
  public String verify(String phone, String code) {
    String ip = ServletUtil.getIp();
    String device = ServletUtil.getFullDeviceID();
    return applyService.verifyCode(phone, ip, device, code, new ApplyStatus()).getMessage();
  }
}
