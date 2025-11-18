package org.apache.ibatis.personal.reflection;

public class School {

  private String name;

  private Integer age;

  private String address;

  public School(String name) {
    this.name = name;
  }

  public School() {
  }

  public School(String name, int age, String address) {
    this.name = name;
    this.age = age;
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
