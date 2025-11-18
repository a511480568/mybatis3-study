package org.apache.ibatis.personal.proxy.staticproxy;

public class StaticUserProxy implements UserInterface{

  private UserInterface userInterface;

  public StaticUserProxy(UserInterface userInterface) {
    this.userInterface = userInterface;
  }

  @Override
  public void sayHello() {
    System.out.println("invoke actual method before");
    userInterface.sayHello();
    System.out.println("invoke actual method after");
  }
}
