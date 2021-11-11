package org.study.grabyou.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.study.grabyou.entity.User;

@Repository
@Mapper
public interface UserDao {

  Integer insertUser(User user);

  Integer updateUser(User user);

  User selectUser(User user);

  List<User> selectAllUser();

  User selectUserByIdOrName(User user);
}
