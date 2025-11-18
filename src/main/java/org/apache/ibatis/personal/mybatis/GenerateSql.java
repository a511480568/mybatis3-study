package org.apache.ibatis.personal.mybatis;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.jdbc.SqlRunner;

import java.sql.Connection;
import java.sql.DriverManager;

public class GenerateSql {

  public static void main(String[] args) throws Exception {

    String insertSql = new SQL().INSERT_INTO("t_user").VALUES("name", "?").toString();
    System.out.println(insertSql);

    Connection connection = DriverManager.getConnection("jdbc:mysql://43.155.6.15:8816/mybatis-study", "root", "yanshouqiang88");

    SqlRunner sqlRunner = new SqlRunner(connection);
    int count = sqlRunner.insert(insertSql, "张三");
    System.out.println("使用 sqlRunner 操作数据库插入数据后影响到行数：" + count);
  }
}
