package org.apache.ibatis.personal.proxy.mybatislike;

import org.apache.ibatis.personal.proxy.dynamicproxy.DynamicProxyInterface;

public class MyBatisLikeMain {
    
    public static void main(String[] args) {
        System.out.println("=== 演示 MyBatis 风格的动态代理 ===");
        
        // 创建代理对象，不需要实现类
        DynamicProxyInterface mapper = MyBatisLikeProxyFactory.createMapperProxy(DynamicProxyInterface.class);
        
        // 调用方法
        mapper.sayHi();
        System.out.println();
        
        String user = mapper.getUserById(1);
        System.out.println("查询结果: " + user);
        System.out.println();
        
        int result = mapper.insertUser("张三", "zhangsan@example.com");
        System.out.println("插入结果: " + result);
    }
}