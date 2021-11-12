package org.study.grabyou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.study.grabyou.entity.User;
import org.study.grabyou.entity.impl.LoginStatus;
import org.study.grabyou.enums.EventType;
import org.study.grabyou.service.LoginService;
import org.study.grabyou.service.impl.RiskControllService;
import org.study.grabyou.utils.ServletUtil;
import org.study.grabyou.service.impl.ApplyServiceImp;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

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
     * @param requestUser 用户
     * @return 登录状态
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public LoginStatus LoginByPassword(@RequestBody User requestUser){
        RiskControllService riskControllService = new RiskControllService();
        int analysisResult = riskControllService.analysis(requestUser.getUsername(), EventType.LOGIN, ServletUtil.getIp(), ServletUtil.getDeviceID(), null);
        LoginStatus status = (LoginStatus)loginService.loginByNameAndPassword(requestUser);
        status.setDecisionType(analysisResult);
        return status;
    }

    /**
     * 前端在/login网页输入手机号和验证码进行登录
     * 
     * @param PhoneNumber 手机号
     * @param code 验证码
     * @return 登录状态
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public LoginStatus LoginByPhoneNumber(@RequestParam("PhoneNumber") String PhoneNumber, @RequestParam("code") String code){
        RiskControllService riskControllService = new RiskControllService();
        int analysisResult = riskControllService.analysis(null, EventType.LOGIN, ServletUtil.getIp(), ServletUtil.getDeviceID(), PhoneNumber);
        LoginStatus status = (LoginStatus)loginService.loginByPhone(PhoneNumber, code);
        status.setDecisionType(analysisResult);
        return status;
    }

    /**
     * 前端点击发送验证码后，会将手机号传到后端
     * 后端产生验证码，验证码会在服务器控制台输出，并返回到前端通过alert展示（当作通过短信发送到手机上）。
     *
     * @param phone 需要接受验证码的手机号
     * @return 验证码
     */
    @PostMapping(value = "/sendCode")
    @ResponseBody
    public String sendCode(@RequestParam("PhoneNumber") String PhoneNumber) {
        ApplyServiceImp applyService = new ApplyServiceImp();
        String ip = ServletUtil.getIp();
        String device = ServletUtil.getFullDeviceID();
        String code = applyService.getCode(PhoneNumber, ip, device);
        System.out.println("==================");
        System.out.println(PhoneNumber + "对应的验证码为" + code + "!!!!");
        System.out.println("==================");
        return code;
    }
}
