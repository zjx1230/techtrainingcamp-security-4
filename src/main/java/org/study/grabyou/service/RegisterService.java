package org.study.grabyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.grabyou.entity.impl.RegisterStatus;
import org.study.grabyou.entity.User;
import org.study.grabyou.enums.MessageEnum;
import org.study.grabyou.mapper.UserDao;

@Service
public class RegisterService {

  @Autowired
  private UserDao userDao;

  /**
   * 根据用户名判断是否存在相同用户名的人
   *
   * @param user
   * @param status
   * @return
   */
  public RegisterStatus judgeUserByName(User user, RegisterStatus status) {
    // 根据用户名搜索用户，判断是否有相同的用户名
    User searchUser = new User();
    searchUser.setUsername(user.getUsername());
    Object res = userDao.selectUserByIdOrName(searchUser);
    if (res != null) {
      status.setErrorMessage(MessageEnum.SameUsername.getMessage());
    }
    return status;
  }

  /**
   * 根据用户手机号判断是否存在相同手机号的人
   *
   * @param user
   * @param status
   * @return
   */
  public RegisterStatus judgeUserByPhone(User user, RegisterStatus status) {
    // 根据手机号搜索用户，判断是否有相同的手机号
    User searchUser = new User();
    searchUser.setPhoneNumber(user.getPhoneNumber());
    Object res = userDao.selectUserByIdOrName(searchUser);
    if (res != null) {
      status.setErrorMessage(MessageEnum.SamePhone.getMessage());
    }
    return status;
  }

  /**
   * 根据判断指标来更新注册状态
   *
   * @param decisiontype
   * @param status
   * @return
   */
  public RegisterStatus judgeDecisionType(int decisiontype, RegisterStatus status) {
    status.setDecisionType(decisiontype);
    switch (decisiontype) {
      case 0:
        break;
      case 1:
        status.setErrorMessage(MessageEnum.RiskMessge1.getMessage());
        break;
      case 2:
        status.setErrorMessage(MessageEnum.RiskMessage2.getMessage());
        break;
      case 3:
        status.setErrorMessage(MessageEnum.RiskMessage3.getMessage());
        break;
      default:
        status.setErrorMessage(MessageEnum.Wrong.getMessage());
        break;
    }
    return status;
  }

  /**
   * 如果 status.code == 0，尝试进行用户注册。 如果注册失败，则将注册状态标记失败。
   *
   * @param user   待注册的用户
   * @param status 注册状态
   * @return 注册状态
   */
  public RegisterStatus insertUser(User user, RegisterStatus status) {
    if (status.getCode() != 0) {
      return status;
    }
    int result = userDao.insertUser(user);
    if (result == 0) {
      status.setErrorMessage(MessageEnum.Wrong.getMessage());
    }
    return status;
  }

}