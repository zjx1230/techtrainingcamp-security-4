package org.study.grabyou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.study.grabyou.service.UserAccessService;
import org.study.grabyou.utils.ServletUtil;

public class IndexController {
  @Autowired
  private UserAccessService userAccessService;

  /**
   * 用户信息展示 - 根据sessionID查看用户信息
   * @return
   */
  @GetMapping("/user")
  public ModelAndView user(){
    String sessionID = ServletUtil.getSessionID();
    ModelAndView mv = new ModelAndView();
    mv.addObject(userAccessService.getUser(sessionID));
    mv.setViewName("user");
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
