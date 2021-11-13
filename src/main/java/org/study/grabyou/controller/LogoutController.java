package org.study.grabyou.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.study.grabyou.entity.impl.LogoutStatus;
import org.study.grabyou.service.UserAccessService;

public class LogoutController {

  @Autowired
  private UserAccessService userAccessService;

  /**
   * 手动销毁 Session
   */
  @GetMapping(value = "/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response){
    HttpSession session = request.getSession();
    userAccessService.logoutUser(session.getId(), new LogoutStatus());
    session.invalidate();
    return "index";
  }

}
