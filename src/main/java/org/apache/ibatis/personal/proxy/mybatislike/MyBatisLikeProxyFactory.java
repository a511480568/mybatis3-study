package org.apache.ibatis.personal.proxy.mybatislike;

import java.lang.reflect.Proxy;

/**
 * 类似 MyBatis 的代理工厂
 */
public class MyBatisLikeProxyFactory {
    
    @SuppressWarnings("unchecked")
    public static <T> T createMapperProxy(Class<T> mapperInterface) {
        MyBatisLikeMapperProxy<T> handler = new MyBatisLikeMapperProxy<>(mapperInterface);
        return (T) Proxy.newProxyInstance(
            mapperInterface.getClassLoader(),
            new Class[]{mapperInterface},
            handler
        );
    }
}