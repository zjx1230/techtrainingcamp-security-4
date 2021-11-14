package org.study.grabyou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.study.grabyou.entity.BlackRecord;
import org.study.grabyou.entity.Event;
import org.study.grabyou.entity.User;
import org.study.grabyou.service.Impl.BlackListService;
import org.study.grabyou.service.Impl.RiskControllService;
import org.study.grabyou.service.UserAccessService;
import org.study.grabyou.utils.ServletUtil;

@Controller
public class IndexController {
  @Autowired
  private UserAccessService userAccessService;
  @Autowired
  private RiskControllService riskControllService;
  @Autowired
  private BlackListService blackListService;

  /**
   * 用户信息展示 - 根据sessionID查看用户信息
   * @return
   */
  @GetMapping("/user")
  public ModelAndView user(){
    String sessionID = ServletUtil.getSessionID();
    User user = userAccessService.getUser(sessionID);
    System.out.println(user);
    if(user == null){
      user = new User();
    }
    ModelAndView mv = new ModelAndView();
    mv.addObject("username", user.getUsername());
    mv.addObject("phoneNumber", user.getPhoneNumber());
    return mv;
  }

  /**
   * 主界面
   * @return
   */
  @GetMapping("/index")
  public String index(){
    return "index";
  }

}
