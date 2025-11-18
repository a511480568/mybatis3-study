package org.apache.ibatis.personal.reflection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Animal<T> {

  private T t;

  private String name;

  public List<T> info() {
    List<T> list = new ArrayList<>();
    return list;
  }

  public <K,V> T test(Map<K, V> map) {
    return t;
  }

  public <E> int test1(E e, Set<String> set) {
    return 0;
  }

  public T getT() {
    return t;
  }

  public void setT(T t) {
    this.t = t;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
