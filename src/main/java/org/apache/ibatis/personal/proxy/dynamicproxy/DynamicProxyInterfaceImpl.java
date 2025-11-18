package org.apache.ibatis.personal.proxy.dynamicproxy;

public class DynamicProxyInterfaceImpl implements DynamicProxyInterface {
  
  @Override
  public void sayHi() {
    System.out.println("Hello! This is the implementation of sayHi method.");
  }
  
  @Override
  public String getUserById(int id) {
    return "User{id=" + id + ", name='实现类中的用户'}";
  }
  
  @Override
  public int insertUser(String name, String email) {
    System.out.println("在实现类中插入用户: " + name + ", " + email);
    return 1;
  }
}