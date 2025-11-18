package org.apache.ibatis.personal.proxy.dynamicproxy;

public interface DynamicProxyInterface {

  void sayHi();
  
  String getUserById(int id);
  
  int insertUser(String name, String email);
}