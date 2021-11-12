package org.study.grabyou.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.study.grabyou.entity.User;

/**
 * 用户数据库的操作
 * 当前仅仅写了 insertUser 和 selectUserByIdOrName 的映射
 * insertUser 插入一个用户名
 * selectUserByIdOrName 根据 User 类中 username or phoneNumber 来判断是否存在重复用户
 */
@Repository
@Mapper
public interface UserDao {

  Integer insertUser(User user);

  Integer updateUser(User user);

  User selectUser(User user);

  List<User> selectAllUser();

  User selectUserByIdOrName(User user);
}
