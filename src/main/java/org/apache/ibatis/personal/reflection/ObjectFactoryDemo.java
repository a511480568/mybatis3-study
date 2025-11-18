package org.apache.ibatis.personal.reflection;

import com.alibaba.fastjson2.JSONObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @see org.apache.ibatis.reflection.factory.ObjectFactory
 */
public class ObjectFactoryDemo {

  public static void main(String[] args) {
    ObjectFactory objectFactory = new DefaultObjectFactory();
    School school = objectFactory.create(School.class);
    System.out.println("无参构造: " + JSONObject.toJSONString(school));

    List<Class<?>> constructorArgTypes = new ArrayList<>();
    constructorArgTypes.add(String.class);

    List<Object> constructorArgs = new ArrayList<>();
    constructorArgs.add("小学");
    School hasParamConstructor = objectFactory.create(School.class, constructorArgTypes, constructorArgs);
    System.out.println("有参构造: " + JSONObject.toJSONString(hasParamConstructor));
  }
}
