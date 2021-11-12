package org.study.grabyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.grabyou.entity.Status;
import org.study.grabyou.entity.impl.LoginStatus;
import org.study.grabyou.entity.User;
import org.study.grabyou.mapper.UserDao;
import org.study.grabyou.service.impl.ApplyServiceImp;
import org.study.grabyou.utils.ServletUtil;

@Service
public class LoginService {

    @Autowired
    private UserDao userDao;

    /**
     * 用户使用用户名和密码登录，判断用户名和密码是否存在且匹配
     *
     * @param user 用户
     * @return
     */
    public Status loginByNameAndPassword(User user){
        LoginStatus status = new LoginStatus();
        User searchUser = userDao.selectUserByName(user.getUsername()); // 首先查找用户名是否存在
        if(searchUser == null){
            status.setErrorMessage("该用户名不存在");
        }else{
            searchUser = userDao.selectUserByNameAndPassword(user); // 验证密码是否正确
            if(searchUser == null){
                status.setErrorMessage("密码错误");
            }
        }
        return status;
    }

    /**
     * 用户使用手机号和验证码登录，判断手机号是否已注册，判断验证码是否正确
     * 
     * @param PhoneNumber 手机号
     * @param code 验证码
     * @return
     */
    public Status loginByPhone(String PhoneNumber, String code){
        LoginStatus status = new LoginStatus();
        User searchUser = userDao.selectUserByPhoneNumber(PhoneNumber);   // 首先查找手机号是否注册用户
        if(searchUser == null){
            status.setErrorMessage("该手机号未注册");
        }else{
            ApplyServiceImp applyService = new ApplyServiceImp();
            if(!applyService.verifyCode(PhoneNumber, ServletUtil.getIp(), ServletUtil.getDeviceID(), code)){
                status.setErrorMessage("验证码输入错误");
            }
        }
        return status;
    }
}
