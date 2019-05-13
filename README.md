# spring-boot-mybatis-druid
- 本地环境
> JDK 版本： 1.8.0_202
> 系统： MacOS Mojave 10.14.4
> 工具 IDEA 2019.1.2
> Github: [https://github.com/ilssio/spring-boot-mybatis-druid](https://github.com/ilssio/spring-boot-mybatis-druid)

- 注意： 解释较少，直接看注释就好, 

# pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.4.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>io.ilss</groupId>
    <artifactId>spring-boot-mybatis-druid</artifactId>
    <version>1.0</version>
    <name>spring-boot-mybatis-druid</name>
    <description>spring-boot-mybatis-druid</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.0.1</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>
        
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

# application.yml
```yml
spring:
  application:
    name: spring-boot-mybatis-druid
  datasource:
    druid:
      url: jdbc:mysql://localhost:3306/test?useSSL=true&characterEncoding=UTF-8
      username: root
      password: feng1104
      # 配置初始化大小（默认0）、最小、最大（默认8）
      initial-size: 1
      min-idle: 1
      max-active: 20
      # 配置获取连接等待超时的时间
      max-wait: 60000
      # 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大。 默认为false
      pool-prepared-statements: true
      # 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。
      max-open-prepared-statements: 20
      # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
      time-between-eviction-runs-millis: 60000
      # 配置一个连接在池中最小和最大生存的时间，单位是毫秒
      min-evictable-idle-time-millis: 300000
      max-evictable-idle-time-millis: 900000

      # 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。
      # 如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
      validation-query: SELECT 1
      # 申请连接时执行validationQuery检测连接是否有效 默认为true
      test-on-borrow: true
      # 归还连接时执行validationQuery检测连接是否有效 默认为false
      test-on-return: false
      # 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
      test-while-idle: true
mybatis:
  mapper-locations: classpath:mapper/*.xml

```

# 数据库对应类：User
```java
package io.ilss.dataobject;

import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @author : feng
 * @description: User
 * @date : 2019-05-13 11:44
 * @version: : 1.0
 */
@Data
public class User {
    private Integer id;

    private String username;

    private String password;
}
```

# DAO层：UserDAO.java 和 UserMapper.xml
- UserDAO.java
```java
package io.ilss.dao;

import io.ilss.dataobject.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : feng
 * @description: UserDAO
 * @date : 2019-05-13 11:46
 * @version: : 1.0
 */
@Repository
public interface UserDAO {

    List<User> listAll();
}

```
- UserMapper.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="io.ilss.dao.UserDAO">
    <select id="listAll" resultType="io.ilss.dataobject.User">
    select * from user
  </select>
</mapper>
```

# 测试接口 
```java
package io.ilss.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import io.ilss.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : feng
 * @description: DruidStatController
 * @date : 2019-05-13 11:42
 * @version: : 1.0
 */
@RestController
public class TestController {

    @Autowired
    UserDAO userDAO;
	
    @GetMapping("/druid/stat")
    public Object druidStat(){
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }

    @GetMapping("/list/user")
    public Object listUser() {
        return userDAO.listAll();
    }
}
```

# Notice：
- 网上有的整合示例，有@Bean 然后 创建Datasource ，实测，使用mybatis-spring-boot-starter依赖不需要去创建
- 另外datasource配置下 加不加druid都可以。多一层而已。druid对两个都支持。（官方说的）
- 访问`localhost:8080/druid/stat`可以进入Druid Monitor  如下：
![Druid Monitor](https://img-blog.csdnimg.cn/20190513134208193.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2lsbzExNA==,size_16,color_FFFFFF,t_70)
