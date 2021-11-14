package org.study.grabyou.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.study.grabyou.entity.Event;
import org.study.grabyou.entity.Status;
import org.study.grabyou.entity.User;
import org.study.grabyou.entity.impl.DeregisterStatus;
import org.study.grabyou.service.Impl.BlackListService;
import org.study.grabyou.service.UserAccessService;
import org.study.grabyou.utils.ServletUtil;

@Controller
public class DeregisterController {

  @Autowired
  private UserAccessService userAccessService;
  @Autowired
  private BlackListService blackListService;


  @GetMapping(value = "/deregister")
  @ResponseBody
  public Status deregister(HttpServletRequest request, HttpServletResponse response){
    HttpSession session = request.getSession();
    DeregisterStatus status = new DeregisterStatus();
    status.setSessionID(session.getId());
    userAccessService.deleteUser(session.getId(), status);
    System.out.println(status);
    if(status.getCode() == 1){
      session.invalidate();
    }else{
      // 没有注销成功，则风控判断
      User user = userAccessService.getUser(session.getId());
      Event event = new Event();
      event.setUserName(user.getUsername());
      event.setTelephoneNumber(user.getPhoneNumber());
      boolean inBlackList = blackListService.isInBlackList(event);
      if(inBlackList){
        ServletUtil.getSession().invalidate();
        return null;
      }
    }
    return status;
  }
}
