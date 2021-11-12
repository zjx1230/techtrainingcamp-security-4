package org.study.grabyou.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.study.grabyou.entity.User;


@Repository
@Mapper
public interface UserDao {
  User selectUserByName(String username);

  User selectUserByNameAndPassword(User user);

  User selectUserByPhoneNumber(String phoneNumber);

}
