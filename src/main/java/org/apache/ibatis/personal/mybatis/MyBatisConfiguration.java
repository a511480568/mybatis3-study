package org.apache.ibatis.personal.mybatis;

import com.alibaba.fastjson2.JSONObject;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;

import java.io.InputStream;
import java.util.List;

public class MyBatisConfiguration {

  public static void main(String[] args) throws Exception {

    InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
    // 在调用 .build(inputStream) 后，SqlSessionFactory 会创建 Configuration 对象，这里面也会解析配置文件
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession();

    // 从 sqlSession 中拿到 Configuration
    Configuration configuration = sqlSession.getConfiguration();
    System.out.println(JSONObject.toJSONString(configuration.getMapperRegistry()));

    MappedStatement mappedStatement = configuration.getMappedStatement("org.apache.ibatis.personal.mapper.UserMapper.getUser");
//    System.out.println(JSONObject.toJSONString(mappedStatement));

    // 获取 Executor
    Executor executor = configuration.newExecutor(new JdbcTransaction(sqlSession.getConnection()), ExecutorType.REUSE);

    // 执行 sql
    List<Object> resultList = executor.query(mappedStatement, null, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
    System.out.println("使用 executor 执行查询的结果: " + JSONObject.toJSONString(resultList));
  }
}
