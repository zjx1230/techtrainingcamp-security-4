package org.study.grabyou.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.study.grabyou.entity.BlackRecord;
import org.study.grabyou.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.study.grabyou.entity.User;
import org.study.grabyou.entity.impl.LogoutStatus;
import org.study.grabyou.enums.EventType;
import org.study.grabyou.service.Impl.BlackListService;
import org.study.grabyou.service.Impl.RiskControllService;
import org.study.grabyou.service.UserAccessService;

@Controller
public class LogoutController {

  @Autowired
  private UserAccessService userAccessService;


  /**
   * 手动销毁 Session
   */
  @GetMapping(value = "/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response){
    HttpSession session = request.getSession();
    String sessionid = session.getId();
    userAccessService.logoutUser(sessionid, null);
    session.invalidate();
    return "index";
  }

}
