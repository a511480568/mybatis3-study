package org.apache.ibatis.personal.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.personal.model.User;

//@Mapper
public interface UserMapper {

  User getUser(Long id);

  User getUserByCondition(User user);

  int addUser(User user);

  int updateUserById(User user);
}
