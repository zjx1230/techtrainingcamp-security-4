package org.study.grabyou.controller;

import com.alibaba.fastjson.JSON;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.study.grabyou.dao.RedisDao;
import org.study.grabyou.entity.Status;
import org.study.grabyou.entity.User;
import org.study.grabyou.entity.impl.RegisterStatus;
import org.study.grabyou.enums.EventType;
import org.study.grabyou.service.ApplyService;
import org.study.grabyou.service.Impl.RiskControllService;
import org.study.grabyou.service.UserAccessService;
import org.study.grabyou.utils.ServletUtil;

@Controller
public class RegisterController {

  @Autowired
  private RiskControllService riskControllService;
  @Autowired
  private ApplyService applyService;
  @Autowired
  private UserAccessService userAccessService;
  @Autowired
  private RedisDao redisDao;


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
  public Status getRegisterStatus(String username, String password, String phone,
      String code) {
    Status status = userAccessService.userRegister(username, password, phone, code);
    return status;
  }
}
