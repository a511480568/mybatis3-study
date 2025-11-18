package org.apache.ibatis.personal.reflection;

import com.alibaba.fastjson2.JSONObject;
import org.apache.ibatis.reflection.TypeParameterResolver;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * 泛型类型测试
 * @see org.apache.ibatis.reflection.TypeParameterResolver
 */
public class TypeParameterDemo {

  public static void main(String[] args) throws Exception {

    Type testReturnType = TypeParameterResolver.resolveReturnType(Cat.class.getMethod("test", Map.class), Cat.class);
    System.out.println(testReturnType);

    Type infoReturnType = TypeParameterResolver.resolveReturnType(Cat.class.getMethod("info"), Cat.class);
    System.out.println(infoReturnType);

    Type[] paramTypes = TypeParameterResolver.resolveParamTypes(Cat.class.getMethod("test1", Object.class, Set.class), Cat.class);
    System.out.println("=================");
    for (Type paramType : paramTypes) {
      System.out.println(paramType);
    }
  }
}
