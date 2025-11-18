package org.apache.ibatis.personal.proxy.staticproxy;

public class UserImpl implements UserInterface{
  @Override
  public void sayHello() {
    System.out.println("static proxy say hello");
  }
}
