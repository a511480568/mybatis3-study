package org.apache.ibatis.personal.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.personal.mapper.UserMapper;
import org.apache.ibatis.personal.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class UserMapperTest1 {

  public static void main(String[] args) throws Exception {
    InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");

    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession();
    // 这里是通过 JDK 的动态代理创建的代理对象，其实是 MapperProxy
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    System.out.println("mapper = " + mapper);
    User user = mapper.getUser(20L);
    System.out.println("user's id = " + user.getId());
    System.out.println("user's name = " + user.getName());
  }
}
