package org.apache.ibatis.personal.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcTest1 {

  public static void main(String[] args) {
    // 43.134.208.111

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      // 1. 加载驱动
      Class.forName("com.mysql.cj.jdbc.Driver"); // 由于有 SPI 机制，不需要手动加载驱动
      // 2. 获取连接
      connection = DriverManager.getConnection("jdbc:mysql://43.134.208.111:8816/mybatis-study", "root", "yanshouqiang88");

      // 3. sql
      String sql = "select * from t_user where id = ?";

      // 4. 获得 sql 执行者
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, 20);

      // 5. 执行 sql
      preparedStatement.executeQuery();
      System.out.println("执行成功");
      ResultSet resultSet = preparedStatement.getResultSet();

      while (resultSet.next()) {
        System.out.println(resultSet.getInt("id"));
        System.out.println(resultSet.getString("name"));
      }
    } catch (Exception e) {

      e.printStackTrace();
    }
  }
}
