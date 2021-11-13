package org.study.grabyou.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.study.grabyou.entity.Status;
import org.study.grabyou.entity.impl.DeregisterStatus;
import org.study.grabyou.service.UserAccessService;

public class DeregisterController {

  @Autowired
  private UserAccessService userAccessService;


  @GetMapping(value = "/deregister")
  @ResponseBody
  public Status deregister(HttpServletRequest request, HttpServletResponse response){
    HttpSession session = request.getSession();
    DeregisterStatus status = new DeregisterStatus();
    userAccessService.deleteUser(session.getId(), status);
    if(status.getCode() == 0){
      session.invalidate();
    }
    return status;
  }
}
