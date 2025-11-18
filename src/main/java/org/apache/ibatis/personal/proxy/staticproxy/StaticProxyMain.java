package org.apache.ibatis.personal.proxy.staticproxy;


/**
 * 静态代理类测试
 */
public class StaticProxyMain {

  public static void main(String[] args) {
    UserInterface user = new UserImpl();
    StaticUserProxy proxy = new StaticUserProxy(user);
    proxy.sayHello();
  }
}
