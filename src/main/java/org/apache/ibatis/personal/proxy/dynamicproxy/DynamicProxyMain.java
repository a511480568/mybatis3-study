package org.apache.ibatis.personal.proxy.dynamicproxy;

import java.lang.reflect.Proxy;

public class DynamicProxyMain {

  public static void main(String[] args) {
    System.out.println("=== 传统方式：需要实现类 ===");
    // 创建目标对象（必须有实现类）
    DynamicProxyInterface target = new DynamicProxyInterfaceImpl();
    
    // 方式1：使用自定义的createProxy方法创建代理对象
    System.out.println("=== 使用自定义createProxy方法创建代理 ===");
    DynamicProxyInterface proxy1 = DynamicProxyHandler.createProxy(target);
    proxy1.sayHi();
    
    // 方式2：直接使用Proxy.newProxyInstance创建代理对象
    System.out.println("\n=== 直接使用Proxy.newProxyInstance创建代理 ===");
    DynamicProxyInterface proxy2 = (DynamicProxyInterface) Proxy.newProxyInstance(
        target.getClass().getClassLoader(),
        new Class[]{DynamicProxyInterface.class},
        new DynamicProxyHandler<>(target)
    );
    proxy2.sayHi();
    
    // 验证是否是代理对象
    System.out.println("\n=== 验证代理对象 ===");
    System.out.println("proxy1是代理对象: " + Proxy.isProxyClass(proxy1.getClass()));
    System.out.println("proxy2是代理对象: " + Proxy.isProxyClass(proxy2.getClass()));
    
    System.out.println("\n\n=== MyBatis 风格：不需要实现类 ===");
    // 演示 MyBatis 风格的代理（不需要实现类）
    org.apache.ibatis.personal.proxy.mybatislike.MyBatisLikeMain.main(args);
  }
}