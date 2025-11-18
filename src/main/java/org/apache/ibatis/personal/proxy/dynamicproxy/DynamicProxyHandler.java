package org.apache.ibatis.personal.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyHandler<T> implements InvocationHandler {

  private T target;

  public DynamicProxyHandler(T target) {
    this.target = target;
  }
  
  /**
   * 创建代理对象的静态方法
   * @param target 被代理的对象
   * @param <T> 泛型类型
   * @return 代理对象
   */
  @SuppressWarnings("unchecked")
  public static <T> T createProxy(T target) {
    DynamicProxyHandler<T> handler = new DynamicProxyHandler<>(target);
    return (T) Proxy.newProxyInstance(
        target.getClass().getClassLoader(),
        target.getClass().getInterfaces(),
        handler
    );
  }

  /**
   *
   * @param proxy 代理对象
   * @param method 被代理的方法
   * @param args 被代理方法的参数
   */
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.println("动态代理类开始调用前");
    
    // 如果target为null，则直接返回
    if (target == null) {
      System.out.println("没有目标对象，直接返回");
      return null;
    }
    
    Object ans = method.invoke(target, args);
    System.out.println("动态代理类开始调用后");
    return ans;
  }
}