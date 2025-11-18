package org.apache.ibatis.personal.proxy.mybatislike;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 类似 MyBatis 的 MapperProxy 实现
 * 不需要目标实现类，而是将方法调用转换为其他操作
 */
public class MyBatisLikeMapperProxy<T> implements InvocationHandler {
    
    private Class<T> mapperInterface;
    
    // 模拟 MyBatis 中的 SQL 语句映射
    private static final Map<String, String> SQL_MAPPINGS = new HashMap<>();
    
    static {
        SQL_MAPPINGS.put("getUserById", "SELECT * FROM users WHERE id = ?");
        SQL_MAPPINGS.put("insertUser", "INSERT INTO users (name, email) VALUES (?, ?)");
        SQL_MAPPINGS.put("sayHi", "SELECT 'Hello from database' as message");
    }
    
    public MyBatisLikeMapperProxy(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        
        // 处理 Object 类的方法
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        
        // 处理默认方法（Java 8+）
        if (method.isDefault()) {
            return invokeDefaultMethod(proxy, method, args);
        }
        
        // 将方法调用转换为 SQL 执行（模拟）
        return executeSql(methodName, args);
    }
    
    /**
     * 模拟执行 SQL 语句
     * @param methodName 方法名
     * @param args 参数
     * @return 执行结果
     */
    private Object executeSql(String methodName, Object[] args) {
        String sql = SQL_MAPPINGS.get(methodName);
        if (sql != null) {
            System.out.println("执行 SQL: " + sql);
            System.out.println("参数: " + (args == null ? "无" : java.util.Arrays.toString(args)));
            
            // 模拟返回结果
            switch (methodName) {
                case "sayHi":
                    return "Hello! This message comes from database execution!";
                case "getUserById":
                    return "User{id=1, name='John'}";
                case "insertUser":
                    return 1; // 影响行数
                default:
                    return "执行完成";
            }
        } else {
            throw new RuntimeException("找不到对应的方法映射: " + methodName);
        }
    }
    
    private Object invokeDefaultMethod(Object proxy, Method method, Object[] args) throws Throwable {
        // Java 8+ 默认方法处理逻辑（简化版）
        throw new UnsupportedOperationException("默认方法处理暂未实现");
    }
}