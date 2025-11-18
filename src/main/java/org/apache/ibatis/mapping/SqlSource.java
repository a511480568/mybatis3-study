/*
 *    Copyright 2009-2023 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.mapping;

/**
 * Represents the content of a mapped statement read from an XML file or an annotation. It creates the SQL that will be
 * passed to the database out of the input parameter received from the user.
 *
 * 这个代表的是数据库操作标签中的语句，比如：
 *     <select id="getUser" resultMap="usermap">
 *         select * from t_user where id = #{id}
 *     </select>
 * 在上面的语句中 SqlSource 就是 select * from t_user where id = #{id}，而 {@link MappedStatement} 代表的是整个含有标签语句，将上面含有标签的语句进行处理完后就是 BoundSql
 *
 * 该接口有四个实现类：
 * 1. {@link org.apache.ibatis.scripting.xmltags.DynamicSqlSource} 该实现类表示的是动态 sql，即 mapper.xml 文件中含有 <if> 或者 ${} 标签
 * 2. {@link org.apache.ibatis.scripting.defaults.RawSqlSource} 该实现类表示的是静态 sql，即 mapper.xml 文件中没有 <if> 或者 ${} 标签，但可能有 #{}
 * 3. {@link org.apache.ibatis.builder.StaticSqlSource} 静态语句，可能含有 ? 可以直接提交给数据库执行的，通过该类中的 getBoundSql 方法可以获取到 BoundSql 对象
 * 4. {@link org.apache.ibatis.builder.annotation.ProviderSqlSource} 该类是 mapper 接口注解中的 sql 语句的实现类
 *
 * @author Clinton Begin
 */
public interface SqlSource {

  BoundSql getBoundSql(Object parameterObject);

}
