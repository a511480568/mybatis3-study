package org.apache.ibatis.personal.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcGenKey {

  public static void main(String[] args) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      // 1. 加载驱动
      Class.forName("com.mysql.cj.jdbc.Driver"); // 由于有 SPI 机制，不需要手动加载驱动
      // 2. 获取连接
      connection = DriverManager.getConnection("jdbc:mysql://43.134.208.111:8816/mybatis-study", "root", "yanshouqiang88");

      // 3. sql
      String sql = "insert into t_user (name) values (?)";

      // 4. 获得 sql 执行者
      preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, "自增长主键测试");

      // 5. 执行 sql
      preparedStatement.executeUpdate();
      System.out.println("执行成功");
      ResultSet resultSet = preparedStatement.getGeneratedKeys();

      while (resultSet.next()) {
        System.out.println(resultSet.getInt(1));
      }
    } catch (Exception e) {

      e.printStackTrace();
    }
  }
}
