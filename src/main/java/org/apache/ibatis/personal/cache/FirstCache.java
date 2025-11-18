package org.apache.ibatis.personal.cache;

import com.alibaba.fastjson2.JSONObject;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.personal.mapper.UserMapper;
import org.apache.ibatis.personal.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * 测试 mybatis 的一级缓存
 */
public class FirstCache {

  public static void main(String[] args) throws Exception {


//    onlyOneSessionAndNoAdd();
//    statementCache();
//    onlyOneSessionAndAdd();
    twoSession();

  }

  /**
   * 只有一个 session，并且只有查询，没有新增、修改等操作
   *
   * 运行结果如下（含日志）：
   * ==>  Preparing: select * from t_user where id = ?
   * ==> Parameters: 20(Long)
   * <==    Columns: id, name
   * <==        Row: 20, mybatis
   * <==      Total: 1
   * {"id":20,"name":"mybatis"}
   * {"id":20,"name":"mybatis"}
   *
   * 从上面含有日志的运行结果可以看到两次调用 mapper 的 getUser 方法，只有以此打印出了 sql 相关日志，说明在同一个 session 中，mybatis 缓存的 key 是相同的，因此，第二次查询时，mybatis 会从缓存中取出数据，而不会再次执行 sql
   * 可以从 statementCache 方法进行验证，statementCache 方法的缓存是 STATEMENT 级别的，每次查询都会去数据库中查询，那么每次都会打印 sql 日志
   */
  private static void onlyOneSessionAndNoAdd() throws Exception {

    InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession(true);

    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    System.out.println(JSONObject.toJSONString(mapper.getUser(20L)));
    System.out.println(JSONObject.toJSONString(mapper.getUser(20L)));
  }

  /**
   * 缓存是 statement 级别，每次查询都会去数据库中查询，那么每次都会打印 sql 日志
   * 在 mybatis-config.xml 中的一级缓存级别改成 STATEMENT
   *
   * 运行结果如下：
   * ==>  Preparing: select * from t_user where id = ?
   * ==> Parameters: 20(Long)
   * <==    Columns: id, name
   * <==        Row: 20, mybatis
   * <==      Total: 1
   * {"id":20,"name":"mybatis"}
   * ==>  Preparing: select * from t_user where id = ?
   * ==> Parameters: 20(Long)
   * <==    Columns: id, name
   * <==        Row: 20, mybatis
   * <==      Total: 1
   * {"id":20,"name":"mybatis"}
   *
   * 两次调用 mapper 的 getUser 方法，每次都会去数据库中查询，因此，每次都会打印 sql 日志
   */
  private static void statementCache() throws Exception {
    InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession(true);

    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    System.out.println(JSONObject.toJSONString(mapper.getUser(20L)));
    System.out.println(JSONObject.toJSONString(mapper.getUser(20L)));
  }

  /**
   * 只有一个 session，但是有新增、修改等操作
   * 运行结果如下：
   *
   * ==>  Preparing: select * from t_user where id = ?
   * ==> Parameters: 20(Long)
   * <==    Columns: id, name
   * <==        Row: 20, mybatis
   * <==      Total: 1
   * {"id":20,"name":"mybatis"}
   *
   * ===== 开始新增一条 user 数据 =====
   * ==>  Preparing: insert into t_user(id,name) values(?,?)
   * ==> Parameters: 30(Long), mybatis(String)
   * <==    Updates: 1
   *
   * ===== 再次查询 user 数据 =====
   * ==>  Preparing: select * from t_user where id = ?
   * ==> Parameters: 20(Long)
   * <==    Columns: id, name
   * <==        Row: 20, mybatis
   * <==      Total: 1
   * {"id":20,"name":"mybatis"}
   *
   * 第一次查询的时候调用了数据库查询，然后新增一条数据，这时候会将缓存数据清空，最后再次查询这条数据的时候，会重新去数据库查询
   *
   */
  private static void onlyOneSessionAndAdd() throws Exception {

    InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession(true);
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    System.out.println(JSONObject.toJSONString(mapper.getUser(20L)));

    System.out.println("===== 开始新增一条 user 数据 =====");
    mapper.addUser(new User(30L, "mybatis"));

    System.out.println("===== 再次查询 user 数据 =====");
    System.out.println(JSONObject.toJSONString(mapper.getUser(20L)));
  }


  /**
   * 两个 session，在 session1 查询后，session2 对数据进行更改，然后再用 session1 查询，以此来判断 mybatis 的缓存是 session 级别的
   *
   * 运行结果如下：
   * ==>  Preparing: select * from t_user where id = ?
   * ==> Parameters: 20(Long)
   * <==    Columns: id, name
   * <==        Row: 20, 重新修改了数据
   * <==      Total: 1
   * {"id":20,"name":"重新修改了数据"}
   * ===== 使用 sqlSession2 改变 user 数据 =====
   * Opening JDBC Connection
   * ==>  Preparing: update t_user set name = ? where id = ?
   * ==> Parameters: 重新修改了数据(String), 20(Long)
   * <==    Updates: 1
   * ===== 使用 sqlSession1 再次查询 user 数据 =====
   * {"id":20,"name":"重新修改了数据"}
   * ===== 使用 sqlSession3 查询 user 数据 =====
   * Opening JDBC Connection
   * ==>  Preparing: select * from t_user where id = ?
   * ==> Parameters: 20(Long)
   * <==    Columns: id, name
   * <==        Row: 20, 重新修改了数据
   * <==      Total: 1
   * {"id":20,"name":"重新修改了数据"}
   *
   * 从上面的日志可以得出，mybatis 的缓存是 session 级别，同一个 session 内的数据会缓存，不同 session 内的数据不会缓存，
   * sqlSession1 缓存了数据，sqlSession2 改变数据，sqlSession1 再次查询数据，会从缓存中获取数据，这时候获取的就是脏数据了
   */
  private static void twoSession() throws Exception {

    InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
    SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
    SqlSession sqlSession3 = sqlSessionFactory.openSession(true);

    UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
    System.out.println(JSONObject.toJSONString(mapper1.getUser(20L)));

    System.out.println("===== 使用 sqlSession2 改变 user 数据 =====");
    UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
    mapper2.updateUserById(new User(20L, "重新修改了数据"));

    System.out.println("===== 使用 sqlSession1 再次查询 user 数据 =====");
    System.out.println(JSONObject.toJSONString(mapper1.getUser(20L)));

    System.out.println("===== 使用 sqlSession3 查询 user 数据 =====");
    UserMapper mapper3 = sqlSession3.getMapper(UserMapper.class);
    System.out.println(JSONObject.toJSONString(mapper3.getUser(20L)));
  }
}
