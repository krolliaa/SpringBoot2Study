# `SpringBoot`基础篇

- 小白：初步掌握`SpringBoot`程序的开发流程，能够基于`SpringBoot`实现基础`SSM`框架整合

- 初学者：掌握各式各样的第三方技术与`SpringBoot`整合的方案、积累基于`SpringBoot`的实战开发经验

- 开发者：提升对`Spring`以及`SpringBoot`原理的理解层次、基于原理理解基础上，实现自主研发基于`SpringBoot`整合任意技术的开发方式

**`SpringBoot`就是用于简化`Spring`应用的初始搭建以及开发过程。**

## 四种方式创建`SpringBoot`项目

创建`SpringBoot`可以到`Spring`官网中去下载，这样可以避免联网下载的问题。如果官网也进不去，可以使用阿里云的版本，将`IDEA`中创建的`URL`更改为：`https://start.aliyun.com`

如果压根没有网络【`Maven`坐标已经下载好】，可以自己创建`Maven`工程，赋值粘贴`pom.xml`中的内容。然后创建`SpringBootDemo01Application.java`：

```java
package com.kk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootDemo01Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemo01Application.class, args);
    }
}
```

- 一种通过`IDEA`的`https://start.spring.io`
- 一种通过`https://start.spring.io`官网
- 一种通过`IDEA`的`https://start.aliyun.com`
- 一种通过手动方式

如果创建的项目有些文件并不想看到可以通过`settings ---> Editor ---> File Types`将其隐藏忽略掉。

## 解析`SpringBoot`项目

- **<font color="red">`parent`【解决配置问题】</font>**

  ```xml
  <parent>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-parent</artifactId>
      <version>2.7.1</version>
      <relativePath/> <!-- lookup parent from repository -->
  </parent>
  ```

  进入到`spring-boot-starter-parent`：

  ```xml
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>2.7.1</version>
  </parent>
  ```

  进入到`spring-boot-dependencies`，可以看到在这里定义了一系列的版本信息以及一系列的坐标引用信息。这样做就做到了统一集中管理，大大提升效率。**不同的`SpringBoot`版本会导致内置依赖版本的变化。**

- **<font color="red">`starter`【解决配置问题】</font>**

  把你所需要的技术全部都给你整合好，不用你一个个的自己去搞。`starter`包含了若干个`pom.xml`坐标依赖信息。它都帮我们搞定了。**这样做就可以减少我们自己去定义依赖配置，达到简化配置的目的。**

- **<font color="red">引导类【用于启动项目，谁来启动`Web`服务器？】</font>**

  引导类的作用就是加载`Bean`，是整个`Boot`工程的执行入口。

  ```java
  package com.kk;
  
  import com.kk.controller.BookController;
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  import org.springframework.context.ConfigurableApplicationContext;
  
  @SpringBootApplication
  public class SpringBootDemo0104Application {
      public static void main(String[] args) {
          ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(SpringBootDemo0104Application.class, args);
          BookController bean = configurableApplicationContext.getBean(BookController.class);
          System.out.println("bean ============> " + bean);
      }
  }
  
  bean ============> com.kk.controller.BookController@4a325eb9
  ```

- **<font color="red">内嵌`Tomcat`</font>**

  `SpringBoot`内置的原因就是`Tomcat`搞成了一个对象。我们甚至可以把`Tomcat`换掉改使用`Jetty`服务器。`Jetty`服务器`Google`公司就在用，非常的轻量，但是负载性能远不如`Tomcat`。还有`undertow`，负载性能勉强跑赢`tomcat`。

  ```xml
  <dependency>
  	<groupId>org.springframework.boot</groupId>
  	<artifactId>spring-boot-starter-web</artifactId>
  	<exclusions>
  		<exclusion>
  			<groupId>org.springframework.boot</groupId>
  			<artifactId>spring-boot-starter-tomcat</artifactId>
  		</exclusion>
  	</exclusions>
  </dependency>
  <dependency>
  	<groupId>org.springframework.boot</groupId>
  	<artifactId>spring-boot-starter-jetty</artifactId>
  </dependency>
  ```

  ![](https://img-blog.csdnimg.cn/6df607f248cd4e579545b5dddc4b9a4d.png)

## 配置`SpringBoot`

用什么技术才能配置什么属性，不用配不了。

修改服务器端口`banner`：

```properties
#修改服务器端口
server.port=80
```

修改标识`banner`：

```properties
#修改标识banner
spring.main.banner-mode=console
spring.banner.image.location=1.png
```

配置日志：

```properties
#配置日志
logging.level.root=info/debug
```

可以在`https://spring.io`中找到关于`SpringBoot - Application Properties`所有的配置属性。**配置属性不是想配就配的，跟你用什么技术有关。**如果你不用`web`你是配置不了`server.port`的。

`SpringBoot`不仅提供了`properties`格式配置文件，还提供了`yml`和`yaml`【这两个同属于一个，通常使用`yml`格式配置文件（数据和冒号之间要用空格）】

配置文件的加载优先级：`properties > yml > yaml`【有`properties`会用`properties`的】，**但是也要注意：如果没有重叠的属性配置是会全部保留的。**

在`yml`或者`yaml`写的时候没有提示？这是因为`IDEA`没有将其识别到`Spring`中，可以通过`ctrl + shift + alt + s ===> Factes ===> 选中项目 ===> 按住小叶子 ===> 添加 yml/yaml` 即可。

## `yaml`文件

### `yaml`读取单个数据

读取`yaml`中的单个数据`@Value + ${}`：

```yaml
country: China

users:
  name: ZhangSan
  age: 100

user1:
  - name: LiSi
    age: 11
  - name: WangWu
    age: 22

likes: [game,music,sleep]

likes1:
  - game
  - music
  - sleep

server:
  port: 80
```

```java
package com.kk.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Value(value = "${country}")
    private String country;

    @Value(value = "${likes[0]}")
    private String likes01;

    @Value(value = "${likes1[0]}")
    private String likes02;

    @Value(value = "${user1[0].name}")
    private String name;

    @GetMapping(value = "/yml")
    public String getYml() {
        return country + likes01 + likes02 + name;
    }

    @GetMapping
    public String getById() {
        System.out.println("SpringBoot Running...");
        return "SpringBoot Running...";
    }
}
```

### `yaml`引用变量

`yaml`文件中可以使用`${}`的方式引用变量：

如果需要使用转义字符，需要加上双引号使其变为字符串。

```yaml
baseDir: c:\windows
tmpDir: ${baseDir}\temp
```

```java
@GetMapping(value = "/yml")
public String getYml() {
    return tmpDir + country + likes01 + likes02 + name;
}
```

### `yaml`读取多个数据

#### 全部读取到`Environment`对象中

`SpringBoot`会将所有的配置全部封装到`Environment`对象中`private Environment env;`使用`@Autowired`注解可以将其自动配置上对象。

```java
@Autowired
private Environment environment;

@GetMapping(value = "/yml")
public String getYml() {
    System.out.println(environment.getProperty("server.port"));
    System.out.println(environment.getProperty("likes[0]"));
    return tmpDir + country + likes01 + likes02 + name;
}
```

#### 读取到单个对象中

- 使用`@ConfigurationProperties`注解绑定配置信息到封装类中

  ```yaml
  datasource:
    driver: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/ssm?useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
  ```

  封装类如下：

  ```java
  package com.kk.pojo;
  
  import org.springframework.boot.context.properties.ConfigurationProperties;
  import org.springframework.stereotype.Component;
  
  @Component
  @ConfigurationProperties(prefix = "datasource")
  public class MyDataSource {
      private String driver;
      private String url;
      private String username;
      private String password;
  
      public MyDataSource() {
      }
  
      public MyDataSource(String driver, String url, String username, String password) {
          this.driver = driver;
          this.url = url;
          this.username = username;
          this.password = password;
      }
  
      public String getDriver() {
          return driver;
      }
  
      public void setDriver(String driver) {
          this.driver = driver;
      }
  
      public String getUrl() {
          return url;
      }
  
      public void setUrl(String url) {
          this.url = url;
      }
  
      public String getUsername() {
          return username;
      }
  
      public void setUsername(String username) {
          this.username = username;
      }
  
      public String getPassword() {
          return password;
      }
  
      public void setPassword(String password) {
          this.password = password;
      }
  
      @Override
      public String toString() {
          return "MyDataSource{" +
                  "driver='" + driver + '\'' +
                  ", url='" + url + '\'' +
                  ", username='" + username + '\'' +
                  ", password='" + password + '\'' +
                  '}';
      }
  }
  ```

  ```java
  package com.kk.controller;
  
  import com.kk.pojo.MyDataSource;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.beans.factory.annotation.Value;
  import org.springframework.core.env.Environment;
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  @RequestMapping(value = "/books")
  public class BookController {
  
      @Value(value = "${country}")
      private String country;
  
      @Value(value = "${likes[0]}")
      private String likes01;
  
      @Value(value = "${likes1[0]}")
      private String likes02;
  
      @Value(value = "${user1[0].name}")
      private String name;
  
      @Value(value = "${tmpDir}")
      private String tmpDir;
  
      @Autowired
      private Environment environment;
  
      @Autowired
      private MyDataSource myDataSource;
  
      @GetMapping(value = "/yml")
      public String getYml() {
          System.out.println(environment.getProperty("server.port"));
          System.out.println(environment.getProperty("likes[0]"));
          System.out.println(myDataSource.toString());
          return tmpDir + country + likes01 + likes02 + name;
      }
  
      @GetMapping
      public String getById() {
          System.out.println("SpringBoot Running...");
          return "SpringBoot Running...";
      }
  }
  ```

  ```java
  @Autowired
  private MyDataSource myDataSource;
  
  @GetMapping(value = "/yml")
  public String getYml() {
      return myDataSource.toString();
  }
  ```

  注意这里的`@ConfigurationProperties(prefix = "")`注解中的`prefix`属性值需要全部小写，否则会报错。

## 整合第三方技术

重在学习整合思想，拿到任何一个技术，你要怎么整合？这才是要学习的内容。

### 整合`JUnit`

导入依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

`@SpringBootTest`和`@Test`帮我们完成了测试任务，只需要导入对象即可使用。

```java
package com.kk;

import com.kk.dao.BookDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootDemo03JUnitApplicationTests {

    @Autowired
    private BookDao bookDao;

    @Test
    void contextLoads() {
        bookDao.save();
        System.out.println("test...");
    }
}
```

该测试类的位置需要在引导类所在的包或者子包下，否则需要设置`@SpringBootTest(classes = Application.class)`指定配置类【其实这种情况就是默认省略掉了而已】。

### 整合`MyBatis`

- 核心配置：数据库连接相关信息（连谁？连什么？）
- 配置方式：注解还是配置文件

创建项目时选择了`SQL --> MyBatis Framework + MySQL Driver`，在`pom.xml`中发生了一些变化：

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.2.2</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

配置`application.yml`：

```yaml
#配置MyBatis相关信息
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/ssm?useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
```

创建`Book`实体类：

```java
package com.kk.pojo;

public class Book {
    private Integer id;
    private String type;
    private String name;
    private String description;

    public Book() {
    }

    public Book(Integer id, String type, String name, String description) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
```

创建`BookDao`类：

```java
package com.kk.dao;

import com.kk.pojo.Book;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface BookDao {
    @Select("select * from tbl_book where id = ${id}")
    public abstract Book getById(Integer id);
}
```

创建测试类：

```java
package com.kk;

import com.kk.dao.BookDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootDemo04MyBatisApplicationTests {

    @Autowired
    private BookDao bookDao;

    @Test
    void contextLoads() {
        System.out.println(bookDao.getById(1));
    }
}
```

### 整合`MyBatis-Plus`

手动导入必要的坐标【`SpringBoot`没有整合】：

```xml
<!-- https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-boot-starter -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.2</version>
</dependency>
```

创建`Book`实体类：

```java
package com.kk.pojo;

public class Book {
    private Integer id;
    private String type;
    private String name;
    private String description;

    public Book() {
    }

    public Book(Integer id, String type, String name, String description) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
```

创建`BookDao`【`MyBatisPlus`直接继承`BaseMapper`接口即可】

```java
package com.kk.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kk.pojo.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookDao extends BaseMapper<Book> {
}
```

测试

```java
package com.kk;

import com.kk.dao.BookDao;
import com.kk.pojo.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringBootDemo05MyBatisPlusApplicationTests {

    @Autowired
    private BookDao bookDao;

    @Test
    void contextLoads() {
        Book book = bookDao.selectById(12);
        System.out.println(book);
        List<Book> bookList = bookDao.selectList(null);
        System.out.println(bookList);
    }
}
```

`yaml`配置

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/ssm?useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
mybatis-plus:
  global-config:
    db-config:
      table-prefix: tbl_
```

### 整合`Druid`

导入必要的坐标：

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.2.11</version>
</dependency>
```

配置`application.yml`文件：

```yaml
spring:
  datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/ssm?useSSL=false&serverTimezone=Asia/Shanghai
      username: root
      password: 123456
mybatis-plus:
  global-config:
    db-config:
      table-prefix: tbl_
```

创建实体类【直接复制粘贴】

创建`BookDao`【直接复制粘贴】

测试

```java
package com.kk;

import com.kk.dao.BookDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootDemo06DruidApplicationTests {

    @Autowired
    private BookDao bookDao;

    @Test
    void contextLoads() {
        System.out.println(bookDao.selectById(10));
    }

}
```

## `SSMP`整合案例

- 实体类开发：使用`Lombok`快速制作实体类
- `Dao`开发：整合`MyBatisPlus`，进行数据层测试类
- `Service`开发：基于`MyBatisPlus`进行增量开发，使用`PostMan`测试接口功能
- `Controller`开发：基于`Restful`开发，使用`PostMan`测试接口功能
- `Controller`开发：前后端开发协议制作
- 页面开发：基于`Vue+Element`制作，前后端联调，页面数据处理，页面消息处理
  - 列表、增删改查、分页
- 项目异常处理
- 按条件查询：页面功能调整、`Controller`修正功能、`Service`修正功能

1. 创建该模块`SpringBoot_demo07_SSMP`，配置好`pom.xml`和`application.yml`

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
       <parent>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-parent</artifactId>
           <version>2.7.1</version>
           <relativePath/> <!-- lookup parent from repository -->
       </parent>
       <groupId>com.kk</groupId>
       <artifactId>SpringBoot_demo07_SSMP</artifactId>
       <version>0.0.1-SNAPSHOT</version>
       <properties>
           <java.version>1.8</java.version>
       </properties>
       <dependencies>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
           </dependency>
           <dependency>
               <groupId>com.baomidou</groupId>
               <artifactId>mybatis-plus-boot-starter</artifactId>
               <version>3.5.2</version>
           </dependency>
           <dependency>
               <groupId>com.alibaba</groupId>
               <artifactId>druid-spring-boot-starter</artifactId>
               <version>1.2.11</version>
           </dependency>
           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <scope>runtime</scope>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-test</artifactId>
               <scope>test</scope>
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

   ```yaml
   server:
     port: 80
   spring:
     datasource:
       druid:
         driver-class-name: com.mysql.cj.jdbc.Driver
         url: jdbc:mysql://localhost:3306/ssm?useSSL=false&serverTimezone=Asia/Shanghai
         username: root
         password: 123456
   mybatis-plus:
     global-config:
       db-config:
         table-prefix: tbl_
   ```

2. 使用`lombok`快速开发实体类：

   `SpringBoot`已经集合了`lombok`，所以不用添加版本也是可以的

   ```java
   package com.kk.pojo;
   
   import lombok.*;
   
   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   public class Book {
       private Integer id;
       private String type;
       private String name;
       private String description;
   }
   ```

3. 测试整合`MyBatisPlus`、`BookMapper`

   ```java
   package com.kk.mapper;
   
   import com.baomidou.mybatisplus.core.mapper.BaseMapper;
   import com.kk.pojo.Book;
   import org.apache.ibatis.annotations.Mapper;
   
   @Mapper
   public interface BookMapper extends BaseMapper<Book> {
   }
   ```

   ```java
   package com.kk;
   
   import com.kk.mapper.BookMapper;
   import com.kk.pojo.Book;
   import org.junit.jupiter.api.Test;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.context.SpringBootTest;
   
   import java.util.List;
   
   @SpringBootTest
   class SpringBootDemo07SsmpApplicationTests {
   
       @Autowired
       private BookMapper bookMapper;
   
       @Test
       void contextLoads() {
       }
   
       @Test
       void testSelectById() {
           Book book = bookMapper.selectById(10);
           System.out.println(book);
       }
   
       @Test
       void testSelectAll() {
           List<Book> bookList = bookMapper.selectList(null);
           bookList.stream().forEach(System.out::println);
       }
   
       @Test
       void testAddBook() {
           Book book = new Book();
           book.setType("Java");
           book.setName("《Java 核心技术卷》");
           book.setDescription("Java 秘器");
           bookMapper.insert(book);
       }
   
       @Test
       void testDeleteBook() {
           bookMapper.deleteById(36);
       }
   
       @Test
       void testUpdateBook() {
           Book book = new Book();
           book.setId(36);
           book.setType("JavaEE");
           book.setName("《Java 核心技术卷》");
           book.setDescription("Java 秘器");
           bookMapper.update(book, null);
       }
   }
   ```

   配置文件：

   ```yaml
   server:
     port: 80
   spring:
     datasource:
       druid:
         driver-class-name: com.mysql.cj.jdbc.Driver
         url: jdbc:mysql://localhost:3306/ssm?useSSL=false&serverTimezone=Asia/Shanghai
         username: root
         password: 123456
   mybatis-plus:
     global-config:
       db-config:
         table-prefix: tbl_
         id-type: auto
   ```

4. 配置日志信息`application.yml`：

   ```yaml
   server:
     port: 80
   spring:
     datasource:
       druid:
         driver-class-name: com.mysql.cj.jdbc.Driver
         url: jdbc:mysql://localhost:3306/ssm?useSSL=false&serverTimezone=Asia/Shanghai
         username: root
         password: 123456
   mybatis-plus:
     global-config:
       db-config:
         table-prefix: tbl_
         id-type: auto
     configuration:
       log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
   ```

5. 完成分页功能

   - 需要使用配置拦截器`com.kk.config.MyBatisPlusConfig`

     ```java
     package com.kk.config;
     
     import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
     import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
     import org.springframework.context.annotation.Bean;
     import org.springframework.context.annotation.Configuration;
     
     @Configuration
     public class MyBatisPlusPageConfig {
         @Bean
         public MybatisPlusInterceptor mybatisPlusInterceptor() {
             MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
             mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
             return mybatisPlusInterceptor;
         }
     }
     ```

   - 测试分页功能

     ```java
     @Test
     void contextLoads() {
         IPage iPage = new Page(2, 5);
         bookMapper.selectPage(iPage, null);
     }
     ```

   因为之前在`application.yaml`中配置了日志功能，所以可以看见`SQL`语句：

   `Preparing: SELECT id,type,name,description FROM tbl_book LIMIT ?,?`

   出现了`LIMIT`关键字表示分页功能正常使用了。

6. 按条件查询

   `MyBatisPlus`中的查询条件都是使用条件包装器完成的：`QueryWrapper`

   从`QueryWrapper`升级到`LambdaQueryWrapper`，这两个都可以加条件`condition`【第一个形参】从而判定拼装还是不拼装`where`语句，第二个参数则是指定`column`即哪一列，第三个参数则是指定具体的自定义参数。建议优先使用`LambdaQueryWrapper`因为这个可以解决掉`QueryWrapper`直接传入`column`可能产生的变量名错误的问题。

   ```java
   @Test
   void testQueryWrapper() {
       QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
       queryWrapper.like("type", "JavaEE");
       bookMapper.selectList(queryWrapper);
       //也可以使用 LambdaQueryWrapper 解决变量名出错的问题
       //还可以使用 condition ，如果变量为 null 则不使用条件查询
       String type = "JavaEE";
       LambdaQueryWrapper<Book> lambdaQueryWrapper = new LambdaQueryWrapper<>();
       lambdaQueryWrapper.like(type != null, Book::getType, type);
   }
   ```

7. 业务层开发

   接口层`BookService`：

   ```java
   package com.kk.service;
   
   import com.kk.pojo.Book;
   
   import java.util.List;
   
   public interface BookService {
       Boolean save(Book book);
       Boolean update(Book book);
       Boolean delete(Integer id);
       Book getBookById(Integer id);
       List<Book> getAll();
   }
   ```

   接口层实现类`BookServiceImpl`：

   ```java
   package com.kk.service.impl;
   
   import com.kk.mapper.BookMapper;
   import com.kk.pojo.Book;
   import com.kk.service.BookService;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   
   import java.util.List;
   
   @Service
   public class BookServiceImpl implements BookService {
   
       @Autowired
       private BookMapper bookMapper;
   
       @Override
       public Boolean save(Book book) {
           return bookMapper.insert(book) > 0;
       }
   
       @Override
       public Boolean update(Book book) {
           return bookMapper.updateById(book) > 0;
       }
   
       @Override
       public Boolean delete(Integer id) {
           return bookMapper.deleteById(id) > 0;
       }
   
       @Override
       public Book getBookById(Integer id) {
           return bookMapper.selectById(id);
       }
   
       @Override
       public List<Book> getAll() {
           return bookMapper.selectList(null);
       }
   }
   ```

   测试类：

   ```java
   package com.kk;
   
   import com.baomidou.mybatisplus.core.metadata.IPage;
   import com.kk.pojo.Book;
   import com.kk.service.impl.BookServiceImpl;
   import org.junit.jupiter.api.Test;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.context.SpringBootTest;
   
   @SpringBootTest
   public class TestService {
   
       @Autowired
       private BookServiceImpl bookService;
   
       @Test
       void testGetById() {
           System.out.println(bookService.getBookById(1));
       }
   
       @Test
       void TestPage() {
           IPage<Book> iPage = bookService.getPage(2, 10);
           System.out.println(iPage.getCurrent());//获取当前页
           System.out.println(iPage.getSize());//获取当前条数
           System.out.println(iPage.getTotal());//获取总条数
           System.out.println(iPage.getRecords());//获取得到的记录
       }
   }
   ```

   之前写这个现在写这个，都写吐了，换个`pojo`实体类还是这样写，难道就没有什么可以让这个开发更快速，大大提高效率的工具吗？肯定是有的，那就是**使用`MyBatisPlus`自动化开发**。

8. 使用`MyBatisPlus`对业务层接口进行快速开发

   定义`IBookService`继承`IService<>`

   ```java
   package com.kk.service;
   
   import com.baomidou.mybatisplus.extension.service.IService;
   import com.kk.pojo.Book;
   
   public interface IBookService extends IService<Book> {
   }
   ```

   定义实现类`IBookServiceImpl`继承`ServiceImpl`

   ```java
   package com.kk.service.impl;
   
   import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
   import com.kk.mapper.BookMapper;
   import com.kk.pojo.Book;
   import com.kk.service.IBookService;
   
   public class IBookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {
   }
   ```

   测试类：

   ```java
   @Test
   void testMyBatisPlusService() {
       iBookService.getById(11);
   }
   ```

   如果原本的功能不满足需求，可以在原本已经有的功能上做追加，尽量就是不要去重写【改变方法体内容】，可以做重载【同名不同参】也可以重新写一个另外所需要的功能封装成一个方法。

   最好是不要覆盖`ServiceImpl`中的方法，否则你用这个就失去了其意义所在。

9. 开发表现层 ===> `Controller`层

   - 基于`Restful`进行表现层接口开发
   - 使用`Postman`测试表现层接口功能

   `BookController`表现层开发：

   ```java
   package com.kk.controller;
   
   import com.baomidou.mybatisplus.core.metadata.IPage;
   import com.kk.pojo.Book;
   import com.kk.service.impl.IBookServiceImpl;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.web.bind.annotation.*;
   
   import java.util.List;
   
   @RestController
   @RequestMapping("/books")
   public class BookController {
   
       @Autowired
       private IBookServiceImpl iBookService;
   
       @GetMapping
       public List<Book> getAll() {
           return iBookService.list();
       }
   
       @PostMapping
       public Boolean save(@RequestBody Book book) {
           return iBookService.save(book);
       }
   
       @PutMapping
       public Boolean update(@RequestBody Book book) {
           return iBookService.updateById(book);
       }
   
       @DeleteMapping(value = "/{id}")
       public Boolean delete(@PathVariable Integer id) {
           return iBookService.removeById(id);
       }
   
       @GetMapping(value = "/{id}")
       public Book getById(@PathVariable Integer id) {
           return iBookService.getById(id);
       }
   
       @GetMapping(value = "/{current}/{pageSize}")
       public IPage<Book> getPage(@PathVariable Integer current, @PathVariable Integer pageSize) {
           return iBookService.getPage(current, pageSize);
       }
   }
   ```

   `Postman`测试接口是否正常：

   ```java
   查询功能：
   GET http://localhost/books
   GET http://localhost/books/1
   
   增加功能：
   POST JSON http://localhost/books
   {
       "type": "测试数据1",
       "name": "测试数据2",
       "description": "测试数据3"
   }
   
   删除功能：
   DELETE JSON http://localhost/books/37
   
   修改功能：
   PUT JSON http://localhost/books
   {
       "id": "37",
       "type": "测试数据1111111111111",
       "name": "测试数据2",
       "description": "测试数据3"
   }
   
   分页查询：
   GET http://localhost/books/1/10
   ```

10. 解决表现层消息一致性问题

    传递给前端的数据有`JSON`数组，有单个`JSON`数据，还有`Boolean`值，还有分页查询返回的格式。这使得传递给前端的消息格式显得乱七八糟，有没有什么办法可以**统一一个格式**发送给前端呢？

    不是要统一吗？本来我们全部加个`data`，把数据都放到`data`里头，那如果数据返回的是`null`如何处理？

    因为这个`"data":null`有两种情况：

    1. 表示没有返回数据导致返回`null`
    2. 表示中途出现异常导致返回`null`

    所以为了分辨这两种情况，我们有必要还需要加上`flag`标志位，来辨别到底成功了还是失败了。如下图：

    ![](https://img-blog.csdnimg.cn/3fd0e684cd9e42a4a9ade9d933ef121c.png)

    所以我们可以专门封装一个消息模型类用于返回给前端：

    ```java
    @Data
    public class R {
        private Boolean flag;
        private Object data;
    }
    ```

    前端后端都统一使用这个格式传递数据，我们称其为：**<font color="red">前后端数据协议</font>**。

    制作消息协议：

    ```java
    package com.kk.controller.utils;
    
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class MessageAgreement {
        private Boolean flag;
        private Object data;
    
        public MessageAgreement(Boolean flag) {
            this.flag = flag;
        }
    }
    
    ```

    更改表现层：

    ```java
    package com.kk.controller;
    
    import com.baomidou.mybatisplus.core.metadata.IPage;
    import com.kk.controller.utils.MessageAgreement;
    import com.kk.pojo.Book;
    import com.kk.service.impl.IBookServiceImpl;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    
    import java.util.List;
    
    @RestController
    @RequestMapping("/books")
    public class BookController2 {
    
        @Autowired
        private IBookServiceImpl iBookService;
    
        @GetMapping
        public MessageAgreement getAll() {
            return new MessageAgreement(true, iBookService.list());
        }
    
        @PostMapping
        public MessageAgreement save(@RequestBody Book book) {
            return new MessageAgreement(iBookService.save(book));
        }
    
        @PutMapping
        public MessageAgreement update(@RequestBody Book book) {
            return new MessageAgreement(iBookService.updateById(book));
        }
    
        @DeleteMapping(value = "/{id}")
        public MessageAgreement delete(@PathVariable Integer id) {
            return new MessageAgreement(iBookService.removeById(id));
        }
    
        @GetMapping(value = "/{id}")
        public MessageAgreement getById(@PathVariable Integer id) {
            return new MessageAgreement(true, iBookService.getById(id));
        }
    
        @GetMapping(value = "/{current}/{pageSize}")
        public MessageAgreement getPage(@PathVariable Integer current, @PathVariable Integer pageSize) {
            return new MessageAgreement(true, iBookService.getPage(current, pageSize));
        }
    }
    ```

    使用`Postman`进行测试。

11. 页面开发

    `MVC`三层已经开发完毕，现在只剩下页面开发。现在常用的就是前后端分离，前端服务器向后端服务器索要数据，后端服务器向数据库要数据，后端服务器从前端服务器获取数据。为了简化开发，这里使用单体服务器来完成该项演示。

    静态页面全部放置到`resources/static`目录中【拷贝即可】【`Maven clean`然后重启服务器】。

    【注：如果服务器被占用在`cmd`使用：`netstat -ano | findstr "80" + taskkill /pid xxxxx /F`解除占用即可】

    访问页面：`http://localhost/pages/books.html`

    修改`books.html`从后端获取数据：

    ```java
    //钩子函数，VUE对象初始化完成后自动执行
    created() {
        this.getAll();
    },
    methods: {
        //列表
        getAll() {
            axios.get("/books").then((res)=>{
                console.log(res.data)
            })
        },
    }
    ```

    加载数据到页面中，从`el-table`标签中可以看到页面显示的数据都保存在`dataList`中所以需要在页面加载的时候就往`dataList`中填充数据，填充如下：

    ```java
    //钩子函数，VUE对象初始化完成后自动执行
    created() {
        this.getAll();
    },
    methods: {
        //列表
        getAll() {
            axios.get("/books").then((res)=>{
                console.log(res.data);
                this.dataList = res.data.data;
            })
        },
    }
    ```

12. 完成页面之增加图书功能

    通过观察新增标签弹层可以看到标签弹层是通过`dialogFormVisible`来调控的，默认该变量的值为`false`，并且当我们点击**新增按钮**时，会调用`handleCreate()`方法，所以我们需要在该方法中将`dialogFormVisible()`设置为`true`，使其显示新增的弹层。

    ```html
    <!-- 新增标签弹层 -->
    <div class="add-form">
        <el-dialog title="新增图书" :visible.sync="dialogFormVisible">
            <el-form ref="dataAddForm" :model="formData" :rules="rules" label-position="right" label-width="100px">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="图书类别" prop="type">
                            <el-input v-model="formData.type"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="图书名称" prop="name">
                            <el-input v-model="formData.name"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="描述">
                            <el-input v-model="formData.description" type="textarea"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancel()">取消</el-button>
                <el-button type="primary" @click="handleAdd()">确定</el-button>
            </div>
        </el-dialog>
    </div>
    ```

    ```javascript
    //弹出添加窗口
    handleCreate() {
        //弹出新增标签弹层 ---> :visible.sync="dialogFormVisible" ---> 默认该变量为 false
        this.dialogFormVisible = true;
    }
    ```

    通过测试可以看到点击新建按钮弹出了新增图书的弹窗：

    ![](https://img-blog.csdnimg.cn/936659fd2f924c97961a8f4360542a85.png)

    接下来就是完成点击确定增添图书的操作了【往后端传数据】：

    通过代码可以看到增添图书弹窗点击取消和确定时分别调用了`cancel`和`handleAdd`两个方法

    ```html
    <div slot="footer" class="dialog-footer">
        <el-button @click="cancel()">取消</el-button>
        <el-button type="primary" @click="handleAdd()">确定</el-button>
    </div>
    ```

    先完成简单的`cancel()`方法，当点击取消按钮时`dialogFormVisible`的值会设为`false`，并且弹出提示信息表示操作取消：

    ```javascript
    //取消
    cancel() {
        this.dialogFormVisible = false;
        this.$message.info("操作取消");
    },
    ```

    测试通过。

    现在来看看确定功能，`handleAdd`要写的应该是往后端传递新增图书的数据，这项操作可以通过`axios`发送`Ajax`请求来完成，根据`Restful`规则增加操作我们应该发送的是`Post`请求：

    需要分情况讨论：

    - 一种就是添加成功我们需要关闭掉弹窗然后告诉用户添加数据成功了。

    - 另外一种就是添加失败我们不需要关闭弹窗，但是要告诉用户添加失败了。
    - 无论成功还是失败最后都需要刷新新的数据

    ```javascript
    //添加
    handleAdd() {
        axios.post("/books", this.formData).then((res) => {
            //如果添加成功则关闭弹层，刷新页面，告知用户添加成功
            //console.log(res.data.flag);
            if (res.data.flag) {
                this.dialogFormVisible = false;
                this.$message.success("新增图书成功！");
            } else {
                //如果添加失败则不关闭弹层，告知用户添加失败同时刷新页面
                this.$message.error("新增图书失败！");
            }
        }).finally(() => {
            this.getAll();
        });
    },
    ```

    模拟添加数据失败的情况：更改下后端`BookController`中的代码【记得改回去】

    ```java
    @PostMapping
    public MessageAgreement save(@RequestBody Book book) {
        return new MessageAgreement(false);
        //return new MessageAgreement(iBookService.save(book));
    }
    ```

    因为有缓存的存在，所以我们添加完毕【成功的情况】，当我们再次点开时弹窗会保留我们上一次的数据，所以我们需要在每次弹窗弹出的时候就清空下缓存，我们知道数据都保存在【通过`books.html`页面代码中获取该信息】`formData`中，所以我们可以使用`this.formData = {}`清空掉数据，因为清空数据的功能非常常用所以我们将其封装为`resetForm()`方法。当我们弹窗的时候就清空表格信息：

    ```java
    //弹出添加窗口
    handleCreate() {
        //弹出新增标签弹层 ---> :visible.sync="dialogFormVisible" ---> 默认该变量为 false
        this.dialogFormVisible = true;
        //打开窗口就清空数据
        this.resetForm();
    },
    //重置表单
    resetForm() {
        this.formData = {};
    },
    ```

13. 完成页面之删除图书的功能

    有了添加图书的例子，删除图书的例子就很好做了：

    ```java
    @DeleteMapping(value = "/{id}")
    public MessageAgreement delete(@PathVariable Integer id) {
        //模拟删除失败的情况：return new MessageAgreement(false);
        return new MessageAgreement(iBookService.removeById(id));
    }
    ```

    ```javascript
    // 删除
    handleDelete(row) {
        console.log(row);
        axios.delete("/books/" + row.id).then((res) => {
            res.data.flag ? this.$message.success("删除成功！") : this.$message.error("删除失败！");
        }).finally(() => {
            this.getAll();
        })
    },
    ```

    但是这样做有一个问题，删除是个很严肃的操作，万一不小心点错了，也删除吗？当然不一定，所以可以使用`this.$confirm("", "", {type:"info}).then(() => {})`先做一个验证，提示用户是否确认删除：

    ```javascript
    // 删除
    handleDelete(row) {
        //console.log(row);
        //确认是否删除
        this.$confirm("确认此操作将永久删除记录，是否继续？", "提示", {type: "info"}).then(() => {
            axios.delete("/books/" + row.id).then((res) => {
                res.data.flag ? this.$message.success("删除成功！") : this.$message.error("删除失败！");
            }).finally(() => {
                this.getAll();
            });
        }).catch(() => {
            this.$message.info("操作取消！");
        })
    },
    ```

14. 完成页面之修改图书的功能

    弹出编辑窗口：

    ```javascript
    //弹出编辑窗口
    handleUpdate(row) {
        //弹出编辑窗口，显示当前行的数据在表格里
        this.dialogFormVisible4Edit = true;
        this.formData = row;
    },
    ```

    这里有个小问题，因为别人也可能在修改数据，所以最好是不要直接使用`row`里的数据，而是取数据库中查询：

    ```java
    //弹出编辑窗口
    handleUpdate(row) {
        //弹出编辑窗口，显示当前行的数据在表格里
        //避免脏读，从数据库中获取数据
        axios.get("/books/" + row.id).then((res) => {
            if (res.data.flag && (res.data.data != null)) {
                this.dialogFormVisible4Edit = true;
                this.formData = res.data.data;
            } else {
                this.$message.error("数据同步失败，自动刷新");
            }
        }).finally(() => {
            this.getAll();
        })
    },
    ```

    点击取消按钮时需要关闭弹窗然后显示提示信息，这里需要更改下`cancel()`方法：

    ```javascript
    //取消
    cancel() {
        this.dialogFormVisible = false;
        this.dialogFormVisible4Edit = false;
        this.$message.info("操作取消");
    },
    ```

    点击确定按钮时需要发送消息：

    ```javascript
    //修改
    handleEdit() {
        axios.put("/books", this.formData).then((res) => {
            console.log(res);
            //后续的操作就跟新增图书的操作其实是一致的
            if (res.data.flag) {
                this.dialogFormVisible4Edit = false;
                this.$message.success("修改图书成功！");
            } else {
                this.$message.error("修改图书失败！");
            }
        }).finally(() => {
            this.getAll();
        });
    },
    ```

    这就完成了修改图书的功能。

15. 异常消息处理

    开发过程中肯定会有一些`bug`的存在。上述全部代码，如果在前端调用后端接口时发生了异常那么前端就会获取一个全新的数据格式，我们可以举证一下：

    ```java
    @GetMapping
    public MessageAgreement getAll() throws IOException {
        //模拟添加失败的情况：return new MessageAgreement(false);
        if(true) throw new IOException();
        return new MessageAgreement(true, iBookService.list());
    }
    ```

    使用`Postman`调用测试接口，获取到`JSON`数据如下：

    ```java
    GET http://localhost/books
    {
        "timestamp": "2022-07-17T15:19:25.138+00:00",
        "status": 500,
        "error": "Internal Server Error",
        "path": "/books"
    }
    ```

    可以看到这跟我们前面统一的消息协议是完全不符合的，所以就需要有一个专门统一的异常处理，将异常统一成跟原先一样的格式。所以当发生异常的时候必然需要有个东西去管理它，于是我们就拿出了`@RestControllerAdvice`和`@ExceptionHandler`，创建`MyExceptionHandlerAdvice`类：【返回统一消息格式`MessageAgreement`，这里因为需要返回报错信息，所以我们需要在`MessageAgreement`中添加必要的`message`属性】

    所以有必要先更改下`MessageAgreement`类：

    ```java
    package com.kk.controller.utils;
    
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class MessageAgreement {
        private Boolean flag;
        private Object data;
        private String message;
    
        public MessageAgreement(Boolean flag) {
            this.flag = flag;
        }
    
        public MessageAgreement(Boolean flag, Object data) {
            this.flag = flag;
            this.data = data;
        }
    
        public MessageAgreement(String message) {
            this.flag = false;
            this.message = message;
        }
    }
    ```

    然后回到统一异常处理类`MyExceptionHandlerAdvice`：

    ```java
    package com.kk.controller.utils;
    
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.RestControllerAdvice;
    
    @RestControllerAdvice
    public class MyExceptionHandlerAdvice {
        @ExceptionHandler(value = {Exception.class})
        public MessageAgreement myExceptionHandler(Exception exception) {
            exception.printStackTrace();
            return new MessageAgreement("系统错误，请稍后再试！");
        }
    }
    ```

    修改每一个`controller`方法，使之适应新的`MessageAgreement`类

    这又会有新的问题，以添加为例，如果消息如果在只发生异常时才使用`res.data.message`【后端控制】而在说明操作成功和操作失败时却是在前端控制，为了统一，我们希望将全部都放到后端去【以添加方法`save`为例】：

    ```java
    @PostMapping
    public MessageAgreement save(@RequestBody Book book) {
        Boolean flag = iBookService.save(book);
        return new MessageAgreement(flag, flag ? "添加成功！" : "添加失败！");
    }
    ```

    这里我们还可以再稍微验证下异常的情况【名字为`11`才抛异常】：

    ```java
    @PostMapping
    public MessageAgreement save(@RequestBody Book book) throws Exception {
        if(book.getName().equals("11")) throw new Exception();
        Boolean flag = iBookService.save(book);
        return new MessageAgreement(flag, null, flag ? "添加成功！" : "添加失败！");
    }
    ```

    然后就是到前端页面，也需要改造下，输出的信息应该都是直接从`res.data.message`中获取【暂时还是现以添加图书为例子，为了方便区分这里先贴出之前的代码】：

    ```javascript
    //添加
    handleAdd() {
        axios.post("/books", this.formData).then((res) => {
            //如果添加成功则关闭弹层，刷新页面，告知用户添加成功
            //console.log(res.data.flag);
            if (res.data.flag) {
                this.dialogFormVisible = false;
                this.$message.success("新增图书成功！");
            } else {
                //如果添加失败则不关闭弹层，告知用户添加失败同时刷新页面
                this.$message.error("新增图书失败！");
            }
        }).finally(() => {
            this.getAll();
        });
    },
    ```

    ```java
    //添加
    handleAdd() {
        axios.post("/books", this.formData).then((res) => {
            //如果添加成功则关闭弹层，刷新页面，告知用户添加成功
            //console.log(res.data.flag);
            if (res.data.flag) {
                this.dialogFormVisible = false;
                this.$message.success(res.data.message);
            } else {
                //如果添加失败则不关闭弹层，告知用户添加失败同时刷新页面
                this.$message.error(res.data.message);
            }
        }).finally(() => {
            this.getAll();
        });
    },
    ```

    其余的方法更改都是一样的操作，这就不多赘述。

16. 完成分页功能

    前面我们显示页面调用的都是`getAll()`方法，所以为了避免修改的麻烦，我们直接覆盖`getAll()`方法即可：

    从前端页面代码可知关于分页的重要数据`current`和`pageSize`保存在`pagination`中，全部条数保存在：`pagination.total`中：

    ```html
    <!--分页组件-->
    <div class="pagination-container">
        <el-pagination
                class="pagiantion"
                @current-change="handleCurrentChange"
                :current-page="pagination.currentPage"
                :page-size="pagination.pageSize"
                layout="total, prev, pager, next, jumper"
                :total="pagination.total">
        </el-pagination>
    </div>
    ```

    ```javascript
    //分页查询 ---> 直接覆盖 getAll()
    getAll() {
        //调用分页查询接口方法
        axios.get("/books/" + this.pagination.currentPage + "/" + this.pagination.pageSize)
            this.pagination.total = res.data.data.total;
            //展示数据到页面
            this.dataList = res.data.data.records;
        });
    },
    ```

    点击按钮切换页码功能：

    ```javascript
    //切换页码
    handleCurrentChange(currentPage) {
        //点击页面时切换
        this.pagination.currentPage = currentPage;
        this.getAll();
    },
    ```

17. 解决分页查询出现的`bug`

    当我们删除最后一页的所有数据，比如说这是第`6`页，但是第`6`页上的记录已经被删完了，可是为什么还是展示在第`6`页呢？这并不合理，这就是一个需要解决的`bug`，此时最大页数为第`5`页，但你却显示了第`6`页，那要如何解决呢？我们就在后端判断下，如果`currentPage`大于了最大页码数，我们就将其转换为最大页码数。

    ```javascript
    @GetMapping(value = "/{current}/{pageSize}")
    public MessageAgreement getPage(@PathVariable Integer current, @PathVariable Integer pageSize) {
        IPage<Book> iPage = iBookService.getPage(current, pageSize);
        //比较最大页码数和要显示的页码，若最大页码小于则需要将当前页码转为最大页码
        if(iPage.getPages() < current) {
            //重新查一遍
            iPage = iBookService.getPage((int) iPage.getPages(), pageSize);
        }
        return new MessageAgreement(true, iPage);
    }
    ```

18. 完成按条件查询的功能

    前端条件查询的代码为：【可以看到并没有获取方框值，所以我们需要写上】

    ```html
    <div class="filter-container">
        <el-input placeholder="图书类别" style="width: 200px;" class="filter-item"></el-input>
        <el-input placeholder="图书名称" style="width: 200px;" class="filter-item"></el-input>
        <el-input placeholder="图书描述" style="width: 200px;" class="filter-item"></el-input>
        <el-button @click="getAll()" class="dalfBut">查询</el-button>
        <el-button type="primary" class="butT" @click="handleCreate()">新建</el-button>
    </div>
    ```

    为了统一，我们在`pagination`中写：

    ```javascript
    pagination: {//分页相关模型数据
        currentPage: 1,//当前页码
        pageSize: 6,//每页显示的记录数
        total: 0,//总记录数
        type: "",//图书类型
        name: "",//图书名称
        description: ""//图书描述
    }
    ```

    绑定到`html`页面中：

    ```html
    <div class="filter-container">
        <el-input placeholder="图书类别" v-model="pagination.type" style="width: 200px;" class="filter-item"></el-input>
        <el-input placeholder="图书名称" v-model="pagination.name" style="width: 200px;" class="filter-item"></el-input>
        <el-input placeholder="图书描述" v-model="pagination.description" style="width: 200px;" class="filter-item"></el-input>
        <el-button @click="getAll()" class="dalfBut">查询</el-button>
        <el-button type="primary" class="butT" @click="handleCreate()">新建</el-button>
    </div>
    ```

    点击查询时使用的也是`getAll()`方法，我们可以在`url`地址中绑定参数，然后我们可以使用`LambdaQueryWrapper`，如果参数为空则不加条件，如果参数不为空则加条件，明确了主根之后，前端要做的就是传递参数，后端要做的就是接收参数判断是否为空从而确定要不要加条件：

    ```javascript
    //分页查询 ---> 直接覆盖 getAll()
    getAll() {
        let param = "?type=" + this.pagination.type;
        param += "&name=" + this.pagination.name;
        param += "&description=" + this.pagination.description;
        let url = "/books/" + this.pagination.currentPage + "/" + this.pagination.pageSize + param;
        console.log(url);
        //调用分页查询接口方法
        axios.get(url).then((res) => {
            this.pagination.total = res.data.data.total;
            //展示数据到页面
            this.dataList = res.data.data.records;
        });
        1
    },
    ```

    更改表现层代码：

    ```java
    //分页查询 + 按条件查询
    @GetMapping(value = "/{current}/{pageSize}")
    public MessageAgreement getPage(@PathVariable Integer current, @PathVariable Integer pageSize, Book book) {
        System.out.println(book);
        IPage<Book> iPage = iBookService.getPage(current, pageSize, book);
        //比较最大页码数和要显示的页码，若最大页码小于则需要将当前页码转为最大页码
        if(iPage.getPages() < current) {
            //重新查一遍
            iPage = iBookService.getPage((int) iPage.getPages(), pageSize, book);
        }
        return new MessageAgreement(true, iPage);
    }
    ```

    更改服务层接口：

    ```java
    package com.kk.service;
    
    import com.baomidou.mybatisplus.core.metadata.IPage;
    import com.baomidou.mybatisplus.extension.service.IService;
    import com.kk.pojo.Book;
    
    public interface IBookService extends IService<Book> {
        IPage<Book> getPage(int current, int pageSize, Book book);
    }
    ```

    更改服务层实现代码：

    ```java
    package com.kk.service.impl;
    
    import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
    import com.baomidou.mybatisplus.core.metadata.IPage;
    import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
    import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
    import com.kk.mapper.BookMapper;
    import com.kk.pojo.Book;
    import com.kk.service.IBookService;
    import org.apache.logging.log4j.util.Strings;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    
    @Service
    public class IBookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {
    
        @Autowired
        private BookMapper bookMapper;
    
        public IPage<Book> getPage(int current, int pageSize, Book book) {
            LambdaQueryWrapper<Book> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.like(Strings.isNotEmpty(book.getType()), Book::getType, book.getType());
            lambdaQueryWrapper.like(Strings.isNotEmpty(book.getName()), Book::getName, book.getName());
            lambdaQueryWrapper.like(Strings.isNotEmpty(book.getDescription()), Book::getDescription, book.getDescription());
            IPage<Book> iPage = new Page<>(current, pageSize);
            bookMapper.selectPage(iPage, lambdaQueryWrapper);
            return iPage;
        }
    }
    ```

    外加一个重置条件查询的按钮：

    ```html
    <el-button @click="handleGetAll()" class="dalfBut">重置</el-button>
    ```

    ```javascript
    //条件查询重置按钮
    handleGetAll() {
        this.pagination.type = "";
        this.pagination.name = "";
        this.pagination.description = "";
        this.getAll();
    }
    ```

    加载数据到页面中，从`el-table`标签中可以看到页面显示的数据都保存在`dataList`中所以需要在页面加载的时候就往`dataList`中填充数据，填充如下：

    ```javascript
    //钩子函数，VUE对象初始化完成后自动执行
    created() {
        this.getAll();
    },
    methods: {
        //列表
        getAll() {
            axios.get("/books").then((res)=>{
                console.log(res.data);
                this.dataList = res.data.data;
            })
        },
    }
    ```

12. 完成页面之增加图书功能

    通过观察新增标签弹层可以看到标签弹层是通过`dialogFormVisible`来调控的，默认该变量的值为`false`，并且当我们点击**新增按钮**时，会调用`handleCreate()`方法，所以我们需要在该方法中将`dialogFormVisible()`设置为`true`，使其显示新增的弹层。

    ```html
    <!-- 新增标签弹层 -->
    <div class="add-form">
        <el-dialog title="新增图书" :visible.sync="dialogFormVisible">
            <el-form ref="dataAddForm" :model="formData" :rules="rules" label-position="right" label-width="100px">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="图书类别" prop="type">
                            <el-input v-model="formData.type"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="图书名称" prop="name">
                            <el-input v-model="formData.name"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="描述">
                            <el-input v-model="formData.description" type="textarea"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancel()">取消</el-button>
                <el-button type="primary" @click="handleAdd()">确定</el-button>
            </div>
        </el-dialog>
    </div>
    ```

    ```javascript
    //弹出添加窗口
    handleCreate() {
        //弹出新增标签弹层 ---> :visible.sync="dialogFormVisible" ---> 默认该变量为 false
        this.dialogFormVisible = true;
    }
    ```

    通过测试可以看到点击新建按钮弹出了新增图书的弹窗：

    ![](https://img-blog.csdnimg.cn/936659fd2f924c97961a8f4360542a85.png)

    接下来就是完成点击确定增添图书的操作了【往后端传数据】：

    通过代码可以看到增添图书弹窗点击取消和确定时分别调用了`cancel`和`handleAdd`两个方法

    ```html
    <div slot="footer" class="dialog-footer">
        <el-button @click="cancel()">取消</el-button>
        <el-button type="primary" @click="handleAdd()">确定</el-button>
    </div>
    ```

    先完成简单的`cancel()`方法，当点击取消按钮时`dialogFormVisible`的值会设为`false`，并且弹出提示信息表示操作取消：

    ```javascript
    //取消
    cancel() {
        this.dialogFormVisible = false;
        this.$message.info("操作取消");
    },
    ```

    测试通过。

    现在来看看确定功能，`handleAdd`要写的应该是往后端传递新增图书的数据，这项操作可以通过`axios`发送`Ajax`请求来完成，根据`Restful`规则增加操作我们应该发送的是`Post`请求：

    需要分情况讨论：

    - 一种就是添加成功我们需要关闭掉弹窗然后告诉用户添加数据成功了。

    - 另外一种就是添加失败我们不需要关闭弹窗，但是要告诉用户添加失败了。
    - 无论成功还是失败最后都需要刷新新的数据

    ```javascript
    //添加
    handleAdd() {
        axios.post("/books", this.formData).then((res) => {
            //如果添加成功则关闭弹层，刷新页面，告知用户添加成功
            //console.log(res.data.flag);
            if (res.data.flag) {
                this.dialogFormVisible = false;
                this.$message.success("新增图书成功！");
            } else {
                //如果添加失败则不关闭弹层，告知用户添加失败同时刷新页面
                this.$message.error("新增图书失败！");
            }
        }).finally(() => {
            this.getAll();
        });
    },
    ```

    模拟添加数据失败的情况：更改下后端`BookController`中的代码【记得改回去】

    ```java
    @PostMapping
    public MessageAgreement save(@RequestBody Book book) {
        return new MessageAgreement(false);
        //return new MessageAgreement(iBookService.save(book));
    }
    ```

    因为有缓存的存在，所以我们添加完毕【成功的情况】，当我们再次点开时弹窗会保留我们上一次的数据，所以我们需要在每次弹窗弹出的时候就清空下缓存，我们知道数据都保存在【通过`books.html`页面代码中获取该信息】`formData`中，所以我们可以使用`this.formData = {}`清空掉数据，因为清空数据的功能非常常用所以我们将其封装为`resetForm()`方法。当我们弹窗的时候就清空表格信息：

    ```java
    //弹出添加窗口
    handleCreate() {
        //弹出新增标签弹层 ---> :visible.sync="dialogFormVisible" ---> 默认该变量为 false
        this.dialogFormVisible = true;
        //打开窗口就清空数据
        this.resetForm();
    },
    //重置表单
    resetForm() {
        this.formData = {};
    },
    ```

13. 完成页面之删除图书的功能

    有了添加图书的例子，删除图书的例子就很好做了：

    ```java
    @DeleteMapping(value = "/{id}")
    public MessageAgreement delete(@PathVariable Integer id) {
        //模拟删除失败的情况：return new MessageAgreement(false);
        return new MessageAgreement(iBookService.removeById(id));
    }
    ```

    ```javascript
    // 删除
    handleDelete(row) {
        console.log(row);
        axios.delete("/books/" + row.id).then((res) => {
            res.data.flag ? this.$message.success("删除成功！") : this.$message.error("删除失败！");
        }).finally(() => {
            this.getAll();
        })
    },
    ```

    但是这样做有一个问题，删除是个很严肃的操作，万一不小心点错了，也删除吗？当然不一定，所以可以使用`this.$confirm("", "", {type:"info}).then(() => {})`先做一个验证，提示用户是否确认删除：

    ```javascript
    // 删除
    handleDelete(row) {
        //console.log(row);
        //确认是否删除
        this.$confirm("确认此操作将永久删除记录，是否继续？", "提示", {type: "info"}).then(() => {
            axios.delete("/books/" + row.id).then((res) => {
                res.data.flag ? this.$message.success("删除成功！") : this.$message.error("删除失败！");
            }).finally(() => {
                this.getAll();
            });
        }).catch(() => {
            this.$message.info("操作取消！");
        })
    },
    ```

14. 完成页面之修改图书的功能

    弹出编辑窗口：

    ```javascript
    //弹出编辑窗口
    handleUpdate(row) {
        //弹出编辑窗口，显示当前行的数据在表格里
        this.dialogFormVisible4Edit = true;
        this.formData = row;
    },
    ```

    这里有个小问题，因为别人也可能在修改数据，所以最好是不要直接使用`row`里的数据，而是取数据库中查询：

    ```java
    //弹出编辑窗口
    handleUpdate(row) {
        //弹出编辑窗口，显示当前行的数据在表格里
        //避免脏读，从数据库中获取数据
        axios.get("/books/" + row.id).then((res) => {
            if (res.data.flag && (res.data.data != null)) {
                this.dialogFormVisible4Edit = true;
                this.formData = res.data.data;
            } else {
                this.$message.error("数据同步失败，自动刷新");
            }
        }).finally(() => {
            this.getAll();
        })
    },
    ```

    点击取消按钮时需要关闭弹窗然后显示提示信息，这里需要更改下`cancel()`方法：

    ```javascript
    //取消
    cancel() {
        this.dialogFormVisible = false;
        this.dialogFormVisible4Edit = false;
        this.$message.info("操作取消");
    },
    ```

    点击确定按钮时需要发送消息：

    ```javascript
    //修改
    handleEdit() {
        axios.put("/books", this.formData).then((res) => {
            console.log(res);
            //后续的操作就跟新增图书的操作其实是一致的
            if (res.data.flag) {
                this.dialogFormVisible4Edit = false;
                this.$message.success("修改图书成功！");
            } else {
                this.$message.error("修改图书失败！");
            }
        }).finally(() => {
            this.getAll();
        });
    },
    ```

    这就完成了修改图书的功能。

15. 异常消息处理

    开发过程中肯定会有一些`bug`的存在。上述全部代码，如果在前端调用后端接口时发生了异常那么前端就会获取一个全新的数据格式，我们可以举证一下：

    ```java
    @GetMapping
    public MessageAgreement getAll() throws IOException {
        //模拟添加失败的情况：return new MessageAgreement(false);
        if(true) throw new IOException();
        return new MessageAgreement(true, iBookService.list());
    }
    ```

    使用`Postman`调用测试接口，获取到`JSON`数据如下：

    ```java
    GET http://localhost/books
    {
        "timestamp": "2022-07-17T15:19:25.138+00:00",
        "status": 500,
        "error": "Internal Server Error",
        "path": "/books"
    }
    ```

    可以看到这跟我们前面统一的消息协议是完全不符合的，所以就需要有一个专门统一的异常处理，将异常统一成跟原先一样的格式。所以当发生异常的时候必然需要有个东西去管理它，于是我们就拿出了`@RestControllerAdvice`和`@ExceptionHandler`，创建`MyExceptionHandlerAdvice`类：【返回统一消息格式`MessageAgreement`，这里因为需要返回报错信息，所以我们需要在`MessageAgreement`中添加必要的`message`属性】

    所以有必要先更改下`MessageAgreement`类：

    ```java
    package com.kk.controller.utils;
    
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class MessageAgreement {
        private Boolean flag;
        private Object data;
        private String message;
    
        public MessageAgreement(Boolean flag) {
            this.flag = flag;
        }
    
        public MessageAgreement(Boolean flag, Object data) {
            this.flag = flag;
            this.data = data;
        }
    
        public MessageAgreement(String message) {
            this.flag = false;
            this.message = message;
        }
    }
    ```

    然后回到统一异常处理类`MyExceptionHandlerAdvice`：

    ```java
    package com.kk.controller.utils;
    
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.RestControllerAdvice;
    
    @RestControllerAdvice
    public class MyExceptionHandlerAdvice {
        @ExceptionHandler(value = {Exception.class})
        public MessageAgreement myExceptionHandler(Exception exception) {
            exception.printStackTrace();
            return new MessageAgreement("系统错误，请稍后再试！");
        }
    }
    ```

    修改每一个`controller`方法，使之适应新的`MessageAgreement`类

    这又会有新的问题，以添加为例，如果消息如果在只发生异常时才使用`res.data.message`【后端控制】而在说明操作成功和操作失败时却是在前端控制，为了统一，我们希望将全部都放到后端去【以添加方法`save`为例】：

    ```java
    @PostMapping
    public MessageAgreement save(@RequestBody Book book) {
        Boolean flag = iBookService.save(book);
        return new MessageAgreement(flag, flag ? "添加成功！" : "添加失败！");
    }
    ```

    这里我们还可以再稍微验证下异常的情况【名字为`11`才抛异常】：

    ```java
    @PostMapping
    public MessageAgreement save(@RequestBody Book book) throws Exception {
        if(book.getName().equals("11")) throw new Exception();
        Boolean flag = iBookService.save(book);
        return new MessageAgreement(flag, null, flag ? "添加成功！" : "添加失败！");
    }
    ```

    然后就是到前端页面，也需要改造下，输出的信息应该都是直接从`res.data.message`中获取【暂时还是现以添加图书为例子，为了方便区分这里先贴出之前的代码】：

    ```javascript
    //添加
    handleAdd() {
        axios.post("/books", this.formData).then((res) => {
            //如果添加成功则关闭弹层，刷新页面，告知用户添加成功
            //console.log(res.data.flag);
            if (res.data.flag) {
                this.dialogFormVisible = false;
                this.$message.success("新增图书成功！");
            } else {
                //如果添加失败则不关闭弹层，告知用户添加失败同时刷新页面
                this.$message.error("新增图书失败！");
            }
        }).finally(() => {
            this.getAll();
        });
    },
    ```

    ```java
    //添加
    handleAdd() {
        axios.post("/books", this.formData).then((res) => {
            //如果添加成功则关闭弹层，刷新页面，告知用户添加成功
            //console.log(res.data.flag);
            if (res.data.flag) {
                this.dialogFormVisible = false;
                this.$message.success(res.data.message);
            } else {
                //如果添加失败则不关闭弹层，告知用户添加失败同时刷新页面
                this.$message.error(res.data.message);
            }
        }).finally(() => {
            this.getAll();
        });
    },
    ```

    其余的方法更改都是一样的操作，这就不多赘述。

16. 完成分页功能

    前面我们显示页面调用的都是`getAll()`方法，所以为了避免修改的麻烦，我们直接覆盖`getAll()`方法即可：

    从前端页面代码可知关于分页的重要数据`current`和`pageSize`保存在`pagination`中，全部条数保存在：`pagination.total`中：

    ```html
    <!--分页组件-->
    <div class="pagination-container">
        <el-pagination
                class="pagiantion"
                @current-change="handleCurrentChange"
                :current-page="pagination.currentPage"
                :page-size="pagination.pageSize"
                layout="total, prev, pager, next, jumper"
                :total="pagination.total">
        </el-pagination>
    </div>
    ```

    ```javascript
    //分页查询 ---> 直接覆盖 getAll()
    getAll() {
        //调用分页查询接口方法
        axios.get("/books/" + this.pagination.currentPage + "/" + this.pagination.pageSize)
            this.pagination.total = res.data.data.total;
            //展示数据到页面
            this.dataList = res.data.data.records;
        });
    },
    ```

    点击按钮切换页码功能：

    ```javascript
    //切换页码
    handleCurrentChange(currentPage) {
        //点击页面时切换
        this.pagination.currentPage = currentPage;
        this.getAll();
    },
    ```

17. 解决分页查询出现的`bug`

    当我们删除最后一页的所有数据，比如说这是第`6`页，但是第`6`页上的记录已经被删完了，可是为什么还是展示在第`6`页呢？这并不合理，这就是一个需要解决的`bug`，此时最大页数为第`5`页，但你却显示了第`6`页，那要如何解决呢？我们就在后端判断下，如果`currentPage`大于了最大页码数，我们就将其转换为最大页码数。

    ```javascript
    @GetMapping(value = "/{current}/{pageSize}")
    public MessageAgreement getPage(@PathVariable Integer current, @PathVariable Integer pageSize) {
        IPage<Book> iPage = iBookService.getPage(current, pageSize);
        //比较最大页码数和要显示的页码，若最大页码小于则需要将当前页码转为最大页码
        if(iPage.getPages() < current) {
            //重新查一遍
            iPage = iBookService.getPage((int) iPage.getPages(), pageSize);
        }
        return new MessageAgreement(true, iPage);
    }
    ```

18. 完成按条件查询的功能

    前端条件查询的代码为：【可以看到并没有获取方框值，所以我们需要写上】

    ```html
    <div class="filter-container">
        <el-input placeholder="图书类别" style="width: 200px;" class="filter-item"></el-input>
        <el-input placeholder="图书名称" style="width: 200px;" class="filter-item"></el-input>
        <el-input placeholder="图书描述" style="width: 200px;" class="filter-item"></el-input>
        <el-button @click="getAll()" class="dalfBut">查询</el-button>
        <el-button type="primary" class="butT" @click="handleCreate()">新建</el-button>
    </div>
    ```

    为了统一，我们在`pagination`中写：

    ```javascript
    pagination: {//分页相关模型数据
        currentPage: 1,//当前页码
        pageSize: 6,//每页显示的记录数
        total: 0,//总记录数
        type: "",//图书类型
        name: "",//图书名称
        description: ""//图书描述
    }
    ```

    绑定到`html`页面中：

    ```html
    <div class="filter-container">
        <el-input placeholder="图书类别" v-model="pagination.type" style="width: 200px;" class="filter-item"></el-input>
        <el-input placeholder="图书名称" v-model="pagination.name" style="width: 200px;" class="filter-item"></el-input>
        <el-input placeholder="图书描述" v-model="pagination.description" style="width: 200px;" class="filter-item"></el-input>
        <el-button @click="getAll()" class="dalfBut">查询</el-button>
        <el-button type="primary" class="butT" @click="handleCreate()">新建</el-button>
    </div>
    ```

    点击查询时使用的也是`getAll()`方法，我们可以在`url`地址中绑定参数，然后我们可以使用`LambdaQueryWrapper`，如果参数为空则不加条件，如果参数不为空则加条件，明确了主根之后，前端要做的就是传递参数，后端要做的就是接收参数判断是否为空从而确定要不要加条件：

    ```javascript
    //分页查询 ---> 直接覆盖 getAll()
    getAll() {
        let param = "?type=" + this.pagination.type;
        param += "&name=" + this.pagination.name;
        param += "&description=" + this.pagination.description;
        let url = "/books/" + this.pagination.currentPage + "/" + this.pagination.pageSize + param;
        console.log(url);
        //调用分页查询接口方法
        axios.get(url).then((res) => {
            this.pagination.total = res.data.data.total;
            //展示数据到页面
            this.dataList = res.data.data.records;
        });
        1
    },
    ```

    更改表现层代码：

    ```java
    //分页查询 + 按条件查询
    @GetMapping(value = "/{current}/{pageSize}")
    public MessageAgreement getPage(@PathVariable Integer current, @PathVariable Integer pageSize, Book book) {
        System.out.println(book);
        IPage<Book> iPage = iBookService.getPage(current, pageSize, book);
        //比较最大页码数和要显示的页码，若最大页码小于则需要将当前页码转为最大页码
        if(iPage.getPages() < current) {
            //重新查一遍
            iPage = iBookService.getPage((int) iPage.getPages(), pageSize, book);
        }
        return new MessageAgreement(true, iPage);
    }
    ```

    更改服务层接口：

    ```java
    package com.kk.service;
    
    import com.baomidou.mybatisplus.core.metadata.IPage;
    import com.baomidou.mybatisplus.extension.service.IService;
    import com.kk.pojo.Book;
    
    public interface IBookService extends IService<Book> {
        IPage<Book> getPage(int current, int pageSize, Book book);
    }
    ```

    更改服务层实现代码：

    ```java
    package com.kk.service.impl;
    
    import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
    import com.baomidou.mybatisplus.core.metadata.IPage;
    import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
    import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
    import com.kk.mapper.BookMapper;
    import com.kk.pojo.Book;
    import com.kk.service.IBookService;
    import org.apache.logging.log4j.util.Strings;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    
    @Service
    public class IBookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {
    
        @Autowired
        private BookMapper bookMapper;
    
        public IPage<Book> getPage(int current, int pageSize, Book book) {
            LambdaQueryWrapper<Book> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.like(Strings.isNotEmpty(book.getType()), Book::getType, book.getType());
            lambdaQueryWrapper.like(Strings.isNotEmpty(book.getName()), Book::getName, book.getName());
            lambdaQueryWrapper.like(Strings.isNotEmpty(book.getDescription()), Book::getDescription, book.getDescription());
            IPage<Book> iPage = new Page<>(current, pageSize);
            bookMapper.selectPage(iPage, lambdaQueryWrapper);
            return iPage;
        }
    }
    ```

    外加一个重置条件查询的按钮：

    ```html
    <el-button @click="handleGetAll()" class="dalfBut">重置</el-button>
    ```

    ```javascript
    //条件查询重置按钮
    handleGetAll() {
        this.pagination.type = "";
        this.pagination.name = "";
        this.pagination.description = "";
        this.getAll();
    }
    ```

# `SpringBoot`运维实用篇

## 打包与运行

为什么需要打包？倘若你的写代码的电脑一关那用户就无法使用了，所以需要打包放到服务器上 ==> 这里是生成`Jar`包。

### `Windows`

- 打包使用`Maven`打包即可：`clean ---> package`然后就可以看到`target`文件夹出现`jar`包，也可以使用命令`mv package`

- 在改文件夹下输入`cmd`进入窗口

- 然后输入`java -jar xxx.jar`

- 打包过程会调用测试程序，所以需要点击`IDEA ---> Maven`那个跳过测试的按钮

- 然后重复上述过程即可

- 注：`jar`支持命令行启动需要依赖`maven`插件支持，所以需要确保`SpringBoot`对应着`Maven`插件

  ```xml
  <build>
      <plugins>
          <plugin>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-maven-plugin</artifactId>
          </plugin>
      </plugins>
  </build>
  ```

  使用这个插件跟不使用这个插件的关键在于`jar`描述文件不同【可以使用压缩软件打开来瞧一瞧】

  ```yml
  Manifest-Version: 1.0
  Spring-Boot-Classpath-Index: BOOT-INF/classpath.idx
  Implementation-Title: SpringBoot_demo07_SSMP
  Implementation-Version: 0.0.1-SNAPSHOT
  Spring-Boot-Layers-Index: BOOT-INF/layers.idx
  Start-Class: com.kk.Application
  Spring-Boot-Classes: BOOT-INF/classes/
  Spring-Boot-Lib: BOOT-INF/lib/
  Build-Jdk-Spec: 1.8
  Spring-Boot-Version: 2.7.1
  Created-By: Maven JAR Plugin 3.2.2
  Main-Class: org.springframework.boot.loader.JarLauncher
  ```

  不使用这个插件时是这样子的：

  ```yml
  Manifest-Version: 1.0
  Spring-Boot-Classpath-Index: BOOT-INF/classpath.idx
  Implementation-Title: SpringBoot_demo07_SSMP
  Implementation-Version: 0.0.1-SNAPSHOT
  ```

  这样子是无法直接启动的，因为最关键的两个描述信息没有描述：

  ```yml
  Start-Class: com.kk.Application
  Main-Class: org.springframework.boot.loader.JarLauncher
  ```

  一个是`jar`包的主类一个是启动类，如果这两家伙不见了则是无法启动的，所以打包的时候需要带上`spring-boot-maven-plugin`插件。

  除此之外还可以看到使用插件进行打包跟不使用插件进行打包其打包出来的包大小非常不一样，其原因是因为使用插件打包会将依赖也打包到`lib`目录下，这就是为什么打包出来的包这么大的原因。

- 关于端口占用的问题前面有提到过，这里重温一遍：

  ```powershell
  netstat -ano | find "端口号"
  #然后记录进程 PID 杀死该进程即可
  taskkill /PID "进程PID号" /F
  #或者可以查询出进程名杀死进程名
  tasklist | findstr "进程PID号"
  taskkill -f -t -im "进程名"
  ```

- 注意：其实这些东西知道就行因为日后不会在`windows`使用`jar`包而是在`Linux`环境下使用。

### `Linux`

- 安装`jdk 1.8`

- 安装`mysql` ===> 结合`navicat`上传数据【记得关闭防火墙】

- 创建`/usr/local/app`目录或者放在`$HOME`即`~`中也可以

- 上传`jar`包

- 可以使用`java -jar xx.jar`也可以使用后台启动：`nohup java -jar xx.jar > server.log > server.log 2>&1 &` ===> `cat server.log`可以查看日志

  ![](https://img-blog.csdnimg.cn/506e5a4b5f054fba8a6cb424fb7c4888.png)

- 尝试访问：`ip`地址访问【关闭防火墙`systemctl stop firewalld`】

- 关闭进程：

  ```java
  ps -ef | grep "java -jar" ---> 查询 PID 27125
  kill -9 27125
  ```

- 在这里发现一个`bug`，重置后显示的不是跳到第一页，所以需要修改下代码：

  ```javascript
  //条件查询重置按钮
  handleGetAll() {
      //点击重置需要回到第一页
      this.pagination.currentPage = 1;
      this.pagination.type = "";
      this.pagination.name = "";
      this.pagination.description = "";
      this.getAll();
  }
  ```

## 配置高级

### 配置临时属性

- 有时候某个属性需要被替代，但是此时已经打包好了，如果要去修改`properties/yml`文件等则是非常麻烦的一件事情，比如我们在`yml`文件中设定的`server.port = 80`但是此时如果`80`端口被更重要的服务所占用了，那我们就必须改变，此时我们就可以使用临时属性改变：`--server.port=8080 `。这就是临时属性的意义，可以赋予运维更高的机动性。

  为什么临时属性可以生效呢？这是因为在命令行中设定的属性优先级高于临时属性。所以临时属性才可以生效。

  ```powershell
  java -jar SpringBoot_demo07_SSMP-0.0.1-SNAPSHOT.jar --server.port=8080
  ```

- 如果一个临时属性在命令行中一直用不了，那是后端的工作没做到位，所以后端人员需要在`IDEA`中对临时属性进行测试。

  可以在`Application.java`入口类进行参数的测试：

  ```java
  package com.kk;
  
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  
  @SpringBootApplication
  public class Application {
      public static void main(String[] args) {
          SpringApplication.run(Application.class, args);
      }
  }
  ```

  可以看到这里带了一个`args`参数，这个`args`参数其实就是用来接收临时属性的，如何设定临时属性的传递有两个方法，一是在`IDEA`中给，另外一个就是直接在代码编辑。如果你完全不想有临时属性发挥作用，那你可以将`args`属性去掉，此时临时属性无论怎么添加都不再发挥作用。我们一个一个来说明下：

  1. 直接在`IDEA`测试参数

     图片中的`Program arguments`就是我们可以添加的参数。

     ![](https://img-blog.csdnimg.cn/fe0c98b9f91e442393b3bfaf88691d75.png)

     设置：`--server.port=8080`，运行项目发现项目正是运行在`8080`端口上的

     ```java
     2022-07-19 12:57:13.068  INFO 36280 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
     ```

  2. 在代码中传递参数

     我们知道传递的参数本质上是通过`args`传递的，所以我们完全可以仿照来传递参数

     ```java
     package com.kk;
     
     import org.springframework.boot.SpringApplication;
     import org.springframework.boot.autoconfigure.SpringBootApplication;
     
     @SpringBootApplication
     public class Application {
         public static void main(String[] args) {
             String[] arg = {"--server.port = 8081", "--a=b"};
             SpringApplication.run(Application.class, arg);
         }
     }
     ```

     此时我们可以看到启动项目是在`8081`端口上进行的，这表明参数发挥了作用：

     ```java
     2022-07-19 13:06:27.868  INFO 14576 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8081 (http) with context path ''
     ```

  3. 如果我们不希望临时参数发挥作用，我们完全可以修改代码，将参数去除

     ```java
     package com.kk;
     
     import org.springframework.boot.SpringApplication;
     import org.springframework.boot.autoconfigure.SpringBootApplication;
     
     @SpringBootApplication
     public class Application {
         public static void main(String[] args) {
             String[] arg = {"--server.port = 8081", "--a=b"};
             SpringApplication.run(Application.class);
         }
     }
     ```

     通过项目运行的端口可以发现此时项目是运行在`yml`配置文件中配置的`80`端口上的，而临时参数没有发挥作用：

     ```java
     2022-07-19 13:07:58.783  INFO 33076 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 80 (http) with context path ''
     ```

### `4`级配置文件    

- 有个问题就是在开发过程中用到的密码等属性跟最后项目经理整合的密码这些是不太一样的，项目经理不可能都把临时属性写到一条命令上吧~那要怎么解决这个问题呢？

  `SpringBoot`早就想到了这个问题，你可以在`resources`目录下建立一个`config`目录，然后在这个目录中将`application.yml`复制过来，这里就增添项目经理想要的配置，并且这两个配置文件存在的是合作关系，如果两个文件配置的那里冲突了，按照优先级是`config`目录中的数据被优先读取。

  `resources/config/application.yml`：

  ```yaml
  server:
    port: 8083
  ```

  运行程序，查看端口是否为`8083`：【正确】

  ```yaml
  2022-07-19 16:12:58.015  INFO 19260 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8083 (http)
  ```

- 这时又冒出来另外一个问题了，就是有些涉密级别很高的配置比如数据库密码等是无法给到开发的，无法给到开发意味着这些东西你是无法直接加的，那该如何办好呢？

  3. 在一个路径下，只要`jar`包跟`application.yml`放在一块，该配置文件的级别将比`resources/config/application.yml`的级别还要大。

  4. 如若在一个路径下中再加个`config`，内再配置`application.yml`文件，则该级别比上一级别还要大【文件路径跟`jar`包放一块的】

  配置文件之前我们说过：`properties > yml > yaml`级别是这样划分的。

  现在`SpringBoot`为了解决各种实际应用场景权限级别的问题，又再细分给`application.yml/properties/yaml`做了更加细化的级别。

  **<font color="red">`application.yml < resources/config/application.yml < [file] application.yml < [file] config/application.yml`</font>**

### 自定义配置文件

- 设定了四个级别的配置文件其安全性还不够，要是有个人搞个后门就知道所有的配置密码什么的都放在名为`application.yml`配置文件中，就很容易找到盗取，更好的方法就是换一个名字，连配置文件的名字都改了。但我们知道`SpringBoot`默认只识别`application.`文件名的配置文件的，如何更改呢？

  1. **第一种方式：使用临时属性配置配置文件名**

     使用临时属性`--spring.config.name=""`即可指定自定义的配置文件名的配置当作配置文件，这里可以使用`IDEA`中的`Program Arguments`进行测试：

     ![](https://img-blog.csdnimg.cn/18b2898b36fc42e6810468758e56c841.png)

     在`ebank.yml`我们设置了服务器端口为：`server.port=81`，重启服务器可以看到运行端口号为`81`：证明自定义配置文件生效【即使`application.yml`但是我们指定了`ebank.yml`就是配置文件，所以`application.yml`是不起作用的，并且通过测试，就算`application.yml`中有，但是`ebank.yml`中没有，其在`application.yml`的配置属性也不会生效，二者是竞争关系而不是合作关系】

     ```java
     2022-07-19 16:46:00.360  INFO 16480 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 81 (http)
     ```

     当然如果你想多个名称都可以当作配置文件也可以，比如这样：【但是要注意一点的就是这样配置的优先级越是后面配置的优先级越高，比如这里有`1 2 3`按照顺序配置，那么最高采用的都先采用`3`其次采用`2`最后采用`1`依次递减】

     比如这里，最优先配置的就是：`ebank-server.yml`这个配置文件

     ```java
     --spring.config.name=ebank,application,ebank-server
     ```

  2. **第二种方式：使用临时属性配置配置文件路径【可以是绝对路径，也可以是类路径】**

     ```java
     --spring.config.location=classpath:/ebank.yml,classpath:/ebank-server.yml
     ```

  3. **第三种方式：使用微服务配置中心集中管理配置文件**

## 多环境开发

企业开发一般分为三种环境：生产环境、开发环境、测试环境，每个不同的环境其配置文件大概率是不相同的，所以可以给每一个环境都配置一个配置文件，再具体使用哪个时用哪个就可以了。

### `yaml`版本单个文件的多环境开发

- 这里设置环境名的时候可以使用`spring.profiles`也可以使用`spring.config.activate.om-profile`只不过前者`SpringBoot`官方不推荐使用了而已。

- 使用`---`即可区分不同的环境配置

  ```yaml
  #应用环境
  spring:
    profiles:
      active: dev
  #设置环境
  ---
  #生产环境
  spring:
    config:
      activate:
        on-profile: pro
  server:
    port: 80
  ---
  #开发环境
  spring:
    profiles: dev
  server:
    port: 81
  ---
  #测试环境
  spring:
    config:
      activate:
        on-profile: test
  server:
    port: 82
  ```

### `yaml`版本多个文件的多环境开发

将各种环境的配置信息全部放在同一个文件是相当不安全的，为此我们需要分开来放不同环境的配置文件，这样做不仅可以增加安全性还有利于更好地维护不同环境地配置信息。

**主配置文件通常用于配置通用属性，而环境分类配置文件通常用于设置冲突属性。**

生产环境配置文件`application-pro.yml`：

```yaml
server:
  port: 80
```

开发环境配置文件`application-dev.yml`：

```yaml
server:
  port: 81
```

测试环境配置文件`application-test.yml`：

```yaml
server:
  port: 82
```

主配置文件`application-pro.yml`：

```yaml
spring:
  profiles:
    active: pro
```

### `properties`版本多个文件的多环境开发

### `Maven`和`SpringBoot`多环境控制开发

```xml
<profiles>
    <profile>
        <id>env_dev</id>
        <properties>
            <profile.active>dev</profile.active>
        </properties>
    </profile>
    <profile>
        <id>env_pro</id>
        <properties>
            <profile.active>pro</profile.active>
        </properties>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
    </profile>
</profiles>
```

```yaml
spring:
  profiles:
    active: @profile.active@
    group:
      "dev": devDB, devRedis, devMVC
      "pro": proDB, proRedis, proMVC
      "test": testDB, testRedis, testMVC
```

## 日志

### 日志基础

- 日志的作用

  - 编程期调试代码
  - 运营期记录信息
    - 记录日常运营重要信息（峰值流量、平均响应时长......）
    - 记录应用报错信息（错误堆栈）
    - 记录运维过程数据（扩容、宕机、报警......）

- 日志相关操作

  - `private static final Logger logger = LoggerFactory.getLogger(BookController.class)`

  - ```java
    logger.debug("debug...");
    logger.info("info...");
    logger.warn("warn...");
    logger.error("error...");【error + fatal([理论上的]灾难性的后果：系统处于崩溃级别)】
    ```

  - 日志级别：`debug < info < warn < error`，会显示`>=`当前级别的日志信息

  - 默认输出的日志都是`info`级别的，如果要调低级别输出`debug

    1. 可以加临时参数：`--debug`【输出调试信息，常用于检查系统运行状况】

    2. 可以在`application.yml`加入：`debug: true`【设置日志级别，`root`表示根节点，即整体应用日志级别】

    3. 可以在`aplication.yml`加入：`logging: level: root: debug`

       甚至可以设置某个包的日志级别：`logging: level: com.kk.controller: debug`

       设置包分组，对某个组进行设置：

       ```yaml
       logging:
         group:
         	ebanK: com.kk.controller,com.kk.service,com.kk.mapper
         	iservice: com.alibaba
         level:
         	root: debug
         	ebank: warn
       ```

  - ```java
    package com.kk.controller;
    
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    @RestController
    @RequestMapping(value = "/books")
    public class BookController {
    
        private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    
        @GetMapping
        public String getBook() {
            logger.debug("debug...");
            logger.info("info...");
            logger.warn("warn...");
            logger.error("error...");
            return "SpringBoot Run...";
        }
    }
    ```

    ```yaml
    server:
      port: 80
    logging:
      level:
        root: debug
    ```

    ```yaml
    server:
      port: 80
    logging:
      group:
        ebanK: com.kk.controller,com.kk.service,com.kk.mapper
        iservice: com.alibaba
      level:
        com.kk.controller: debug
        root: debug
        ebank: debug
    ```

  - 问题：`private static final Logger logger = LoggerFactory.getLogger(BookController.class);`这句代码需要反反复复不断重复地写，有什么办法可以省掉这个麻烦吗？

    要想所有的类都默认有这个东西，那我们使用继承不就可以了吗？

    父类代码如下：

    ```java
    package com.kk.controller;
    
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.stereotype.Component;
    
    public class BaseLog {
        public static Logger logger;
    
        public BaseLog() {
            logger = LoggerFactory.getLogger(this.getClass());
        }
    }
    ```

    子类代码直接使用`logger`：

    ```java
    package com.kk.controller;
    
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    @RestController
    @RequestMapping(value = "/books")
    public class BookController2 extends BaseLog {
    
        @GetMapping
        public String getBook() {
            logger.debug("debug...");
            logger.info("info...");
            logger.warn("warn...");
            logger.error("error...");
            return "SpringBoot Run...";
        }
    }
    ```

    第二种方法：直接使用`lombok`给的`@Slf4j`

    添加依赖：

    ```xml
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
    ```

    添加`@Slf4j`注解，默认的变量名称为：`log`，其相当于添加了：`private static final Logger log = LoggerFactory.getLogger(BookController2.class);`

    ```java
    package com.kk.controller;
    
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    @RestController
    @RequestMapping(value = "/books")
    @Slf4j
    public class BookController2 extends BaseLog {
    
        @GetMapping
        public String getBook() {
            log.debug("debug...");
            log.info("info...");
            log.warn("warn...");
            log.error("error...");
            return "SpringBoot Run...";
        }
    }
    ```


### 日志输出格式控制

输出的日志就是常见的服务器启动的信息展示，其格式为：**时间[`%date`简写`%d`]** **日志级别[`%clr(%Sp)`]**  **进程`PID`[多进程时非常有效]** **进程名[`%thread`简称`%t`]** **类名[`%class`简称`%c`]** **`%n`表示换行**

【上述设置网上都有】

```yaml
logging:
  pattern:
    console: "%d - %clr(%p) --- [%16t] %clr(%-40.40c){cyan} : %m %n"
```

### 文件日志文件

将日志记录到文件中 ---> 配置文件：

```yaml
logging:
  file:
    name: server.log
```

要是运行时间过长，岂不是日志文件大小要非常非常大？【`windows`打开`40M`的都麻烦，`4G`根本都打不开】，所以需要份文件记录日志 ---> 滚动记录日志

```yaml
logging:
  file:
    name: server.log
  logback:
    rollingpolicy:
      max-file-size: 3KB
      file-name-pattern: server.%d{yyyy-MM-dd}.%i.log
```

# `SpringBoot`开发实用篇

## 热部署

### 启动热部署

代码修改完毕立马生效，不必重新启动服务器 ===> 这就需要用到热部署。 ===> `pom.xml`添加`spring-boot-devtools`

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
    <artifacted>spring-boot-devtools</artifacted>
</dependency>
```

然后点击`IDEA`最上方`build`再点击`build project`构建项目，此时才可以正常地执行热部署操作。`ctrl + F9` ===> 激活热部署。

【此时就不用再消耗大量地资源去消耗，而是去重载，只加载更改的部分，加载的资源大大的减少了】

理解热部署需要理解两个概念：【程序第一次启动时既有重启也有重载】热部署对应着的就是**重启**这个过程

- 重启`Restart`：自定义开发代码，包含类、页面、配置文件等，加载位置`Restart`类加载器
- 重载`ReLoad`：`jar`包，加载位置`base`类加载器

重启和重载是两个过程。第一次启动项目有这两个过程，热部署不会去加载`jar`资源，所以只有重启这一个过程。**热部署仅仅加载当前开发者自定义开发的资源而不加载`jar`资源。**

<hr/>

### 自动热部署

总是要去点击`ctrl + F9`激活热部署非常的麻烦，能否自动修改完毕代码保存后自动激活热部署呢？当然可以。

只需要：

1. `settings ---> compiler ---> 点击 build project automatically`

2. 第二步要不要做取决于`IDEA`的版本，新版本的`IDEA`执行到第一步就可以自动热部署了，老版本的`IDEA`还需要：**`ctrl + shift + alt + /`**打开`Registry`直接输入**`comiler.automake.allow.app.running`**勾选上即可进行自动热部署

   新版本的`IDEA`设置自动热部署稍微有些许麻烦，要勾选三个选项才可进行热部署：

   勾选：

   - `settings ---> Build ---> compiler ---> Build project automatically `
   - `settings ---> Build ---> compiler ---> Rebuild module on dependency change`
   - `settings ---> Advanced Settings ---> 最上面的 Allow auto-make to start even if developed...`

此时当`IDEA`失去焦点`5s`之后将进行自动构建项目，为什么要这样设计？

- 如果不这样设计你每修改两个字就自动构建，那不是一件很疯狂的举动吗？所以失去焦点`5s`之后再自动构建这项设计非常地合理。

自动热部署有些文件是不参与的，比如：前端文件，`Meta-INF`目录下的文件都是不参与热部署的。

因为`devtools`是`SpringBoot`中的一个工具，所以如果要排除某些文件的热部署可以在`yaml`配置文件中配置，使用`devtools.restart.exclude`即可排除不参与热部署的文件。

```yaml
spring: 
  devtools:
  restart:
    exclude: static/**,config/application.yml
```

### 禁用热部署

禁用热部署有很多种方式，并且每种方式的级别是不同的，可以通过配置文件关闭热部署：

```yaml
spring:
  devtools:
  	restart:
      enabled: false
```

但是这种设置的级别是非常低的，一旦有比它更高的设置比如在`config/application.yml`，在`file/application.yml`甚至是在`file/config/application.yml`中设置了`spring.devtools.restart.enabled=true`就可以将热部署打开了。

所以我们另外介绍一种更高级的方式去关闭热部署 ---> 使用`System.getProperty("", "")`：

```java
package com.kk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.getProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(Application.class, args);
    }
}
```

## 配置高级

### `configurationProperties`

- 可以给自定义类配置属性

  ```java
  package com.kk.pojo;
  
  import lombok.Data;
  import org.springframework.boot.context.properties.ConfigurationProperties;
  import org.springframework.stereotype.Component;
  
  @Component
  @Data
  @ConfigurationProperties(prefix = "servers")
  public class Servers {
      private String ipAddress;
      private String port;
      private Integer timeout;
  }
  ```

  ```yaml
  servers:
    ipAddress: 192.168.0.100
    port: 9527
    timeout: -1
  ```

  ```java
  package com.kk;
  
  import com.kk.pojo.Servers;
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  import org.springframework.boot.context.properties.EnableConfigurationProperties;
  import org.springframework.context.ConfigurableApplicationContext;
  
  @SpringBootApplication
  @EnableConfigurationProperties(value = {Servers.class})
  public class SpringBootDemo12ConfigurationApplication {
      public static void main(String[] args) {
          ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(SpringBootDemo12ConfigurationApplication.class, args);
          Servers servers = configurableApplicationContext.getBean(Servers.class);
          System.out.println(servers);
      }
  }
  ```

  注意，如果在这里使用了`@EnableConfigurationProperties(value = {Servers.class})`那就无需再在`Servers`类中使用`@Component`注解，否则一个`Spring`容器内将会产生两个`Servers`类，这是不被允许的。

  或者你要么使用`getBean("servers")`获取对象，但是这样你必须在`Servers`类中书写`@Component`。

- 也可以给第三方`Bean`配置属性，跟给自定义类配置属性没什么差别【而自定义类配置属性在学习`yaml`配置文件的时候也已经说过】

  - ```java
    @Bean
    @ConfigurationProperties(prefix = "datasource")
    public DruidDataSource getDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }
    ```

  - ```yaml
    datasource:
      driverClassName: com.mysql.cj.jdbc.Driver
    ```

  - ```java
    package com.kk;
    
    import com.alibaba.druid.pool.DruidDataSource;
    import com.kk.pojo.Servers;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.boot.context.properties.ConfigurationProperties;
    import org.springframework.boot.context.properties.EnableConfigurationProperties;
    import org.springframework.context.ConfigurableApplicationContext;
    import org.springframework.context.annotation.Bean;
    
    @SpringBootApplication
    @EnableConfigurationProperties(value = {Servers.class})
    public class SpringBootDemo12ConfigurationApplication {
    
        @Bean
        @ConfigurationProperties(prefix = "datasource")
        public DruidDataSource getDataSource() {
            DruidDataSource druidDataSource = new DruidDataSource();
            return druidDataSource;
        }
    
        public static void main(String[] args) {
            ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(SpringBootDemo12ConfigurationApplication.class, args);
            Servers servers = configurableApplicationContext.getBean(Servers.class);
            System.out.println(servers);
            DruidDataSource druidDataSource = configurableApplicationContext.getBean(DruidDataSource.class);
            System.out.println(druidDataSource.getDriverClassName());
        }
    }
    ```

- **<font color="red">注意在使用`@ConfigurationProperties(prefix = "xxx")`时，其属性`prefix`的值应保持小写。</font>**

  **绑定的前缀名命名规范：仅能使用小写字母、数字、下划线作为合法的字符并且必须是字母开头【按正常的规范写就可以了】**

- 在匹配属性时的配置文件即`application.yml`中，其属性名跟类属性名匹配采用宽松绑定的原则。即你无论怎么写都是可以的，它会忽略大小写、中划线、下划线去匹配，非常的宽松！

  **官方建议的写法是烤肉串模式即：`ip-address`这种格式。**

  这种宽松绑定的方式只在`@ConfigurationProperties`才有效，如果你使用的是`@Value`赋予属性值，则是无效的。

### 常用计量单位[时间+数据]

- 常用计量单位的应用：比如这里的`timeout: -1`表示的是不限制，但是如果`timeout: 2`那这个单位是多少呢？我们现在无法定量。使用`Duration`和`long`表示的单位都是`ms毫秒`，不过`Duration`不仅仅限于毫秒，使用`@DurationUnit(ChronoUnit.HOURS)`等可以更换单位。

  ```java
  package com.kk.pojo;
  
  import lombok.Data;
  import org.springframework.boot.context.properties.ConfigurationProperties;
  import org.springframework.boot.convert.DurationUnit;
  
  import java.time.Duration;
  import java.time.temporal.ChronoUnit;
  
  @Data
  @ConfigurationProperties(prefix = "servers")
  public class Servers {
      private String ipAddress;
      private String port;
      private long timeout;
      @DurationUnit(ChronoUnit.DAYS)
      private Duration serverTimeout;
  }
  ```

  ```yaml
  servers:
    ipAddress: 192.168.0.100
    port: 9527
    timeout: 3
    server-timeout: 100
  datasource:
    driverClassName: com.mysql.cj.jdbc.Driver
  ```

  此时打印的结果为：`Servers(ipAddress=192.168.0.100, port=9527, timeout=3, serverTimeout=PT2400H)`

  除了上述的时间计量单位，还有数据计量单位：`B KB MB GB TB`等：可以直接在配置文件中配置`10MB 100GB`这样，虽然结果还是会转换为`B`，但是已经不用我们写了

  ```yaml
  servers:
    ipAddress: 192.168.0.100
    port: 9527
    timeout: 3
    server-timeout: 100
    data-size: 10MB
  datasource:
    driverClassName: com.mysql.cj.jdbc.Driver
  ```

  ```java
  package com.kk.pojo;
  
  import lombok.Data;
  import org.springframework.boot.context.properties.ConfigurationProperties;
  import org.springframework.boot.convert.DurationUnit;
  import org.springframework.util.unit.DataSize;
  
  import java.time.Duration;
  import java.time.temporal.ChronoUnit;
  
  @Data
  @ConfigurationProperties(prefix = "servers")
  public class Servers {
      private String ipAddress;
      private String port;
      private long timeout;
      @DurationUnit(ChronoUnit.DAYS)
      private Duration serverTimeout;
      private DataSize dataSize;
  }
  ```

  输出结果为【注意看最后面的数字单位】：

  ```java
  Servers(ipAddress=192.168.0.100, port=9527, timeout=3, serverTimeout=PT2400H, dataSize=10485760B)
  ```

  同时还可以像`@DurationUnit(ChronoUnit.xxx)`那样修改计量单位，不过这里的数字计量单位使用的是：`@DataSizeUnit(DataUnit.B/K/M/G/T)`，如下：【但是最后显示的结果还是`B`，只是不需要我们直接转化了】

  ```java
  @DataSizeUnit(DataUnit.MEGABYTES)
  private DataSize dataSize;
  ```

  ```java
  Servers(ipAddress=192.168.0.100, port=9527, timeout=3, serverTimeout=PT100H, dataSize=10737418240B)
  ```

### 数据校验

- 有时候在类文件中是`Integer`但是在配置文件中传递的是一个字符串，那后台铁定是无法传递的了，此时要怎么办呢？如果此时可以做格式校验就好了。

  开启数据校验有助于系统安全，`Java`提供了一组有关数组校验的`API`。 ---> `MVNRepository`搜索`validation`导入依赖：`JSR303`规范。

  然后只需要在要做数据校验的类添加`@Validate`注解即可，然后在各个需要做数据校验的字段添加各种校验，比如：该最大数值不能超过`8888`，则为：`@Max(value = 8888, message = "最大值不能超过8888")`再比如：该最小数值不能小于`6666`，则为：`@Min(value = 6666, message = "最小值不能小于6666")`

  添加`validation`相关依赖：【`SpringBoot`已经整合了`validation-api`所以无需定义版本】

  ```xml
  <dependency>
      <groupId>javax.validation</groupId>
      <artifactId>validation-api</artifactId>
  </dependency>
  ```

  使用：【注意这里的`@Validated`和`@Max`两个注解】

  ```java
  package com.kk.pojo;
  
  import lombok.Data;
  import org.springframework.boot.context.properties.ConfigurationProperties;
  import org.springframework.boot.convert.DataSizeUnit;
  import org.springframework.boot.convert.DurationUnit;
  import org.springframework.util.unit.DataSize;
  import org.springframework.util.unit.DataUnit;
  import org.springframework.validation.annotation.Validated;
  
  import javax.validation.constraints.Max;
  import java.time.Duration;
  import java.time.temporal.ChronoUnit;
  
  @Data
  @ConfigurationProperties(prefix = "servers")
  @Validated
  public class Servers {
      private String ipAddress;
      @Max(value = 8888, message = "最大值不能超过 8888")
      private String port;
      private long timeout;
      @DurationUnit(ChronoUnit.HOURS)
      private Duration serverTimeout;
      @DataSizeUnit(DataUnit.GIGABYTES)
      private DataSize dataSize;
  }
  ```

  本以为可以了，但是运行程序报错：报错信息显示说虽然`Validation API`已经在类路径了但是没有实现类存在于类路径，需要添加实现类到类路径，比如添加：`Hibernate Validator`校验器。

  ```java
  Description:
  
  The Bean Validation API is on the classpath but no implementation could be found
  
  Action:
  
  Add an implementation, such as Hibernate Validator, to the classpath
  ```

  那我们就去`mvnrepository`中去找这个`Hibernate Validator`校验器：找到 ---> `[Hibernate Validator Engine]`，将依赖导入`pom.xml`中去

  ```java
  <dependency>
      <groupId>org.hibernate.validator</groupId>
      <artifactId>hibernate-validator</artifactId>
  </dependency>
  ```

  导入完毕我们再去重新运行下这个程序，可以发现校验器已经生效了，我们将`port`的大小设置为：`9999`很明显超过了`8888`，故会报错，并且告诉你要去更新你的`application.yml`配置文件中的配置信息：

  ```java
  Description:
  
  Binding to target org.springframework.boot.context.properties.bind.BindException: Failed to bind properties under 'servers' to com.kk.pojo.Servers failed:
  
      Property: servers.port
      Value: a
      Origin: class path resource [application.yml] - 3:9
      Reason: 最大值不能超过 8888
  
  
  Action:
  
  Update your application's configuration
  ```

  可以点击`@Max`然后点击`package javax.validation.constraints;`包中的`constraints`就可以发现，有许许多多的校验器是可供我们使用的。

### 进制数据转换规则

在`application.yaml`配置文件中，会有一些字面值的表达方式，在配置文件中，`int`类型的表示是支持二进制、八进制跟十六进制的。二进制以`0B/b`开头，八进制以`0`开头，十六进制以`0X/x`开头。

**<font color="red">所以如果你的数据库密码`password`属性写的是`0127`这种以`0`开头的，那么最结果将转换为`87`，这就导致了密码不正确的问题，所以遇到这种情况，需要转换为`""`加个引号转换为字符串。</font>**

```java
boolean: TRUE //TRUE FALSE true false True False 均可
float: 3.14 //6.8523015e+5支持科学计数法
int: 123 //支持二进制、八进制、十六进制
null: ~ //使用 ~ 即可表示 null
string: HelloWorld //字符串可以直接书写，如果是数字将按照 float 跟 int 类型先判别
string2: "Hello World" //可以使用双引号包裹特殊字符
date: 2018-02-18 //日期必须使用yyyy-MM-dd格式
datetime: 2018-02-18T17:02:31+08:00 //时间和日期之间使用T连接，最后使用+代表时区
```

```yaml
datasource:
  driverClassName: com.mysql.cj.jdbc.Driver
  password: 0127
```

```java
@Test
void contextLoads() {
    System.out.println(password);
}
```

输出结果为：`87`

## 测试

### 加载测试专用属性

加载属性有三种方法：

1. 想想就是在`application.yml`配置文件中配置`test: prop: testValue`【仅用于做测试】然后使用`@Value`加载，这个之前就已经见过了

   ```yaml
   test:
     prop: testValue
   ```

   ```java
   package com.kk;
   
   import org.junit.jupiter.api.Test;
   import org.springframework.beans.factory.annotation.Value;
   import org.springframework.boot.test.context.SpringBootTest;
   
   @SpringBootTest
   class SpringBootDemo13TestApplicationTests {
   
       @Value(value = "${test.prop}")
       private String testProp;
   
       @Test
       void contextLoads() {
           System.out.println(testProp);
       }
   }
   ```

2. 在`@SpringBootTest(properties = "test.prop=testValue1")`因为`properties`的级别比`yaml`级别高所以会优先加载`testValue1`

   ```java
   package com.kk;
   
   import org.junit.jupiter.api.Test;
   import org.springframework.beans.factory.annotation.Value;
   import org.springframework.boot.test.context.SpringBootTest;
   
   @SpringBootTest(properties = {"test.prop=testValue1"})
   class SpringBootDemo13TestApplicationTests {
   
       @Value(value = "${test.prop}")
       private String testProp;
   
       @Test
       void contextLoads() {
           System.out.println(testProp);
       }
   }
   ```

3. 在`@SpringBootTest(args = "--test.prop=testValue2")`因为当前`SpringBoot`版本的原因为`2.7.x`临时属性的加载级别最高所以这里会优先加载`properties`的。按照以前的`2.5.x 6.x`的版本，则会优先加载`args`，这里因为版本不同所以会有差别，可以不必太纠结，到时候直接试一试就知道哪个优先级高了。

   ```java
   package com.kk;
   
   import org.junit.jupiter.api.Test;
   import org.springframework.beans.factory.annotation.Value;
   import org.springframework.boot.test.context.SpringBootTest;
   
   @SpringBootTest(properties = {"test.prop=testValue1"}, args = {"--test.prop=testValue2"})
   class SpringBootDemo13TestApplicationTests {
   
       @Value(value = "${test.prop}")
       private String testProp;
   
       @Test
       void contextLoads() {
           System.out.println(testProp);
       }
   }

### 加载测试专用配置【类】

测试专用配置类比于`MPConfig`，使用`@Import(value = {MgConfig.class})`就加载了配置类了。举例：配置类如下：【这种做法有利于我们只需要在某个测试类中使用配置时可以用】

这里为了体现如果出现了两个相同返回类型的情况，特意搞了两个`String`类型返回。特此表明在使用配置类时需要跟其名字保持一致，`msg1`就是对象名称。

```java
package com.kk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MgConfig {
    @Bean
    public String msg1() {
        return "bean msg1";
    }

    @Bean
    public String msg2() {
        return "bean msg2";
    }
}
```

导入、使用配置类：【注意这里的`msg1`】

```java
package com.kk;

import com.kk.config.MgConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(value = {MgConfig.class})
public class ConfigTest {

    @Autowired
    private String msg1;

    @Test
    void msg() {
        System.out.println(msg1);
    }
}
```

### `Web`环境模拟测试

在`@SpringBootTest`中使用`@webEnvironment`属性就可以自定是否使用`web`。

在`pom.xml`中更改下依赖：【新建时没有导入】

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

然后就可以使用`webEnvironment`属性了：

不启用`web`：注意这里的`SpringBootTest.WebEnvironment.NONE`

```java
package com.kk.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class WebTest {
    @Test
    void test() {
    }
}
```

按定义的端口启动：注意这里的`SpringBootTest.WebEnvironment.DEFINED_PORT`

```java
package com.kk.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WebTest {
    @Test
    void test() {

    }
}
```

按随机的端口启动：注意这里的`SpringBootTest.WebEnvironment.RANDOM_PORT`

```java
package com.kk.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebTest {
    @Test
    void test() {

    }
}
```

创建表现层代码：

```java
package com.kk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/books")
public class BookController {
    @GetMapping
    public String getById() {
        System.out.println("GET BY ID......");
        return "Get By Id...SpringBoot";
    }
}
```

接下来就是模拟发送请求调用接口的代码了：【这种发送的请求是虚拟的，模拟的，不是真实去调用】

需要使用到`@AutoConfigureMockMvc`去模拟调用接口，然后调用接口的时候使用`MockMvc mokckMvc.perform`去调用`MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/books")`【这是一个实现类，可以通过源代码找到】

```java
package com.kk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WebTest {
    @Test
    void test(@Autowired MockMvc mockMvc) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/books");
        mockMvc.perform(mockHttpServletRequestBuilder);
    }
}
```

预期结果保存在：`ResultActions`

虚拟请求状态匹配：

```java
@Test
void testStatus(@Autowired MockMvc mockMvc) throws Exception {
    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/books1");
    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    StatusResultMatchers statusResultMatchers = MockMvcResultMatchers.status();
    ResultMatcher ok = statusResultMatchers.isOk();
    System.out.println(resultActions.andExpect(ok));
}
```

返回结果：

```java
java.lang.AssertionError: Status expected:<200> but was:<404>
Expected :200
Actual   :404
<Click to see difference>
```

虚拟请求匹配响应体：

```java
@Test
void testContent(@Autowired MockMvc mockMvc) throws Exception {
    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/books");
    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    ContentResultMatchers contentResultMatchers = MockMvcResultMatchers.content();
    ResultMatcher content = contentResultMatchers.string("SpringBoot");
    resultActions.andExpect(content);
}
```

返回结果：

```java
java.lang.AssertionError: Response content expected:<SpringBoot> but was:<Get By Id...SpringBoot>
Expected :SpringBoot
Actual   :Get By Id...SpringBoot
<Click to see difference>
```

虚拟请求匹配响应头：

```java
@Test
void testHeader(@Autowired MockMvc mockMvc) throws Exception {
    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/books");
    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    HeaderResultMatchers headerResultMatchers = MockMvcResultMatchers.header();
    ResultMatcher resultMatcher = headerResultMatchers.string("characterSet", "aaa");
    resultActions.andExpect(resultMatcher);
}
```

写测试的时候应该将：状态、响应体、响应头都放在一块形成一大块的代码。

```java
package com.kk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.HeaderResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WebTest {
    @Test
    void test(@Autowired MockMvc mockMvc) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/books1");
        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
        StatusResultMatchers statusResultMatchers = MockMvcResultMatchers.status();
        ResultMatcher ok = statusResultMatchers.isOk();
        System.out.println(resultActions.andExpect(ok));
    }

    @Test
    void testStatus(@Autowired MockMvc mockMvc) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/books1");
        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
        StatusResultMatchers statusResultMatchers = MockMvcResultMatchers.status();
        ResultMatcher ok = statusResultMatchers.isOk();
        System.out.println(resultActions.andExpect(ok));
    }

    @Test
    void testContent(@Autowired MockMvc mockMvc) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/books");
        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
        ContentResultMatchers contentResultMatchers = MockMvcResultMatchers.content();
        ResultMatcher content = contentResultMatchers.string("SpringBoot");
        resultActions.andExpect(content);
    }

    @Test
    void testHeader(@Autowired MockMvc mockMvc) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/books");
        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
        HeaderResultMatchers headerResultMatchers = MockMvcResultMatchers.header();
        ResultMatcher resultMatcher = headerResultMatchers.string("characterSet", "aaa");
        resultActions.andExpect(resultMatcher);
    }
}
```

### 业务层测试事务回滚

测试环境中的数据不必真实保存到数据库中，但是按照之前的做法，是会保存的。

```java
package com.kk;

import com.kk.pojo.Book;
import com.kk.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceTest {
    @Autowired
    private BookService bookService;

    @Test
    void testService() {
        Book book = new Book();
        book.setType("SpringBoot");
        book.setName("SpringBoot");
        book.setDescription("SpringBoot");
        bookService.save(book);
    }
}
```

怎么做才能让数据不保存到数据库呢？ ---> 事务回滚。【`@Transactional` + `@Rollback`】默认直接加`@Transactional`后`SpringBoot`就认为你要做事务回滚。

`@Rollback`有两个值，一个`true`一个`false`，可以控制回不回滚。

发现不起作用，原因是这个`sql`是我从一个`github`上复制粘贴过来的，注意看下这里的数据库引擎：

```sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_book
-- ----------------------------
DROP TABLE IF EXISTS `tbl_book`;
CREATE TABLE `tbl_book`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
```

这里使用的数据库引擎为：`MyISAM`，是不支持事务的，需要将其更改为`INNODB`数据库引擎。然后再进行测试，可以发现事务回滚。

`@Rollback(value = false)`此时不回滚：

```java
package com.kk;

import com.kk.pojo.Book;
import com.kk.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback(value = false)
public class ServiceTest {
    @Autowired
    private BookService bookService;

    @Test
    void testService() 
        Book book = new Book();
        book.setType("SpringBoot");
        book.setName("SpringBoot");
        book.setDescription("SpringBoot");
        bookService.save(book);
    }
}
```

### 测试用例设置随机数据

可以发现测试数据是固定的，如何设定随机数据呢？---> 在`application.yml`中设定然后使用那一套`@ConfigurationProperties(prefix = "testcase")`即可。

```java
testcase:
  id: ${random.int(10,100)}
  name: ${random.value}
  type: ${random.value}}
  description: ${random.long}
```

```java
package com.kk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "testcase")
public class TestCase {
    private String id;
    private String name;
    private String type;
    private String description;
}
```

## 数据层解决方案

- 当前使用的数据层解决方案：`Druid + MyBatis-Plus + MySql`
  - 数据源：`Druid`
  - 持久化：`MyBatis-Plus`
  - 数据库：`MySql`

### 内置数据源【默认数据源解决方案】

内置数据源有三：`HikariCP + Tomcat提供 + Commons DBCP`

是否使用`Druid`数据源，早在引入`Druid`依赖就已经确定下来了。就算你不在`application.yml`中指定`druid./:`，照样用的还是`Druid`数据源

```java
2022-07-22 11:17:28.184  INFO 5992 --- [           main] c.a.d.s.b.a.DruidDataSourceAutoConfigure : Init DruidDataSource
2022-07-22 11:17:28.253  INFO 5992 --- [           main] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
```

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.2.11</version>
</dependency>
```

当你把这个依赖去掉时，使用的数据源为`SpringBoot`内嵌的默认数据源`HikariCP`，除此之外`SpringBoot`还提供了`Tomcat`提供的`DataSource`以及`Commons DBCP`两种数据源可供开发者选择：

- `HikariCP`【默认内置数据源对象，官方推荐】
- `Tomcat`提供的`DataSource`【`HikariCP`不可用并且在`web`环境中】
- `Commons DBCP`【`HikariCP`和`Tomcat`提供的数据源都不可用】

若想做进一步配置，可以在基础配置之上再做个性化配置：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/ssm?useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
    hikari:
      maximum-pool-size: 50
```

### `JDBCTemplate`【默认持久化解决方案】

要使用`JDBCTemplate`需先将`MyBatis-Plus`依赖去掉。`mybatis-plus-boot-starter`内带了`spring-boot-starter-jdbc`【可以通过`Maven`进行查看】，我们现在要使用的就是`spring-boot-starter-jdbc`。

引入`spring-boot-starter-jdbc`依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```

```java
package com.kk;

import com.kk.pojo.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringBootDemo14SqlApplicationTests {

    @Test
    void contextLoads(@Autowired JdbcTemplate jdbcTemplate) {
        String sql = "select * from tbl_book";
        List<Map<String, Object>> books = jdbcTemplate.queryForList(sql);
        System.out.println(books);
    }

    @Test
    void testList(@Autowired JdbcTemplate jdbcTemplate){
        //Map 很难用我们有一套更标准的方法
        String sql = "select * from tbl_book";
        RowMapper<Book> rowMapper = new RowMapper<Book>() {
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setType(rs.getString("type"));
                book.setDescription(rs.getString("description"));
                return book;
            }
        };
        List<Book> bookList = jdbcTemplate.query(sql, rowMapper);
        System.out.println(bookList);
    }
}
```

### `H2`数据库【默认数据库解决方案】

`SpringBoot`内嵌了三种数据库供开发者选择，用于提高开发者测试效率。【都是拿`Java`程序写的】

- `H2`
- `HSQL`
- `Derby`

以`H2`数据库为例引入依赖：

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>2.1.214</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
    <version>2.7.1</version>
</dependency>
```

添加`spring-boot-starter-web`依赖，因为要有启动`Tomcat`服务器。设置`H2`数据库需要在`Web`中设置。

设置`H2`相关配置：

```yaml
spring:
  h2:
    console:
      path: /h2
      enabled: true
server:
  prot: 80
```

启动服务器，进入网页`localhost/h2`，按照网页显示的内容配置数据源：

```yaml
spring:
  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:~/test
    username: sa
    password: 123456
    hikari:
      maximum-pool-size: 50
```

添加数据库：

```sql
create table tbl_book;
alter table tbl_book add column id int;
alter table tbl_book add column type varchar;
alter table tbl_book add column name varchar;
alter table tbl_book add column description varchar;
insert into tbl_book(id, type, name, description) values(1, 'SpringBoot1', 'SpringBoot2', 'SpringBoot3');
insert into tbl_book(id, type, name, description) values(2, 'SpringBoot3', 'SpringBoot4', 'SpringBoot5');
select * from tbl_book;
```

使用`JdbcTemplate`持久化进行测试：

```java
package com.kk;

import com.kk.pojo.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringBootDemo14SqlApplicationTests {

    @Test
    void contextLoads(@Autowired JdbcTemplate jdbcTemplate) {
        String sql = "select * from TBL_BOOK";
        List<Map<String, Object>> books = jdbcTemplate.queryForList(sql);
        System.out.println(books);
    }

    @Test
    void testList(@Autowired JdbcTemplate jdbcTemplate){
        //Map 很难用我们有一套更标准的方法
        String sql = "select * from tbl_book";
        RowMapper<Book> rowMapper = new RowMapper<Book>() {
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setType(rs.getString("type"));
                book.setDescription(rs.getString("description"));
                return book;
            }
        };
        List<Book> bookList = jdbcTemplate.query(sql, rowMapper);
        System.out.println(bookList);
    }
}
```

## 整合第三方技术

### `NoSQL`解决方案

这里由于不可变因素`Linux`虚拟机的网络只能使用`NAT`地址转换而无法使用桥接，要想成功连接还需要设定下端口转换：

```
TCP 192.168.56.1 22 10.0.2.15 22
```

然后使用`XSHELL`连接：`192.168.56.1:22`即可成功连接。

#### `Redis`篇

`Redis`是一款`key-value`存储结构的内存级的`NoSQL`数据库：

- 支持多种数据存储格式
- 支持持久化
- 支持集群

`redis`的安装就不多说了[博客都有收藏]，简单介绍下在`Linux`开启`Redis`在`Windows`中使用的命令：

首先需要进入到`redis.conf`，修改以下内容：

```java
protected-mode yes ---> protected-mode no[关闭保护模式]
bind 127.0.0.1 -::1 ---> #bind 127.0.0.1 -::1[禁用]
port 6379 ---> 可改可不改，实际生产中必须会改
daemonize yes ---> daemonize no[开启后台启用]

因为这里的网络是 NAT 地址转换的，所以需要做个端口转换：
TCP 192.168.56.1 9527 10.02.15 6379
```

```
Linux: ./redis-server ../redis.conf
Windows: redis-cli -h 192.168.56.1 -p 9527
```

如上操作之后即可成功连接。可以在`Windows set k1 v1`在`Linux get k1`查看。

- `SpringBoot`整合`Redis`

  1. 导入依赖
  
     ```xml
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-data-redis</artifactId>
     </dependency>
     ```
  
  2. 配置`application.yml`
  
     ```yaml
     spring:
       redis:
         host: 192.168.56.1
         port: 9527
     ```
  
  3. 类比于`JdbcTemplate`可以用`RedisTemplate`来操作`Redis`数据库
  
     `Redis`里面有很多类型的数据可以存储，所以操作前需要先确定数据类型：`redisTemplate.opsForHash`等等。
  
     ```java
     package com.kk;
     
     import org.junit.jupiter.api.Test;
     import org.springframework.beans.factory.annotation.Autowired;
     import org.springframework.boot.test.context.SpringBootTest;
     import org.springframework.data.redis.core.HashOperations;
     import org.springframework.data.redis.core.RedisTemplate;
     import org.springframework.data.redis.core.ValueOperations;
     
     @SpringBootTest
     class SpringBootDemo16RedisApplicationTests {
     
         @Autowired
         private RedisTemplate redisTemplate;
     
         @Test
         void contextLoads() {
     
         }
     
         @Test
         void set() {
             //问你准备操作哪种数据类型？
             //Cluster 集群、Hash、Geo 地理坐标、Value 最普通
             ValueOperations valueOperations = redisTemplate.opsForValue();
             valueOperations.set("k2", "v2");
         }
     
         @Test
         void get() {
             ValueOperations valueOperations = redisTemplate.opsForValue();
             Object object = valueOperations.get("k2");
             System.out.println(object);
         }
     
         @Test
         void hSet() {
              HashOperations hashOperations = redisTemplate.opsForHash();
              hashOperations.put("info", "name", "Tom");
         }
     
         @Test
         void hGet() {
             HashOperations hashOperations = redisTemplate.opsForHash();
             Object object = hashOperations.get("info", "name");
             System.out.println(object);
         }
     }
     ```
  
  4. 在`IDEA`中操作完毕的数据在`Liunx`中并不显示，这是为什么呢？
  
     `RedisTemplate<K, V>`通过源码可以发现操作的都是对象，如果想要让数据库也有数据，原`Redis`操作的都是字符串类型，所以需要的需要使用`StringRedisTemplate`，才能在数据库中显示。
  
     ```java
     @Test
     void stringSet() {
         ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
         valueOperations.set("stringRedisTemplate", "Yeah");
     }
     @Test
     void stringGet() {
         ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
         String value = valueOperations.get("stringRedisTemplate");
         System.out.println(value);
     }
     ```
  
     当`stringSet()`方法执行完毕之后可以通过`Linux Redis ---> get stringRedisTemplate`看到存在该数据。
  
     在`Linux`中更改，然后在`IDEA`中获取，打印：得到更改后的数据，证明是同一个。
  
     其实`RedisTemplate`跟`StringRedisTemplate`属于同根同源的，只不过一个操作的是对象，任何引用数据类型，一个是指定需要字符串类型。
  
  5. `Redis`有一个传统的操作客户端的技术：`Jedis`
  
     `SpringBoot`中默认的操作客户端技术就是：`Lettuce`【生菜】，**为什么有了`Lettuce`还需要使用`Jedis`呢？原因：考虑到系统升级，如果以前一直使用的都是`Jedis`，那最好还是不要换，换了可能会有一些莫名其妙的风险出现。**
  
     导入依赖：【并不用写版本号，说明在`SpringBootParent`已经定义了`jedis`】
  
     ```xml
     <dependency>
         <groupId>redis.clients</groupId>
         <artifactId>jedis</artifactId>
     </dependency>
     ```
  
     在`application.yml`中做配置：
  
     ```yaml
     spring:
       redis:
         host: 192.168.56.1
         port: 9527
         client-type: jedis
         lettuce:
           pool:
             max-active: 16
         jedis:
           pool:
             max-active: 16
     ```
  
     - `lettuce`（默认）和`jedis`的区别【了解】
       - `Jedis`连接`Redis`服务器是直连模式，当多线程模式下使用`Jedis`会存在线程安全问题，解决方案可以通过配置连接池使每个连接专用，这样整体性能就大受影响【安全 ---> 增加连接数 ---> 影响性能】（万一来连接数非常非常大呢？岂不是很慢？配再多能满足需求吗？）
       - `lettuce`基于`Netty`框架进行与`Redis`服务器连接，底层设计中采用`StatefulRedisConnection`。`StatefulRedisConnection`自身是是线程安全的，可以保障并发访问安全问题，所以一个连接可以被多线程复用。当然`lettuce`也支持多连接实例一起工作。
  
  6. 现在有一个问题：
  
     想操作结构化数据，并且需要很快的响应速度。
  
     我们知道`MySQL`数据库里面存储的就是结构化数据，但是它的响应速度是比较慢的。而`Redis`存储的是`key-value`数据，无法存储结构化数据，我们只能通过模拟的方式，这样的操作虽然响应速度很快很不方便。
  
     **<font color="red">有没有一种东西，它既能操作结构化数据，又能给予较快的响应速度呢？还真有，那就是：`MongoDB`</font>**

#### `MongoDB`篇

- `MongoDB`是一个开源、高性能、无模式的文档型数据库。是`NoSQL`中的一种。是最像关系型数据库的非关系型数据库。【可以操作结构化数据的同时以最快的响应速度响应】

- 应用场景：【总的来说就是适用于存储临时村塾，修改频度较高的数据】

  - 淘宝用户数据【存储位置：数据库 + 特征：永久性存储，修改频度极低】
  - 游戏装备数据/道具（有图片有数据【不适合存储在`Redis`】 ===> 需要响应速度快）【存储位置：`Mongodb` 特征：永久性存储和临时性存储相结合、修改频度较高】
  - 直播数据、打赏数据、粉丝数据【存储位置：`Redis Mongodb` 特征：临时性存储，修改频度非常高】
  - 物联网数据【存储位置：`Mongodb` + 特征：临时存储，修改频度飞速】

- 在这里简单说下在`Linux`中安装`MongoDB`的过程：

  - 解压然后修改名称，创建`data conf`目录

  - 进入到`conf`目录，创建`mongodb.conf`文件，内容为：

    ```shell
    port=27017
    bind_ip=0.0.0.0
    dbpath=/usr/local/app/mongodb/data
    logpath=/usr/local/app/mongodb/log
    fork=true
    ```

  - 配置环境变量

    ```shell
    vim /etc/profile
     
    #文件最后添加此配置（第一种）
    export MONGODB_HOME=/usr/local/app/mongodb
    export PATH=$PATH:${MONGODB_HOME}/bin
    ```

  - 启动`mongodb`：`/usr/local/app/mongodb/bin/mongodbd --config=/usr/local/app/mongodb/conf/mongodb.conf`

  - 然后在`windows`中使用`studio 3T`连接`192.168.56.1 27017`即可

- `Mongodb`的文档很像`JSON`但是不是`JSON`，在`Studio 3T`练习完成`MongoDB CRUD`基础操作

  ```shell
  db = db.getSiblingDB("study");
  //db.getCollection("book").find({});
  //db.book.insert({"name":"SpringBoot","type":"SpringBoot"})
  //db.book.insert({"name":"SpringBoot"});
  //db.book.find({"name":"SpringBoot"});
  //db.book.remove({"type":"SpringBoot"});
  db.book.find();
  //db.book.update({"name":"SpringBoot"},{$set:{"name":"SpringBoot2"}})
  ```

- `SpringBoot`整合`MongoDB`

  1. 导入依赖：`pom.xml`

     ```xml
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-data-mongodb</artifactId>
     </dependency>
     ```

  2. 修改配置：`application.yml`

     ```yaml
     spring:
       data:
         mongodb:
           #host: 192.168.56.1
           #port: 27017
           #database: study
           uri: mongodb://192.168.56.1:27017/study
     ```

  3. 测试：

     ```java
     package com.kk;
     
     import com.kk.pojo.Book;
     import org.junit.jupiter.api.Test;
     import org.springframework.beans.factory.annotation.Autowired;
     import org.springframework.boot.test.context.SpringBootTest;
     import org.springframework.data.mongodb.core.MongoTemplate;
     
     import java.util.List;
     
     @SpringBootTest
     class SpringBootDemo17MongoDbApplicationTests {
     
         @Autowired
         private MongoTemplate mongoTemplate;
     
         @Test
         void contextLoads() {
             Book book = new Book();
             book.setId(1);
             book.setName("《深入理解 Java 虚拟机》");
             book.setType("计算机科学");
             book.setDescription("作为一位Java程序员，你是否也曾经想深入理解Java虚拟机，但是却被它的复杂和深奥拒之门外？没关系，本书极尽化繁为简之妙，能带领你在轻松中领略Java虚拟机的奥秘。本书是近年来国内出版的唯一一本与Java虚拟机相关的专著，也是唯一一本同时从核心理论和实际运用这两个角度去探讨Java虚拟机的著作，不仅理论分析得透彻，而且书中包含的典型案例和最佳实践也极具现实指导意义。");
             mongoTemplate.save(book);
         }
     
         @Test
         void testFind() {
             List<Book> bookList = mongoTemplate.findAll(Book.class);
             System.out.println(bookList);
         }
     }
     ```

     **报错：因为之前设置的类型跟我们在这里设置的`int id = 1`冲突了，我们需要先删除掉`Object[xxx]`那一行的数据。**

#### `Elastic-Search`篇

- `ES`是一个分布式的全文搜索引擎。功能非常强大，是亿量级的非关系型数据库，并且速度是毫秒级。

  要想做全文搜索，首先要做的是分词先匹配到`id`然后获取数据 ---> **倒排索引【`ES`核心工作原理】**。每条数据就是一个文档。

- 本想在`Linux`安装`ES`结果一直出`bug`，很浪费时间，所以还是改成`Windows`先了，等哪天学习不那么紧张了再来尝试下在`Linux`安装`ES`。

  默认端口为：`9200`，`MonogoDB`默认端口为：`27017`，`Redis`默认端口为：`6379`

  启动`elasticsearch.bat`然后浏览`http://localhost:9200/`看到一堆`JSON`数据格式的字符串表明启动成功。

- **索引操作 + `IK`分词器安装 + 设置索引创建规则**

  通过`postman`即可向`ES`发送数据，添加数据等。**`Restful`风格**。

  比较特殊的是添加操作使用的是`PUT`而不是`POST`，这是因为要保证它的幂等性。

  ```xml
  http://localhost:9200/books [PUT DELTE GET]添加
  ```

  若要使用分词功能需要开启分词器。分词器就在`mapping`中使用，我们可以使用插件来进行分词：

  **将插件放入到`plugins/`目录中，创建`ik`文件夹，然后重启`ES`即可使用分词器。**

  ```json
  {
      "books": {
          "aliases": {},
          "mappings": {},
          "settings": {
              "index": {
                  "routing": {
                      "allocation": {
                          "include": {
                              "_tier_preference": "data_content"
                          }
                      }
                  },
                  "number_of_shards": "1",
                  "provided_name": "books",
                  "creation_date": "1658572860949",
                  "number_of_replicas": "1",
                  "uuid": "qy96z3zjQASHY57RAiXaTg",
                  "version": {
                      "created": "7160299"
                  }
              }
          }
      }
  }
  ```

  `Postman`发送请求：

  ```json
  {
      "mappings":{
          //装载属性
          "properties":{
              "id":{
                  //表示 id 是一个关键字
                  //"index":false 表示不参与查询
                  "type":"keyword"
              },
              "name":{
                  //告诉ES这个字段提供的是文本信息
                  "type":"text",
                  "analyzer":"ik_max_word",
                  "copy_to":"all"
              },
              "type":{
                	"type":"keyword"  
              },
              "description":{
                  "type":"text",
                  "analyzer":"ik_max_word",
                  "copy_to":"all"
              },
              //整合 name description ---> all 这个字段是虚拟出来的，不真实存在
              //主要用于查询
              "all":{
                  "type":"text",
                  "analyzer":"ik_max_word"
              }
          }
      }
  }
  ```

  发送`GET`请求：`http://localhost:9200/books`，得到如下结果【需要关闭`Raw-JSON`】：

  ````json
  {
      "books": {
          "aliases": {},
          "mappings": {
              "properties": {
                  "all": {
                      "type": "text",
                      "analyzer": "ik_max_word"
                  },
                  "description": {
                      "type": "text",
                      "copy_to": [
                          "all"
                      ],
                      "analyzer": "ik_max_word"
                  },
                  "id": {
                      "type": "keyword"
                  },
                  "name": {
                      "type": "text",
                      "copy_to": [
                          "all"
                      ],
                      "analyzer": "ik_max_word"
                  },
                  "type": {
                      "type": "keyword"
                  }
              }
          },
          "settings": {
              "index": {
                  "routing": {
                      "allocation": {
                          "include": {
                              "_tier_preference": "data_content"
                          }
                      }
                  },
                  "number_of_shards": "1",
                  "provided_name": "books",
                  "creation_date": "1658573588593",
                  "number_of_replicas": "1",
                  "uuid": "JRixpMzpST-L2yXACe1yJw",
                  "version": {
                      "created": "7160299"
                  }
              }
          }
      }
  }
  ````

- **`ES`文档操作：增删查改**

  1. 导入依赖

     ```xml
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
         <version>2.7.2</version>
     </dependency>
     ```

  2. 修改配置

     ```yaml
     spring:
       elasticsearch:
         uris: http://localhost:9200
     ```

  3. 使用`ElasticSearchRestTemplate`操作`ES`

     > 插播：这里`Maven`在清理了缓存之后没有出现提示了，只需要在`settings ---> Maven ---> Repository`点击`update`更新一下即可。

     ```java
     package com.kk;
     
     import com.kk.pojo.Book;
     import org.junit.jupiter.api.Test;
     import org.springframework.beans.factory.annotation.Autowired;
     import org.springframework.boot.test.context.SpringBootTest;
     import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
     
     @SpringBootTest
     class SpringBootDemo18ElasticSearchApplicationTests {
     
         @Autowired
         private ElasticsearchRestTemplate elasticSearchRestTemplate;
     
         @Test
         void contextLoads() {
             Book book = new Book();
             book.setId(1);
             book.setName("《深入理解 Java 虚拟机》");
             book.setType("计算机科学");
             book.setDescription("作为一位Java程序员，你是否也曾经想深入理解Java虚拟机，但是却被它的复杂和深奥拒之门外？没关系，本书极尽化繁为简之妙，能带领你在轻松中领略Java虚拟机的奥秘。本书是近年来国内出版的唯一一本与Java虚拟机相关的专著，也是唯一一本同时从核心理论和实际运用这两个角度去探讨Java虚拟机的著作，不仅理论分析得透彻，而且书中包含的典型案例和最佳实践也极具现实指导意义。");
             elasticSearchRestTemplate.save(book);
         }
     
     }
     ```

     但是这里使用不了，报错，报错信息为：意思就是说`Spring`容器中没有该类型的数据，这让我很怀疑...难道在这短短的时间内，`SpringBoot`跟`ES`的改版导致无法使用`ElasticSearchRestTemplate`吗？

     ```java
     org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'com.kk.SpringBootDemo18ElasticSearchApplicationTests': Unsatisfied dependency expressed through field 'elasticsearchRestTemplate'; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: 
     ```

     不过确实，`SpringBoot`版本升级的速度跟`ES`升级的速度对应不上，一般我们也不使用`ElasticSearchRestTemplate`而是使用`RestHighLevelClient`。具体使用方法请看下边：

     【顺带一提的是：在`ES7.15`版本之后，`ES`官方将它的高级客户端`RestHighLevelClient`标记为弃用状态，同时推出了全新的`Java API`客户端`ElasticSearch Java API Client`】

     1. 需要将给`ElasticSearchRestTeamplte`设置的所有信息都先注释掉，包括依赖、配置信息还有代码【代码可以保留】

     2. 导入`ElaticSearch-Rest-High-Level-Client`依赖：

        这里的系统`version`是要写的，这个依赖引入的是原生的`API`，`SpringBoot`并没有将其放入到容器中，所以需要自己去实现该类。该类已经被弃用了。

        ```xml
        <dependency>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>elasticsearch-rest-high-level-client</artifactId>
            <version>7.17.5</version>
        </dependency>
        ```

     3. 手动创建：

        ```java
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient();
        ```

        点开源码，发现创建`RestHighLevelClient`需要一个`RestClientBuilder`，那就给它一个：

        ```java
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
        ```

        然后发现需要传递一个`HttpHost`：于是尝试传递【写代码总是会遇到不会的，需要不断地去尝试才可以完成一个程序】

        ```java
        HttpHost httpHost = HttpHost.create("http://localhost:9200");
        ```

        合起来就是：

        ```java
        @BeforeEach
        void setUp() {
            //创建 RestHighLevelClientBuilder
            HttpHost httpHost = HttpHost.create("http://localhost:9200");
            RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
            restHighLevelClient = new RestHighLevelClient(restClientBuilder);
        }
        ```

        然后就是做一些客户端的操作：

        ```java
        restHighLevelClient.indices().create();
        ```

        发现`create()`方法需要传递`CreateIndexRequest`还有`RequestOptions`，那就新建一个`CreateIndexRequest`：

        ```java
        //创建索引【客户端操作】
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("books");
        restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        ```

        因为是手动创建的并且是写到`ES`中的，所以`RestHighLevelClient`隶属于`I/O`流，结尾需要手动关闭流。

        ```java
        restHighLevelClient.close();
        ```

        因为每次测试前都会开流关流，代码看起来非常的冗余，我们可以使用`SpringBootTest`提供的`setUp`还有`termDown`来实现开头和结尾必须实现的代码。所以总的代码合成起来就是：

        ```java
        package com.kk;
        
        import com.kk.pojo.Book;
        import org.apache.http.HttpHost;
        import org.elasticsearch.client.*;
        import org.elasticsearch.client.indices.CreateIndexRequest;
        import org.junit.jupiter.api.AfterEach;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.springframework.boot.test.context.SpringBootTest;
        
        import java.io.IOException;
        
        @SpringBootTest
        class SpringBootDemo18ElasticSearchApplicationTests {
        
            private RestHighLevelClient restHighLevelClient;
        
            @BeforeEach
            void setUp() {
                //创建 RestHighLevelClientBuilder
                HttpHost httpHost = HttpHost.create("http://localhost:9200");
                RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
                restHighLevelClient = new RestHighLevelClient(restClientBuilder);
            }
        
            @AfterEach
            void tearDown() throws IOException {
                //因为是手动创建的需要关闭客户端
                restHighLevelClient.close();
            }
        
            @Test
            void testESRestHighLevel() throws IOException {
                //创建索引【客户端操作】
                CreateIndexRequest createIndexRequest = new CreateIndexRequest("books");
                restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        
            }
        }
        ```

        测试完使用`PostMan`即可查看是否测试成功：

        ```java
        {
            "books": {
                "aliases": {},
                "mappings": {},
                "settings": {
                    "index": {
                        "routing": {
                            "allocation": {
                                "include": {
                                    "_tier_preference": "data_content"
                                }
                            }
                        },
                        "number_of_shards": "1",
                        "provided_name": "books",
                        "creation_date": "1658631998622",
                        "number_of_replicas": "1",
                        "uuid": "yu-fK1cwSyWANAOzJEe-hw",
                        "version": {
                            "created": "7160299"
                        }
                    }
                }
            }
        }
        ```

     4. 添加文档【使用分词器】：

        注意：在此之前需要删除上一次创建的`books`索引，否则会报错。（同名索引只能有一个）

        ```java
        @Test
        void testESIndexByIk() throws IOException {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("books");
            String json = "{\n" +
                    "    \"mappings\":{\n" +
                    "        \"properties\":{\n" +
                    "            \"id\":{\n" +
                    "                \"type\":\"keyword\"\n" +
                    "            },\n" +
                    "            \"name\":{\n" +
                    "                \"type\":\"text\",\n" +
                    "                \"analyzer\":\"ik_max_word\",\n" +
                    "                \"copy_to\":\"all\"\n" +
                    "            },\n" +
                    "            \"type\":{\n" +
                    "              \t\"type\":\"keyword\"  \n" +
                    "            },\n" +
                    "            \"description\":{\n" +
                    "                \"type\":\"text\",\n" +
                    "                \"analyzer\":\"ik_max_word\",\n" +
                    "                \"copy_to\":\"all\"\n" +
                    "            },\n" +
                    "            \"all\":{\n" +
                    "                \"type\":\"text\",\n" +
                    "                \"analyzer\":\"ik_max_word\"\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
            createIndexRequest.source(json, XContentType.JSON);
            restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        }
        ```

        使用`PostMan`查看：【创建成功】

        ```json
        {
            "books": {
                "aliases": {},
                "mappings": {
                    "properties": {
                        "all": {
                            "type": "text",
                            "analyzer": "ik_max_word"
                        },
                        "description": {
                            "type": "text",
                            "copy_to": [
                                "all"
                            ],
                            "analyzer": "ik_max_word"
                        },
                        "id": {
                            "type": "keyword"
                        },
                        "name": {
                            "type": "text",
                            "copy_to": [
                                "all"
                            ],
                            "analyzer": "ik_max_word"
                        },
                        "type": {
                            "type": "keyword"
                        }
                    }
                },
                "settings": {
                    "index": {
                        "routing": {
                            "allocation": {
                                "include": {
                                    "_tier_preference": "data_content"
                                }
                            }
                        },
                        "number_of_shards": "1",
                        "provided_name": "books",
                        "creation_date": "1658635116262",
                        "number_of_replicas": "1",
                        "uuid": "aWGjN2lUSByTBDZVtM-nsQ",
                        "version": {
                            "created": "7160299"
                        }
                    }
                }
            }
        }
        ```

        创建文档：

        `PostMan`查询文档使用：`http://localhost:9200/_doc/{id}`

        查询全部内容：`http://localhost:9200/_search`

        将对象转换为`JSON`格式的数据需要使用到`fastjson`，这里导入依赖：

        ```xml
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.79</version>
        </dependency>
        ```

        创建文档：

        ```java
        @Test
        void myCreateDocument() throws Exception {
            HttpHost httpHost = HttpHost.create("http://localhost:9200");
            RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
            restHighLevelClient = new RestHighLevelClient(restClientBuilder);
            Book book = bookMapper.selectById(1);
            IndexRequest indexRequest = new IndexRequest("books").id(String.valueOf(book.getId()));
            String json = JSON.toJSONString(book);
            indexRequest.source(json, XContentType.JSON);
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            restHighLevelClient.close();
        }
        ```

        使用`PostMan`查询文档：`http://localhost:9200/_doc/1`

        ```json
        {
            "_index": "books",
            "_type": "_doc",
            "_id": "1",
            "_version": 1,
            "_seq_no": 0,
            "_primary_term": 1,
            "found": true,
            "_source": {
                "description": "Java学习经典,殿堂级著作！赢得了全球程序员的广泛赞誉。",
                "id": 1,
                "name": "Java编程思想（第4版）",
                "type": "计算机理论"
            }
        }
        ```

        查询到`id=1`的`book`说明查询成功！

        这只能添加单条文档，如何批量添加呢？总不能添加一个文档就创建一个`RestHighLevelClient`对象吧！`RestHighLevelClient`创建了批处理的对象，用于批量添加的`BulkRequest`。

        只需要将单个`IndexRequest`添加进`BulkRequest`中即可。

        ```java
        @Test
        void myBulkCreateDocument() throws IOException {
            HttpHost httpHost = HttpHost.create("http://localhost:9200");
            RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
            restHighLevelClient = new RestHighLevelClient(restClientBuilder);
            List<Book> bookList = bookMapper.selectList(null);
            BulkRequest bulkRequest = new BulkRequest();
            for(Book book : bookList) {
                IndexRequest indexRequest = new IndexRequest("books").id(String.valueOf(book.getId()));
                String json = JSON.toJSONString(book);
                indexRequest.source(json, XContentType.JSON);
                bulkRequest.add(indexRequest);
            }
            restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            restHighLevelClient.close();
        }
        ```

        `Java`程序查询`ES`中的文档：

        按`id`查询以及按条件查询：`http://localhost:9200/_doc/1`

        ```java
        @Test
        void mySelectById() throws IOException {
            GetRequest getRequest = new GetRequest("books", "10");
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            String json = getResponse.getSourceAsString();
            System.out.println(json);
        }
        ```

        ```java
        @Test
        void mySelectById() throws IOException {
            GetRequest getRequest = new GetRequest("books", "10");
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            String json = getResponse.getSourceAsString();
            System.out.println(json);
        }
        @Test
        void mySelect() throws IOException {
            SearchRequest searchRequest = new SearchRequest("books");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.termQuery("all", "java"));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            for (SearchHit searchHit : searchHits) {
                String json = searchHit.getSourceAsString();
                Book book = JSON.parseObject(json, Book.class);
                System.out.println(book);
            }
        }
        ```

### 缓存解决方案

长期大量的访问数据库，这样数据库是防不住的。总是会有崩掉的风险，因为总有一些数据是经常被访问的，所以就想：能不能找一块空间用来存储这些总是被访问的数据，这样就可以缓解数据库被大量长期访问的压力了。我们将这称之为**缓存**。

- 缓存就是：一种介于数据永久存储介质与数据应用之间的数据临时存储介质
- 使用缓存可以有效的减少低速数据读取过程的次数【例如磁盘`IO`】，提高系统性能（从内存读肯定比从磁盘读要快）
- 如有必要还可以做二级缓存多级缓存，缓存不仅仅只能缓解数据库的，还可以提供一种临时的存储空间，比如：手机验证码验证信息。

通过`HashMap`模拟数据库缓存【仅仅只是一个例子】：

```java
package com.kk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.mapper.BookMapper;
import com.kk.pojo.Book;
import com.kk.service.BookService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    private HashMap<Object, Book> hashMap = new HashMap();

    @Override
    public Book getById(Serializable id) {
        if (hashMap.get(id) == null) {
            Book book = super.getById(id);
            hashMap.put(id, book);
        }
        return hashMap.get(id);
    }
}
```

启动服务器然后访问接口`http://localhost:8080/books/1`：

可以发现第一次访问时访问了数据库，但是第二次再访问的时候不再访问数据库，而是直接从内存中保存在`HashMap`中取数据：这种从缓存中取数据的方式大大提高了效率，尤其是在大量访问量的时候尤其明显。

![](https://img-blog.csdnimg.cn/0fbadfd3c4e44fe9895c09ba5a8b41dd.png)

![](https://img-blog.csdnimg.cn/ba188cb59fd54cd195fcec1b289ee059.png)

通过`HashMap`模拟临时缓存【仅仅只是一个例子 ---> 验证码】：

```java
package com.kk.service;

public interface MsgService {
    //获取验证码
    public abstract String getCode(String telephone);
    //校验验证码
    public abstract Boolean checkCode(String telephone, String code);
}
```

```java
package com.kk.service.impl;

import com.kk.service.MsgService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MsgServiceImpl implements MsgService {

    private HashMap<String, String> hashMap = new HashMap();

    @Override
    public String getCode(String telephone) {
        String code = telephone.substring(0, 6);
        if (hashMap.get(telephone) == null) {
            hashMap.put(telephone, code);
        }
        return hashMap.get(telephone);
    }

    @Override
    public Boolean checkCode(String telephone, String code) {
        return code.equals(hashMap.get(telephone));
    }
}
```

使用`PostMan`访问接口获取验证码：`http://localhost:8080/books?telephone=13888888888`得到`138888`。

然后使用`Post`方式访问：`http://localhost:8080/books?telephone=13888888888&code=138888`获取到`true`。证明缓存生效。

#### `Simple`篇

- `SpringBoot`针对缓存提出的解决方案【也有其它很优秀的缓存方案，这里学习的是`SpringBoot`自带的缓存技术】：

  - **开启缓存**
  - **设置进入缓存的数据**
  - **设置读取缓存的数据**

  1. 导入依赖：

     ```xml
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-cache</artifactId>
     </dependency>
     ```

  2. 启动缓存：【在`引导类`启动】`@EnableCaching`

     ```java
     package com.kk;
     
     import org.springframework.boot.SpringApplication;
     import org.springframework.boot.autoconfigure.SpringBootApplication;
     import org.springframework.cache.annotation.EnableCaching;
     
     @SpringBootApplication
     @EnableCaching
     public class SpringBootDemo19CacheApplication {
         public static void main(String[] args) {
             SpringApplication.run(SpringBootDemo19CacheApplication.class, args);
         }
     }
     ```

  3. 使用缓存：`@Cacheable`

     我们将`BookServiceImpl`中之前自定义的缓存都给注释掉，然后在要加入缓存的地方使用注解`@Cacheable(value = "cacheSpace", key = "#id")`

     这个`value = "cacheSpace"`是自定义的，你可以使用任何你想使用的。这个值的意思是将数据缓存到名为`cacheSpace`的空间中。

     ```java
     @Override
     @Cacheable(value = "cacheSpace", key = "#id")
     public Book getById(Serializable id) {
         return super.getById(id);
     }
     ```

- `SpringBoot`使用的这套缓存技术是`Simple`，当然如果想换一种缓存技术实现缓存也是可以的。并且`SpringBoot`对其它缓存技术进行了整合，统一了接口【对原始代码没有影响】，方便缓存技术的开发与管理。

  常用的缓存技术方案：`Ehcache Redis Memecached`

- 缓存使用案例：手机验证码

  1. 制作`SimCard`手机验证码类

     ```java
     package com.kk.pojo;
     
     import lombok.AllArgsConstructor;
     import lombok.Data;
     import lombok.NoArgsConstructor;
     
     @Data
     @NoArgsConstructor
     @AllArgsConstructor
     public class SimCard {
         private String telephone;
         private String code;
     }
     ```

  2. 制作`SimCardService`服务层类

     ```java
     package com.kk.service;
     
     import com.kk.pojo.SimCard;
     
     public interface SimCardService {
         public abstract String sendCode(String telephone);
         public abstract Boolean checkCode(SimCard simCard);
     }
     ```

     实现类【暂定】：

     ```java
     package com.kk.service.impl;
     
     import com.kk.pojo.SimCard;
     import com.kk.service.SimCardService;
     
     public class SimCardServiceImpl implements SimCardService {
         @Override
         public String sendCode(String telephone) {
             return null;
         }
     
         @Override
         public Boolean checkCode(SimCard simCard) {
             return null;
         }
     }
     ```

  3. 制作`SimCardController`表现层类

     ```java
     package com.kk.controller;
     
     import com.kk.pojo.SimCard;
     import com.kk.service.SimCardService;
     import com.kk.util.CodeUtil;
     import com.sun.org.apache.bcel.internal.classfile.Code;
     import org.springframework.beans.factory.annotation.Autowired;
     import org.springframework.web.bind.annotation.GetMapping;
     import org.springframework.web.bind.annotation.PostMapping;
     import org.springframework.web.bind.annotation.RequestMapping;
     import org.springframework.web.bind.annotation.RestController;
     
     @RestController
     @RequestMapping(value = "/sms")
     public class SimCardController {
     
         @Autowired
         private SimCardService simCardService;
     
     
         @GetMapping
         public String getCode(String telephone) {
             return simCardService.sendCode(telephone);
         }
     
         @PostMapping
         public Boolean checkCode(SimCard simCard) {
             return simCardService.checkCode(simCard);
         }
     }
     ```

  4. 制作验证码：

     ```java
     package com.kk.util;
     
     public class CodeUtil {
         public static String getCode(String telephone) {
             long password = 286574;
             long currentTime = System.currentTimeMillis();
             long hash = telephone.hashCode();
             long code = Math.abs(((hash ^ password) ^ currentTime) % 1000000);
             String[] values = {"000000", "00000", "0000", "000", "00", "0", ""};
             String stringCode = code + "";
             stringCode = values[stringCode.length()] + stringCode;
             return stringCode;
         }
     }
     ```

  5. 完成`SimCardSercviceImpl`服务层功能：【`@CachePut(value = "cacheSpace", key = "#telephone")`将数据放入缓存空间中】

     ```java
     package com.kk.service.impl;
     
     import com.kk.pojo.SimCard;
     import com.kk.service.SimCardService;
     import com.kk.util.CodeUtil;
     import org.springframework.cache.annotation.CachePut;
     import org.springframework.stereotype.Service;
     
     @Service
     public class SimCardServiceImpl implements SimCardService {
         @Override
         @CachePut(value = "cacheSpace", key = "#telephone")
         public String sendCode(String telephone) {
             return CodeUtil.getCode(telephone);
         }
     
         @Override
         public Boolean checkCode(SimCard simCard) {
             return null;
         }
     }
     ```

     验证验证码，从缓存中取数据：

     ```java
     package com.kk.service.impl;
     
     import com.kk.pojo.SimCard;
     import com.kk.service.SimCardService;
     import com.kk.util.CodeUtil;
     import org.springframework.cache.annotation.CachePut;
     import org.springframework.cache.annotation.Cacheable;
     import org.springframework.stereotype.Service;
     
     @Service
     public class SimCardServiceImpl implements SimCardService {
         @Override
         @CachePut(value = "cacheSpace", key = "#telephone")
         public String sendCode(String telephone) {
             return CodeUtil.getCode(telephone);
         }
     
         @Override
         @Cacheable(value = "cacheSpace", key = "#telephone")
         public Boolean checkCode(SimCard simCard) {
             return null;
         }
     }
     ```

     现在要想一个问题：我们的数据是从哪里来的？

     数据是存储在对象中的对不对，而这个对象都是`Spring`容器帮我们管理的。而容器中的对象在初始化容器的时候就已经产生了，我们能做的就是修改里面的数据，缓存也是如此，缓存中的数据是放在容器中的对象里头的。所以我们需要修改一下工具类，产生验证码我们采用对象的模式，获取验证码我们也采用验证码的模式，用容器来管理。

     改造工具类`CodeUtil`：添加`@Component`注解，去除掉方法中的`static`静态方法，改为实例方法，这样容器才能管理

     ```java
     package com.kk.util;
     
     import org.springframework.cache.annotation.Cacheable;
     import org.springframework.stereotype.Component;
     
     @Component
     public class CodeUtil {
         public String generateCode(String telephone) {
             long password = 286574;
             long currentTime = System.currentTimeMillis();
             long hash = telephone.hashCode();
             long code = Math.abs(((hash ^ password) ^ currentTime) % 1000000);
             String[] values = {"000000", "00000", "0000", "000", "00", "0", ""};
             String stringCode = code + "";
             stringCode = values[stringCode.length()] + stringCode;
             return stringCode;
         }
     
         @Cacheable(value = "cacheSpace", key = "#telephone")
         public String getCode(String telephone) {
             return null;
         }
     }
     ```

     `Service`中的代码相应地也要做出变化了，现在我们明白了，我们在`Service`中的代码`@CachePut(value = "cacheSpace", key = "#telephone")`是往`SPring`容器里头存放缓存，是容器来管理这个缓存，从`CodeUtil`这个对象中取，所以要在`CodeUtil`中获取缓存中的内容：这就是为什么`@Cacheable(value = "cacheSpace", key = "#telephone")`注解要放在`CodeUtil`类里头了。

     下面是`Service`的代码：

     ```java
     package com.kk.service.impl;
     
     import com.kk.pojo.SimCard;
     import com.kk.service.SimCardService;
     import com.kk.util.CodeUtil;
     import org.springframework.beans.factory.annotation.Autowired;
     import org.springframework.cache.annotation.CachePut;
     import org.springframework.stereotype.Service;
     
     @Service
     public class SimCardServiceImpl implements SimCardService {
     
         @Autowired
         private CodeUtil codeUtil;
     
         @Override
         @CachePut(value = "cacheSpace", key = "#telephone")
         public String sendCode(String telephone) {
             return codeUtil.generateCode(telephone);
         }
     
         @Override
         public Boolean checkCode(SimCard simCard) {
             String code = codeUtil.getCode(simCard.getTelephone());
             return code.equals(simCard.getCode());
         }
     }
     ```

     此时当我们再通过`PostMan`测试，可以发现测试通过。

#### `EhCache`篇

1. 导入依赖：【版本可不用写】

   ```xml
   <dependency>
       <groupId>net.sf.ehcache</groupId>
       <artifactId>ehcache</artifactId>
       <version>2.10.9.2</version>
   </dependency>
   ```

2. 【修改配置】启用`ehcache`缓存：

   ```yaml
   spring:
     cache:
       type: ehcache
   # spring.cache.type 是可修改的
   ```

3. 对于`SpringBoot`来说`ehcache`是体系外的技术，它有它自己的一套配置，所以需要导入`ehcache`配置文件才可以使用`ehcache`

   导入`ehcache`配置文件：

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
            updateCheck="false">
       <diskStore path="D:\ehcache" />
   
       <!--默认缓存策略 -->
       <!-- external：是否永久存在，设置为true则不会被清除，此时与timeout冲突，通常设置为false-->
       <!-- diskPersistent：是否启用磁盘持久化-->
       <!-- maxElementsInMemory：最大缓存数量-->
       <!-- overflowToDisk：超过最大缓存数量是否持久化到磁盘-->
       <!-- timeToIdleSeconds：最大不活动间隔，设置过长缓存容易溢出，设置过短无效果，可用于记录时效性数据，例如验证码-->
       <!-- timeToLiveSeconds：最大存活时间-->
       <!-- memoryStoreEvictionPolicy：缓存清除策略-->
       <defaultCache
           eternal="false"
           diskPersistent="false"
           maxElementsInMemory="1000"
           overflowToDisk="false"
           timeToIdleSeconds="60"
           timeToLiveSeconds="60"
           memoryStoreEvictionPolicy="LRU" />
   </ehcache>
   ```

4. 然后其余的代码都不用动，运行，报错，报错信息为：

   ```java
   java.lang.IllegalArgumentException: Cannot find cache named 'cacheSpace' for Builder[public java.lang.String com.kk.service.impl.SimCardServiceImpl.sendCode(java.lang.String)] caches=[cacheSpace] | key='#telephone' | keyGenerator='' | cacheManager='' | cacheResolver='' | condition='' | unless=''
   	at org.springframework.cache.interceptor.AbstractCacheResolver.resolveCaches(AbstractCacheResolver.java:92) ~[spring-context-5.3.22.jar:5.3.22]
   ```

   这是因为使用`ehcache`可以，但是它是不知道缓存空间名称是`cacheSpace`【这个是我们自定义的】，我们需要在`ehcache.xml`配置文件中告诉`ehcache`，缓存空间名称是`cacheSpace`

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
            updateCheck="false">
       <diskStore path="D:\ehcache" />
   
       <!--默认缓存策略 -->
       <!-- external：是否永久存在，设置为true则不会被清除，此时与timeout冲突，通常设置为false-->
       <!-- diskPersistent：是否启用磁盘持久化-->
       <!-- maxElementsInMemory：最大缓存数量-->
       <!-- overflowToDisk：超过最大缓存数量是否持久化到磁盘-->
       <!-- timeToIdleSeconds：最大不活动间隔，设置过长缓存容易溢出，设置过短无效果，可用于记录时效性数据，例如验证码-->
       <!-- timeToLiveSeconds：最大存活时间-->
       <!-- memoryStoreEvictionPolicy：缓存清除策略-->
       <defaultCache
               eternal="false"
               diskPersistent="false"
               maxElementsInMemory="1000"
               overflowToDisk="false"
               timeToIdleSeconds="60"
               timeToLiveSeconds="60"
               memoryStoreEvictionPolicy="LRU" />
       <Cache
               name="cacheSpace"
               eternal="false"
               diskPersistent="false"
               maxElementsInMemory="1000"
               overflowToDisk="false"
               timeToIdleSeconds="60"
               timeToLiveSeconds="60"
               memoryStoreEvictionPolicy="LRU" />
   </ehcache>
   ```

5. 此时我们再次运行服务，观察结果，测试通过

6. 在这里就可以感受到`SpringBoot`整合的好处了，我们只需要导入依赖，修改配置，添加并且修改相关的`ehcache.xml`配置文件就可以使用`ehcache`缓存技术了，代码都不需要变化！不得不再次感叹`SpringBoot`简直太强大了！

7. 注意配置文件中的：`timeToIdleSeconds`和`timeToLiveSeconds`设置的是缓存过期时间，单位是`second`秒，比如我们现在设置为`10`，然后再进行测试，`10s`后验证码【即缓存中的内容】将失效。

   ```xml
   <Cache
           name="cacheSpace"
           eternal="false"
           diskPersistent="false"
           maxElementsInMemory="1000"
           overflowToDisk="false"
           timeToIdleSeconds="10"
           timeToLiveSeconds="10"
           memoryStoreEvictionPolicy="LRU" />
   ```

   `10s`过后直接报错：

   ```json
   {
       "timestamp": "2022-07-24T15:55:45.567+00:00",
       "status": 500,
       "error": "Internal Server Error",
       "path": "/sms"
   }
   ```

8. `SpringBoot`整合`ehcache`缓存技术整合完毕

#### 数据淘汰策略【补充】

在`ehcache.xml`中可以看到`memoryStoreEvictionPolicy=“LRU”`的字样，这就是跟数据淘汰策略相关配置。

影响数据淘汰的相关配置：检测容易丢失数据

- `volatile-lru`：**挑选最近最少使用的数据淘汰**
- `volatile-lfu`：**挑选最近使用次数最少的数据淘汰**
- `volatile-ttl`：**挑选将要过期的数据淘汰**
- `volatile-random`：**任意选择数据淘汰**

关于`LRU`和`LFU`，请看下面这张图片：

![](https://img-blog.csdnimg.cn/a8f0e65252844082aca7ec2eba5a4b20.png)

这是一个`9s`的时间段，最底下的`9s 5s 7s 8s`代表的是在`9s 5s 7s 8s`这个时间点分别访问了`name age addr gender`，上面蓝色的数字代表的是在`9s`总的这一段时间内，`name`总共被访问了`2`次，`age`总共被访问了`4`次，而`addr`总共被访问了`2`次，`gender`总共被访问了`1`次。

- 所谓的`LRU`：就是淘汰这段时间内最远没有被访问的数据，比如这里的`name`我们在第`9s`的时候刚访问过，而`age`在第`5s`被访问过此后再没有被访问。这就是离得最远的没有被访问的数据，所以按照`LRU`数据淘汰策略，`age`将被淘汰。

- 所谓的`LFU`：就是淘汰这段时间访问次数最少的数据，比如这里的`gender`访问次数为`1`次，是所有数据在这段时间内访问次数最少的数据，所以按照`LFU`数据淘汰策略，`gender`将被淘汰。

#### `Redis`篇

1. 引入`redis`依赖

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-redis</artifactId>
   </dependency>
   ```

2. 修改配置

   ```yaml
   spring:
     cache:
       type: redis
     redis:
       host: 192.168.56.1
       port: 9527
   ```

3. 在`Linux`中启动`redis`服务器：`./redis-server ../redis.conf`

4. 使用`Postman`进行测试，测试顺利，验证通过，在`redis`客户端可以看到`cacheSpace::1367890000`这么一个`key`说明进行了缓存

5. 可以给`redis`中设置缓存过期时间，在`application.yaml`中配置即可（配置`10s`）

   ```yaml
   spring:
     cache:
       redis:
         time-to-live: 10s
   ```

6. `use-key-prefix`可以设置是否添加前缀，默认为`true`

   `key-prefix`可以自定义前缀【前提是：`use-key-prefix`为`true`】

   `cache-null-values`表示是否可以缓存`null`默认为`false`

   ```yaml
   spring:
     cache:
       type: redis
       redis:
         time-to-live: 10s
         use-key-prefix: true
         key-prefix: sms_
         cache-null-values: false
   ```

#### `Memcached`篇

`memcached`是一个在国内非常流行的缓存技术，请注意它并不是`SpringBoot`内置的缓存技术，既然不是内置的缓存技术，它必然跟前面学习的整合`Simple Ehcache Redis`有点区别。

**<font color="red">`Memcached`的默认端口为：`11211`</font>**

1. 下载`memcached`之前需要安装`libevent`

   这里注意一点`./configure --prefix=/usr/local/libevent`，不要跟源码放在一块，否则后面`make install`时会报错

2. 下载安装`memcached`

   运行：`memcached -p 11211 -m 64M -u root -d`，如果是在`windows`安装则需要在管理员权限的`cmd`中才可以安装成功

3. `memcached`客户端选择：

   - `Memcached Client for Java`：最早期的客户端，稳定可靠，用户群广，但是效率低
   - `SpyMemcached`：效率更高，并发处理较不好
   - `Xmemcached`：并发处理更好

   要用就用最好的：`Xmemcached`，由于`SpringBoot`没有整合`memcached`所以需要采用硬编码的方式实现客户端初始化管理。

4. 从`mvnrepository`导入坐标，可以现在`pom.xml`看有无`Xmemcached`，事实上是没有的

   ```xml
   <dependency>
       <groupId>com.googlecode.xmemcached</groupId>
       <artifactId>xmemcached</artifactId>
       <version>2.4.7</version>
   </dependency>
   ```

5. 硬编码`MemcachedClient`，将其交给容器管理

   ```java
   package com.kk.config;
   
   import net.rubyeye.xmemcached.MemcachedClient;
   import net.rubyeye.xmemcached.MemcachedClientBuilder;
   import net.rubyeye.xmemcached.XMemcachedClientBuilder;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   import java.io.IOException;
   
   @Configuration
   public class MyMemcachedClient {
       @Bean
       public MemcachedClient getMemcachedClient() throws IOException {
           MemcachedClientBuilder memcachedClientBuilder = new XMemcachedClientBuilder("localhost:11211");
           MemcachedClient memcachedClient = memcachedClientBuilder.build();
           return memcachedClient;
       }
   }
   ```

6. 使用`memcachedClient`添加缓存，使用缓存即可【将前面学习使用的`Simple Ehcache Redis`的代码注释掉】`set get`更加自主，灵活性更高。

   获取验证码：**这里的过期时间是以秒为单位的**

   ```java
   @Override
   public String sendCode(String telephone) throws InterruptedException, TimeoutException, MemcachedException {
       //获取验证码
       String code = codeUtil.generateCode(telephone);
       memcachedClient.set("cacheSpace::" + telephone, 10, code);
       return code;
   }
   ```

   验证验证码：

   ```java
   @Override
   public Boolean checkCode(SimCard simCard) throws InterruptedException, TimeoutException, MemcachedException {
       //从缓存中提取验证码
       String code = memcachedClient.get("cacheSpace::" + simCard.getTelephone()).toString();
       System.out.println(code);
       //跟前端用户传递的验证码作比较
       return simCard.getCode().equals(code);
   }
   ```

7. 到这里`SpringBoot`整合`memcached`就算是完成了，但是我们还可以再进行优化一下，比如在`MemcachedClient`程序中，我们的`new XMemcachedClientBuilder("localhost:11211");`是以硬编码的方式写的，而且我们做一些配置的时候都需要硬编码的方式去修改，通常更灵活的做法就是加配置，从配置文件中取数据，这个我们之前也做过，现在来优化一下代码：

   在`yaml`配置文件中自定义配置信息：

   ```yaml
   memcached:
     # memcached服务器地址
     servers: 192.168.56.1:11211
     # 设置连接池的数量
     poolSize: 10
     # 设置默认操作超时
     opTimeout: 3000
   ```

   创建`MemcachedYaml`配置相关对象：

   ```java
   package com.kk.pojo;
   
   import lombok.AllArgsConstructor;
   import lombok.Data;
   import lombok.NoArgsConstructor;
   import org.springframework.boot.context.properties.ConfigurationProperties;
   
   @Data
   @AllArgsConstructor
   @NoArgsConstructor
   @ConfigurationProperties(prefix = "memcached")
   public class MemcachedYaml {
       private String servers;
       private int poolSize;
       private long opTimeout;
   }
   ```

   启动类添加：`@EnableConfigurationProperties`或者在`MemcachedYaml`中添加`@Component`两者都行，看个人喜好选择。

   然后使用这个配置对象：

   ```java
   package com.kk.config;
   
   import com.kk.pojo.MemcachedYaml;
   import net.rubyeye.xmemcached.MemcachedClient;
   import net.rubyeye.xmemcached.MemcachedClientBuilder;
   import net.rubyeye.xmemcached.XMemcachedClientBuilder;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   import java.io.IOException;
   
   @Configuration
   public class MyMemcachedClient {
   
       @Autowired
       private MemcachedYaml memcachedYaml;
   
       @Bean
       public MemcachedClient getMemcachedClient() throws IOException {
           MemcachedClientBuilder memcachedClientBuilder = new XMemcachedClientBuilder(memcachedYaml.getServers());
           memcachedClientBuilder.setOpTimeout(memcachedYaml.getOpTimeout());
           memcachedClientBuilder.setConnectionPoolSize(memcachedYaml.getPoolSize());
           MemcachedClient memcachedClient = memcachedClientBuilder.build();
           return memcachedClient;
       }
   }
   ```

有些人总觉得这些缓存技术做的都不好，包括`memcached`，于是他们自己做了一个缓存技术：`jetcache`【阿里巴巴开发】，在`Spring cache`的基础之上全新打造并且指出`Spring cache`的各种问题，直指要害。

#### `Jetcache`篇

问题：

1. **缓存过期时间配置松散：**比如`ehcache`的缓存过期时间是在`ehcache.xml`中配置的，而`redis`的缓存过期时间在`application.yml`中配置，再比如说`memcache`则是在程序代码中配置的。这样的配置操作实在是过于松散了，哪哪都可以配置，非常乱。
2. **服务器地址被固定了：**服务器地址不是本地的就是远程的，无法一同实现即共享又能实现访问速度飞快的问题，能不能即可以用本地访问又可以远程访问
3. 等等...

`Jetcache`对`SpringCache`进行了封装，在原有功能基础上实现了多级缓存、缓存统计、自动刷新、异步调用、数据报表等功能。

`Jetcache`自身不是缓存，而是整合了其它的缓存技术，底层其实就是框架。目前`Jetcache`只整合了四种缓存技术：【本地跟远程可以四选二即本地和缓存同时存在，也可以单独使用本地或者远程缓存】

- 本地缓存
  - `LinkedHashMap`
  - `Caffeine`
- 远程缓存
  - `Redis`
  - `Tair`

1. 搭建基础环境：`SpringBoot_demo20_Jetcache`，引入依赖：`mysql mybatis-plus lombok druid`等。

2. 引入依赖：【这里的版本号是有讲究的，高版本的`SpringBoot`需要用到高版本的`jetcache`否则会出错】

   ```java
   <dependency>
       <groupId>com.alicp.jetcache</groupId>
       <artifactId>jetcache-starter-redis</artifactId>
       <version>2.6.7</version>
   </dependency>
   ```

3. 修改配置文件，告诉`JetCache`要使用的远程缓存为`Redis`

   这里的`default`其实就是配置块的名称，你可以设置其它的，比如这里再加一个`sms`名称

   ```java
   jetcache:
     remote:
       default:
         type: redis
         host: 192.168.56.1
         port: 9527
         poolConfig:
           maxTotal: 50
       sms:
         type: redis
         host: 192.168.56.1
         port: 9527
         poolConfig:
           maxTotal: 50
   ```

4. 开启`JetCache`缓存：往引导类中添加`@EnableCreateCacheAnnotation`

   ```java
   package com.kk;
   
   import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   
   @SpringBootApplication
   @EnableCreateCacheAnnotation
   public class SpringBootDemo20JetcacheApplication {
       public static void main(String[] args) {
           SpringApplication.run(SpringBootDemo20JetcacheApplication.class, args);
       }
   }
   ```

5. 使用`JetCache`缓存

   ```java
   package com.kk.service.impl;
   
   import com.alicp.jetcache.Cache;
   import com.alicp.jetcache.anno.CreateCache;
   import com.kk.pojo.SimCard;
   import com.kk.service.SimCardService;
   import com.kk.util.CodeUtil;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   
   import java.util.concurrent.TimeUnit;
   
   
   @Service
   public class SimCardServiceImpl implements SimCardService {
   
       @Autowired
       private CodeUtil codeUtil;
   
       @CreateCache(area = "sms", name = "jetCache", expire = 10, timeUnit = TimeUnit.SECONDS)
       private Cache<String, String> jetCache;
   
       @Override
       public String sendCode(String telephone) {
           String code = codeUtil.generateCode(telephone);
           jetCache.put(telephone, code);
           return code;
       }
   
       @Override
       public Boolean checkCode(SimCard simCard) {
           String code = jetCache.get("sms_jetCache" + simCard.getTelephone());
           return simCard.getCode().equals(code);
       }
   }
   ```

6. 添加本地缓存解决方案 ---> 修改配置

   ```yaml
   jetcache:
     local:
       default:
         type: linkedhashmap
         #必配：keyConvertor ---> 对象的比对量非常大，所以需要转换为 String ---> fastjson
         keyConvertor: fastjson
   ```

7. 引入`fastjson`依赖：

   ```xml
   <dependency>
       <groupId>com.alibaba</groupId>
       <artifactId>fastjson</artifactId>
       <version>1.2.79</version>
   </dependency>
   ```

8. 可以指定本地还是远程缓存方案，默认为：`CacheType.Remote`

   ```java
   @CreateCache(name = "jetCache", expire = 3600, timeUnit = TimeUnit.SECONDS, cacheType = CacheType.BOTH)
   //CacheType.Local
   //CacheType.Remote
   ```

9. 开启方法缓存：【可以添加个注解就可以直接使用了】

   在引导类中添加：`@EnableMethodCache(basePackages="com.kk")`

   ```java
   package com.kk;
   
   import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
   import com.alicp.jetcache.anno.config.EnableMethodCache;
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   
   @SpringBootApplication
   @EnableCreateCacheAnnotation
   @EnableMethodCache(basePackages = "com.kk")
   public class SpringBootDemo20JetcacheApplication {
       public static void main(String[] args) {
           SpringApplication.run(SpringBootDemo20JetcacheApplication.class, args);
       }
   }
   ```

10. 在`BookServiceImpl`中使用【这个地方需要使用到缓存】：

    - 在`get`方法中添加注解`@Cached(name="book", key="id", expired=3600, timeUnit=TimeUnit.seconds)`【默认使用远程缓存方案】

      ```java
      @GetMapping("{id}")
      @Cached(name = "book_", key = "#id", expire = 3600, timeUnit = TimeUnit.SECONDS, cacheType = CacheType.REMOTE)
      public Book get(@PathVariable Integer id) {
          return bookService.getById(id);
      }
      
      @PutMapping
      @CacheUpdate(name = "book_", key = "#book.id", value = "#book")
      public boolean update(@RequestBody Book book) {
          return bookService.update(book);
      }
      
      @DeleteMapping("{id}")
      @CacheInvalidate(name = "book_", key = "#id")
      public boolean delete(@PathVariable Integer id) {
          return bookService.delete(id);
      }
      ```

      - 报错：`NullPointerException`，因为`Redis`中没有配置`keyConvertor`

      - 解决上述，但是又报错：`NoSerializableException`无序列化异常，因为`Redis`中无法直接存储对象，所以需要序列化，取数据的时候反序列化即可

        - 在`pojo.Book`中实现`Serializable`接口，实现可序列化

          ```java
          package com.kk.pojo;
          
          import lombok.AllArgsConstructor;
          import lombok.Data;
          import lombok.NoArgsConstructor;
          
          import java.io.Serializable;
          
          @Data
          @NoArgsConstructor
          @AllArgsConstructor
          public class Book implements Serializable {
              private Integer id;
              private String name;
              private String type;
              private String description;
          }
          ```

        - 配置文件添加：`valueEncode:java + valueDecode:java`

          ```yaml
          jetcache:
            valueEncode: java
            valueDecode: java
            local:
              default:
                type: linkedhashmap
                keyConvertor: fastjson
            remote:
              default:
                type: redis
                host: 192.168.56.1
                port: 9527
                keyConvertor: fastjson
                poolConfig:
                  maxTotal: 50
              sms:
                type: redis
                host: 192.168.56.1
                port: 9527
                poolConfig:
                  maxTotal: 50

      - 要想使用本地缓存方案可用：`cacheType=CacheType.LOCAL`

    - 当数据库发生更新时相应的缓存也需要发生变化：

      `@CacheUpdate(name="book_", key="#book.id", value="#book")`

    - 当数据库发生删除时相应的缓存也需要无效化：

      `@CacheInvalidate(name="book_", key="#id")`

    - 除了上述操作，`JetCache`还提供了一项操作：`@CacheRefresh(refresh="")`可以设置刷新时间，时间一到从数据库中获取数据【慎用】

      **`refresh`中单位为：秒**

      ```java
      package com.kk.controller;
      
      import com.alicp.jetcache.anno.*;
      import com.kk.pojo.Book;
      import com.kk.service.BookService;
      import org.springframework.beans.factory.annotation.Autowired;
      import org.springframework.web.bind.annotation.*;
      
      import java.util.List;
      import java.util.concurrent.TimeUnit;
      
      @RestController
      @RequestMapping(value = "/books")
      public class BookController {
      
          @Autowired
          private BookService bookService;
      
          @GetMapping("{id}")
          @Cached(name = "book_", key = "#id", expire = 3600, timeUnit = TimeUnit.SECONDS, cacheType = CacheType.REMOTE)
          @CacheRefresh(refresh = 1)
          public Book get(@PathVariable Integer id) {
              return bookService.getById(id);
          }
      
          @PostMapping
          public boolean save(@RequestBody Book book) {
              return bookService.save(book);
          }
      
          @PutMapping
          @CacheUpdate(name = "book_", key = "#book.id", value = "#book")
          public boolean update(@RequestBody Book book) {
              return bookService.update(book);
          }
      
          @DeleteMapping("{id}")
          @CacheInvalidate(name = "book_", key = "#id")
          public boolean delete(@PathVariable Integer id) {
              return bookService.delete(id);
          }
      
          @GetMapping
          public List<Book> getAll() {
              return bookService.getAll();
          }
      }
      ```

11. 除此之外：`JetCache`还可以统计数据查看 ---> `application.yaml`中配置

    ```yaml
    jetcache:
      statIntervalMinutes: 1
    ```

    ![](https://img-blog.csdnimg.cn/d7ff9027bec94edeab2d3137cc37aee3.png)

#### `J2Cache`篇

`JetCache`经过学习我们知道，它的本地缓存解决方案只支持：`Caffeine LinkedHashMap`而远程只支持`Redis Tair`，品种实在是少得可怜，现在我就是想本地用`memcached`远程使用`redis`这种需求，可以解决吗？答案是可以的：那就是使用`J2Cache`。

`J2Cache`不是什么缓存技术，其本质其实是一个缓存整合框架，可以提供缓存的整合方案，使各种缓存搭配使用，自身不提供缓存功能。其整合技术是基于`ehcache + redis`进行整合。

1. 导入依赖【两个】：如若需要整合`ehcahce`则需要导入`ehcache`坐标

   ```xml
   <dependency>
       <groupId>net.oschina.j2cache</groupId>
       <artifactId>j2cache-core</artifactId>
       <version>2.8.5-release</version>
   </dependency>
   <dependency>
       <groupId>net.oschina.j2cache</groupId>
       <artifactId>j2cache-spring-boot2-starter</artifactId>
       <version>2.8.0-release</version>
   </dependency>
   <dependency>
       <groupId>net.sf.ehcache</groupId>
       <artifactId>ehcache</artifactId>
   </dependency>
   ```

2. 修改配置【`j2cache`有自己的配置文件，需要导入到`SpringBoot`】

   ```yaml
   server:
     port: 80
   j2cache:
     config-location: j2cache.properties
   ```

3. 配置文件在`j2cache-core`核心包内，可以通过`ctrl + 1`在`IDEA`中查找，将`j2cache.properties`复制过来。但是其配置文件过于繁琐，我们可以自定义简写一个：

   ```xml
   # 配置1级缓存
   j2cache.L1.provider_class = ehcache
   ehcache.configXml = ehcache.xml
   
   # 配置1级缓存数据到2级缓存的广播方式：可以使用redis提供的消息订阅模式，也可以使用jgroups多播实现
   j2cache.broadcast = net.oschina.j2cache.cache.support.redis.SpringRedisPubSubPolicy
   
   # 配置2级缓存
   j2cache.l2-cache-open = false
   j2cache.L2.provider_class = net.oschina.j2cache.cache.support.redis.SpringRedisProvider
   j2cache.L2.config_section = redis
   redis.hosts = 192.168.56.1:9527
   
   redis.mode = single
   
   redis.namespace = j2cache
   ```

4. 配置`ehcache.xml`

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
            updateCheck="false">
       <diskStore path="D:\ehcache" />
   
       <!--默认缓存策略 -->
       <!-- external：是否永久存在，设置为true则不会被清除，此时与timeout冲突，通常设置为false-->
       <!-- diskPersistent：是否启用磁盘持久化-->
       <!-- maxElementsInMemory：最大缓存数量-->
       <!-- overflowToDisk：超过最大缓存数量是否持久化到磁盘-->
       <!-- timeToIdleSeconds：最大不活动间隔，设置过长缓存容易溢出，设置过短无效果，可用于记录时效性数据，例如验证码-->
       <!-- timeToLiveSeconds：最大存活时间-->
       <!-- memoryStoreEvictionPolicy：缓存清除策略-->
       <defaultCache
               eternal="false"
               diskPersistent="false"
               maxElementsInMemory="1000"
               overflowToDisk="false"
               timeToIdleSeconds="60"
               timeToLiveSeconds="60"
               memoryStoreEvictionPolicy="LRU" />
       <Cache
               name="cacheSpace"
               eternal="false"
               diskPersistent="false"
               maxElementsInMemory="1000"
               overflowToDisk="false"
               timeToIdleSeconds="10"
               timeToLiveSeconds="10"
               memoryStoreEvictionPolicy="LRU" />
   </ehcache>
   ```

5. 使用`j2cache`整合方案

   ```java
   package com.kk.service.impl;
   
   import com.kk.pojo.SimCard;
   import com.kk.service.SimCardService;
   import com.kk.util.CodeUtil;
   import net.oschina.j2cache.CacheChannel;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   
   @Service
   public class SimCardServiceImpl implements SimCardService {
   
       @Autowired
       private CodeUtil codeUtil;
   
       @Autowired
       private CacheChannel cacheChannel;
   
       @Override
       public String sendCode(String telephone) {
           //获取验证码
           String code = codeUtil.generateCode(telephone);
           cacheChannel.set("sms", telephone, code);
           return code;
       }
   
       @Override
       public Boolean checkCode(SimCard simCard) {
           //从缓存中提取验证码
           String code = cacheChannel.get("sms", simCard.getTelephone()).asString();
           //跟前端用户传递的验证码作比较
           return simCard.getCode().equals(code);
       }
   }
   ```

### 任务解决方案

- 定时任务是企业级应用中的常见操作

  - 年度报表
  - 缓存统计报告
  - ... ... ...

- `Java`中有自带的定时任务的`API`：

  ```java
  public static void main(String[] args) {
      Timer timer = new Timer();
      TimerTask timerTask = new TimerTask() {
          @Override
          public void run() {
              System.out.println("Timer Task Running...");
          }
      };
      //从当前时间点开始每隔两秒运行一次
      timer.schedule(timerTask, 0, 2000);
  }
  ```

- 为了提高定时任务做的更高效一些，有些人就搞出了`Quartz`框架，这是当前市面上流行的定时任务框架`Quartz`，而且`Spring`将其整合之后发现不是很难做而且速度也不是特别快，所以自己又搞出了`Spring Quartz`。

#### `Quartz`篇

- 相关概念
  - 工作`job`：用于定义具体执行的工作
  - 工作明细`jobDetail`：用于描述定时工作相关信息
  - 触发器`Trigger`：用于描述触发工作的规则，通常使用`cron`表达式定义调度规则【时间点那些】
  - 调度器`Scheduler`：描述了工作明细与触发器的对应关系

1. 导入依赖：

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-quartz</artifactId>
       <version>2.7.2</version>
   </dependency>
   ```

2. 创建具体执行的工作：

   ```java
   package com.kk.quartz;
   
   import org.quartz.JobExecutionContext;
   import org.quartz.JobExecutionException;
   import org.springframework.scheduling.quartz.QuartzJobBean;
   
   public class MyQuartzJob extends QuartzJobBean {
       @Override
       protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
           System.out.println("Quartz Job Running...");
       }
   }
   ```

3. 添加工作明细`JobDetail`：

   ```java
   @Bean
   public JobDetail jobDetail() {
       //storeDurably 是否做持久化
       return JobBuilder.newJob(MyQuartzJob.class).storeDurably().build();
   }
   ```

4. 添加触发器`Trigger`：

   ```java
   @Bean
   public Trigger trigger() {
       CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/15/30/45 * * * * ?");
       return TriggerBuilder.newTrigger().forJob(jobDetail()).withSchedule(cronScheduleBuilder).build();
   }
   ```

5. 整个配置类的代码如下：

   ```java
   package com.kk.config;
   
   import com.kk.quartz.MyQuartzJob;
   import org.quartz.*;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   @Configuration
   public class MyQuartz {
       @Bean
       public JobDetail jobDetail() {
           //storeDurably 是否做持久化
           return JobBuilder.newJob(MyQuartzJob.class).storeDurably().build();
       }
   
       @Bean
       public Trigger trigger() {
           CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/15/30/45 * * * * ?");
           return TriggerBuilder.newTrigger().forJob(jobDetail()).withSchedule(cronScheduleBuilder).build();
       }
   }
   ```

6. 启动项目，可以在控制台观察到每隔`15s`就会打印出：`Quartz Job Running...`

#### `Task`篇

上述整合`Quartz`写的代码实在是太繁琐了，`Task`油然而生，每多一个就要多一个触发器多一个工作明细。`Spring`觉得这件事情非常简单：

1. 在引导类添加注解`@EnableScheduling`开启定时任务功能

   ```java
   package com.kk;
   
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.scheduling.annotation.EnableScheduling;
   
   @SpringBootApplication
   @EnableScheduling
   public class SpringBootDemo22TaskApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(SpringBootDemo22TaskApplication.class, args);
       }
   }
   ```

2. 然后创建一个`Bean`类，在具体执行的定时任务方法添加`@Schedule(0/1 * * * * ？)`即可

   ```java
   package com.kk.task;
   
   import org.springframework.scheduling.annotation.Scheduled;
   import org.springframework.stereotype.Component;
   
   @Component
   public class MyTask {
       @Scheduled(cron = "0/1 * * * * ?")
       public void printTask() {
           System.out.println("Task Running...");
       }
   }

【:>震惊...这么简单那还要`Quartz`干啥...】

3. 可以给`Task`做配置：

   ```yaml
   spring:
     task:
       scheduling:
         #添加线程前缀 ---> 便于线程太多容易分辨
         thread-name-prefix: ssm_
   ```

### 邮件解决方案

#### `JavaMail`篇

- `SMTP`：简单邮件传输协议，用于**发送**电子邮件的传输协议
- `POP3`：用于**接收**电子邮件的标准协议
- `IMAP`：互联网消息协议，是`POP3`的替代协议【会做邮件状态的双向同步】

1. 引入依赖

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-mail</artifactId>
       <version>2.7.2</version>
   </dependency>
   ```

2. 修改配置

   ```yaml
   spring: 
     mail: 
       #邮件供应商 ---> smtp 发送 ---> pop3 接收 up imap
       #需要在邮件供应商的系统中开启 smtp pop3
       host: smtp.163.com
       username: xxx
       password: xxx
   ```

3. 发送邮件 ---> 使用`JavaMailSender`

   简单邮件`SimpleMailMessage`内容需要包括：发件人、收件人、标题、正文 ---> 定义四个变量即可

   如果给`from`加上`(xxx)`字样则收件人收到的发件人信息会显示`xxx`而不会显示邮箱账号。

   ```java
   package com.kk.service.impl;
   
   import com.kk.service.SendMailService;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.mail.SimpleMailMessage;
   import org.springframework.mail.javamail.JavaMailSender;
   import org.springframework.stereotype.Service;
   
   @Service
   public class SendMailServiceImpl implements SendMailService {
   
       @Autowired
       private JavaMailSender javaMailSender;
   
       private String from = "xxx@xx.com";
       private String to = "xxx@xx.com";
       private String subject = "测试邮件标题";
       private String content = "测试邮件内容";
   
   
       @Override
       public void sendMail() {
           SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
           simpleMailMessage.setFrom(from + "(AAA)");
           simpleMailMessage.setTo(to);
           simpleMailMessage.setSubject(subject);
           simpleMailMessage.setText(content);
           javaMailSender.send(simpleMailMessage);
       }
   }
   ```

4. 如果想要发送高级一点的内容比如：链接、图片还有附件，则需要使用`MimeMessage`然后借助`MimeMessageHelper(mimeMessage)`发送。

   **如果想要解析`HTML`内容，需要将`setText`第二个属性设置为`true`，否则会直接当作文本来解析邮件内容。**

   **并且如果想发送附件的话，需要将`MimeMessageHelper`中的`multipart`属性设置为`true`，该属性默认为`false`，这样才能发送附件。**

   下面是测试发送高级邮件的代码：

   ```java
   @Override
   public void sendMail() {
       try {
           MimeMessage mimeMessage = javaMailSender.createMimeMessage();
           MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
           mimeMessageHelper.setFrom(from);
           mimeMessageHelper.setTo(to);
           mimeMessageHelper.setSubject("测试第二封高级邮件");
           //setText 默认是无法解析 HTML 的，如果想要解析需要设置解析为 true
           //发送图片 + 发送链接
           mimeMessageHelper.setText("高级邮件的正文内容" + "<img src='https://th.bing.com/th/id/R.901f8ebdab22d065baefeae6c2701cc0?rik=Z3Hew18zFaF%2bLQ&riu=http%3a%2f%2fwww.pp3.cn%2fuploads%2f20120418lw%2f13.jpg&ehk=Es5ZGH90h%2foCghvlIwdKfUiqpO05gLSgOEBU2i0Mwok%3d&risl=&pid=ImgRaw&r=0'/>" + "<a href = https://www.baidu.com>点开有惊喜</a>", true);
           //添加附件
           File file1 = new File("C:\\Users\\Lucky\\Desktop\\R-C.png");
           File file2 = new File("C:\\Users\\Lucky\\Desktop\\Desktop.rar");
           mimeMessageHelper.addAttachment("李德胜爷爷.png", file1);
           mimeMessageHelper.addAttachment("李嬴.zip", file2);
           javaMailSender.send(mimeMessage);
       } catch (MessagingException e) {
           e.printStackTrace();
       }
   }
   ```

`SpringBoot`邮件解决方案到此完成 ---> `JavaMailSender`

### 消息解决方案

【消息发送方 ---> 生产者，消息接收方 ---> 消费者】从烽火台开始说起，消息分为发送方跟接收方，通常我们将发送方称为消息生产者，接收方称为消息消费者。

【同步消息、异步消息】除此之外，消息可以分为同步消息和异步消息，同步消息是生产者发送消息之后必须等到消费者消费以后才可以做进一步地处理，而异步消息则是生产者发送消息之后不必等消费者消费消息即可干自己想要干的事情。

在编程世界中也有存在着消息这种东东：

在单体服务器的世界里，客户端向服务器发送请求，这是再正常不过的世界。但是如果此时有很多很多的请求同时向服务器发送请求，则服务端的压力会变得非常非常大，要是崩溃了可不行，于是就把这些请求通通转发走，服务端充当一个中转站。

为了接收信息，出现了**消息队列**这么一种东西，它负责将信息统统转换为特定的消息格式，并分配给各个不同的子业务系统。缓解此前服务器端压力太大的问题。

![](https://img-blog.csdnimg.cn/3d58b0cfb3ef422aacbe3450600ee21b.png)

- 企业级应用中广泛使用的三种异步消息传递技术
  - `JMS[Java Message Service]`：一个规范，等同于`JDBC`规范，提供了与消息服务相关的`API`接口
    - 消息模型：
      - `peer-2-peer`点对点模型，消息发送到一个消息队列，该队列保存消息，只能被一个消费者消息或超时没被消息
      - `publish-subscribe`**发布订阅模型**：生产者生产消息可以被多个消费者消费，生产者和消费者完全独立，不需要感知对方的存在
    - 实现了`JMS`规范的技术：`ActiveMQ Redis HornetMQ RabbitMQ RocketMQ【没有完全遵守 JMS 规范】`
  - `AMQP`：`JMS`只适用于`Java`，但是有些系统并不是纯然只有`Java`这门语言开发，于是有人就搞出了`AMQP`协议，规范了网络交换的数据格式，兼容`JMS`。我`python`写的生产者可以被`Java`写的消费者消费。听起来就很酷。
    - 具有跨平台性，服务器供应商，生产者，消费者可以使用不同语言来实现
    - 为了解决跨平台性，`AMQP`协议规定所有的消息种类统统都规定为字节数组`byte[]`
    - 实现了`AMQP`协议的技术：`RabbitMQ RocketMQ StromMQ`
  - `MQTT`：消息队列遥测传输技术，专门为小设备设计，是`IOT`生态系统中主要成分之一。
  
- 订单短信的消息案例创建：

  为什么专门挑选发送短信这个业务为例子呢？因为发送短信发完了，并不需要等到短信发送成功业务才继续往下执行，这就很明显是一个异步消息的业务。所以可以使用消息队列来帮助完成。所以才选定发送短信的业务为例子。【发送短信也是模拟的】

  创建`OrderService`业务层：

  ```java
  package com.kk.service;
  
  public interface OrderService {
      //这里传递的只有订单 id 一个值，只是一个模拟，实际上订单内容肯定不止 id 一个
      public abstract void order(String id);
  }
  ```

  ```java
  package com.kk.service.impl;
  
  import com.kk.service.MessageService;
  import com.kk.service.OrderService;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Service;
  
  @Service
  public class OrderServiceImpl implements OrderService {
  
      @Autowired
      private MessageService messageService;
  
      @Override
      public void order(String id) {
          System.out.println("订单处理开始：" + id);
          messageService.sendMessage(id);
          System.out.println("订单处理完毕：" + id);
      }
  }
  ```

  创建`MessageService`业务层：

  ```java
  package com.kk.service;
  
  public interface MessageService {
      //要发送短信的订单纳入队列
      public abstract void sendMessage(String id);
      //处理短信
      public abstract String doMessage();
  }
  
  ```

  ```java
  package com.kk.service.impl;
  
  import com.kk.service.MessageService;
  import org.springframework.stereotype.Service;
  
  import java.util.ArrayList;
  
  @Service
  public class MessageServiceImpl implements MessageService {
  
      private ArrayList<String> arrayList = new ArrayList<>();
  
      @Override
      public void sendMessage(String id) {
          arrayList.add(id);
          System.out.println("待发送短信的订单已纳入处理队列：" + id);
      }
  
      @Override
      public String doMessage() {
          return "发送短信完毕： " + arrayList.remove(0);
      }
  }
  ```

  `OrderController`表现层：

  ```java
  package com.kk.controller;
  
  import com.kk.service.OrderService;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.web.bind.annotation.PathVariable;
  import org.springframework.web.bind.annotation.PostMapping;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  @RequestMapping(value = "/order")
  public class OrderController {
  
      @Autowired
      private OrderService orderService;
  
      @PostMapping("/{id}")
      public void sendOrder(@PathVariable String id) {
          orderService.order(id);
      }
  }
  ```

  `MessageController`表现层：

  ```java
  package com.kk.controller;
  
  import com.kk.service.MessageService;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.web.bind.annotation.PostMapping;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  @RequestMapping(value = "/message")
  public class MessageController {
  
      @Autowired
      private MessageService messageService;
  
      @PostMapping
      public String doMessage() {
          return messageService.doMessage();
      }
  }
  ```

#### `ActiveMQ`篇

1. 下载、安装`ActiveMQ`，这里的网络实在太差劲了所以先在`Windows`版本做 ---> 进入`win64`即可，然后访问：`http://localhost:8161`即可，`8161`是后台管理端口，`61616`是服务端口。

2. 引入依赖

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-activemq</artifactId>
       <version>2.7.2</version>
   </dependency>
   ```

3. 修改配置

   ```java
   server:
     port: 80
   spring:
     activemq:
       broker-url: tcp://localhost:61616
   ```

4. `ActiveMQ`符合`JMS`规范所以直接使用`JmsMessagingTemplate`即可，存储时先转换为消息格式再发送到`ActiveMQ`，消费时需要先接收再转换为消息格式。

   ```java
   package com.kk.service.impl.activemq;
   
   import com.kk.service.MessageService;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.jms.core.JmsMessagingTemplate;
   import org.springframework.stereotype.Service;
   
   import java.util.ArrayList;
   
   @Service
   public class ActiveMqMessageServiceImpl implements MessageService {
       @Autowired
       private JmsMessagingTemplate jmsMessagingTemplate;
   
       @Override
       public void sendMessage(String id) {
           jmsMessagingTemplate.convertAndSend(id);
           System.out.println("待发送短信的订单已纳入处理队列：" + id);
       }
   
       @Override
       public String doMessage() {
           return "发送短信完毕： " + jmsMessagingTemplate.receiveAndConvert(String.class);
       }
   }
   ```

5. 报错：好家伙，这是因为你没有告诉`ActiveMQ`具体要存放到哪个位置，你要么在`application.yml`配置文件中配置，要么在代码中以硬编码的方式告诉`ActiveMQ`

   ```java
   java.lang.IllegalStateException: No 'defaultDestination' or 'defaultDestinationName'
   ```

   先来看以硬编码的方式告诉：无论存储都要告诉`ActiveMQ`

   ```java
   package com.kk.service.impl.activemq;
   
   import com.kk.service.MessageService;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.jms.core.JmsMessagingTemplate;
   import org.springframework.stereotype.Service;
   
   import java.util.ArrayList;
   
   @Service
   public class ActiveMqMessageServiceImpl implements MessageService {
       @Autowired
       private JmsMessagingTemplate jmsMessagingTemplate;
   
       @Override
       public void sendMessage(String id) {
           jmsMessagingTemplate.convertAndSend("destination_id", id);
           System.out.println("待发送短信的订单已纳入处理队列：" + id);
       }
   
       @Override
       public String doMessage() {
           return "发送短信完毕： " + jmsMessagingTemplate.receiveAndConvert("destination_id", String.class);
       }
   }
   ```

   重启服务器，测试通过。

   第二种方式就是在`application.yml`配置文件中配置：

   ```yaml
   server:
     port: 80
   spring:
     activemq:
       broker-url: tcp://localhost:61616
     jms:
       template:
         default-destination: destination_application_id
   ```

   取消硬编码的`destination`，重启服务器，测试通过。

6. 目前我们是手动的生产、消费，按理说我们应该生产多少个，消费者自动去监听消费。这个在`AactiveMQ`中也是可以实现的。

   为了方便修改，更加直观，将`destination`改为硬编码的方式。

   创建`ActiveMQMessageListener`：该类就是用于监听生产者生产的消息的

   ```java
   package com.kk.service.impl.activemq;
   
   import org.springframework.jms.annotation.JmsListener;
   import org.springframework.stereotype.Component;
   
   @Component
   public class ActiveMQMessageListener {
       @JmsListener(destination = "destination_id")
       public void listenAndReceiveMessage(String id) {
           System.out.println("自动监听并消费消息：" + id);
       }
   }
   ```

   此时再重启服务器，然后发送订单：`http://localhost/order/8`则会发现在控制台中自动消费了生产的消息。

7. 如果该消息在`destination_id`处理完毕之后想交给另外一个`destination`从而造成一个流水线的方式可以使用`@SendTo(destination = "")`注解，代码如下：

   ```java
   package com.kk.service.impl.activemq;
   
   import org.springframework.jms.annotation.JmsListener;
   import org.springframework.messaging.handler.annotation.SendTo;
   import org.springframework.stereotype.Component;
   
   @Component
   public class ActiveMQMessageListener {
       @JmsListener(destination = "destination_id")
       @SendTo(value = "destination_send_to")
       public String listenAndReceiveMessage(String id) {
           System.out.println("自动监听并消费消息：" + id);
           return "new" + id;
       }
   
       @JmsListener(destination = "destination_send_to")
       public void listenAndReceiveMessage2(String newId) {
           System.out.println(newId);
       }
   }
   ```

8. 当前模式还是点对点的模式，如果想更改为发布订阅模式，可以在`application.yml`中进行配置，将`pub-sub-domain`更改为`true`即可：

   ```yaml
   server:
     port: 80
   spring:
     activemq:
       #ActiveMQ服务器端口
       broker-url: tcp://localhost:61616
     jms:
       template:
         default-destination: destination_application_id
       pub-sub-domain: true
   ```

`SpringBoot`整合`ActiveMQ`完成。

#### `RabbitMQ`篇

1. `RabbitMQ`是`Erlang`语言编写的，所以首先需要安装`Erlang`环境，安装完毕需要重启计算机才能生效，然后配置环境变量：`ERLANG_HOME`以及`PATH`

2. 安装`RabbitMQ`，进入到`cmd`使用`rabbitmq.bat start`启动，如果需要管理界面，还需要启动`rabbitmq-plugins.bat enable xxx`启动插件，默认管理的用户名跟密码均为：`guest`【需在管理权权限下启动】

   `RabbitMQ`默认的服务端口为：`5672`，后台管理`web`端口为：`15672`

   ![](https://img-blog.csdnimg.cn/73921277eaaf4d43858b7030fba49360.png)

3. `RabbitMQ`有好几种消息模型

4. 引入依赖：

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-amqp</artifactId>
       <version>2.7.2</version>
   </dependency>
   ```

5. 修改配置：

   ```yaml
   spring:
     rabbitmq:
       host: localhost
       port: 5672
   ```

6. 创建包：`rabbitmq/direct【直连模式】 + rabbitmq/topic`

7. 创建队列、交换机，并且绑定队列到交换机上形成路由

   ```java
   package com.kk.service.impl.rabbitmq.direct;
   
   import org.springframework.amqp.core.Binding;
   import org.springframework.amqp.core.BindingBuilder;
   import org.springframework.amqp.core.DirectExchange;
   import org.springframework.amqp.core.Queue;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   @Configuration
   public class RabbitMQConfiguration {
       @Bean
       //交给容器管理交换机对象，交换机负责绑定管理队列，并将消息推送到队列中
       public DirectExchange getDirectExchange() {
           return new DirectExchange("directExchange");
       }
   
       @Bean
       //创建队列1
       public Queue getDirectQueue1() {
           return new Queue("directQueue1");
       }
   
       @Bean
       //创建队列2
       public Queue getDirectQueue2() {
           return new Queue("directQueue2");
       }
   
       @Bean
       //交换机绑定队列1 ---> 交换机+队列 ---> 绑定形成路由
       public Binding bindingDirectQueue1() {
           return BindingBuilder.bind(getDirectQueue1()).to(getDirectExchange()).with("direct1");
       }
   
       @Bean
       //交换机绑定队列2 ---> 交换机+队列 ---> 绑定形成路由
       public Binding bindingDirectQueue2() {
           return BindingBuilder.bind(getDirectQueue2()).to(getDirectExchange()).with("direct2");
       }
   }
   ```

8. 使用`AmqpTemplate`转换并发送消息

   ```java
   package com.kk.service.impl.rabbitmq.direct;
   
   import com.kk.service.MessageService;
   import org.springframework.amqp.core.AmqpTemplate;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.jms.core.JmsMessagingTemplate;
   import org.springframework.stereotype.Service;
   
   @Service
   public class RabbitMqMessageServiceImpl implements MessageService {
       @Autowired
       private AmqpTemplate amqpTemplate;
   
       @Override
       public void sendMessage(String id) {
           System.out.println("待发送短信的订单已纳入处理队列：" + id);
           //指定交换机、路由、要传递的值
           amqpTemplate.convertAndSend("directExchange", "direct1", id);
       }
   
       @Override
       public String doMessage() {
           return "发送短信完毕";
       }
   }
   ```

9. 监听器，消费者监听消息并自动消费

   ```java
   package com.kk.service.impl.rabbitmq.direct;
   
   import org.springframework.amqp.rabbit.annotation.RabbitListener;
   import org.springframework.jms.annotation.JmsListener;
   import org.springframework.messaging.handler.annotation.SendTo;
   import org.springframework.stereotype.Component;
   
   @Component
   public class RabbitMQMessageListener {
       @RabbitListener(queues = {"directQueue1"})
       //监听哪个消息队列需要给出
       public void rabbitMQReceive(String id) {
           System.out.println("消息队列 1 已完成短信发送业务，id = " + id);
       }
   }
   ```

10. 启动服务器测试接口验证代码是否正确。

11. 测试完毕，程序正常运行，现在添加第二个监听器监听同一个队列，观察结果：

    ```java
    package com.kk.service.impl.rabbitmq.direct;
    
    import org.springframework.amqp.rabbit.annotation.RabbitListener;
    import org.springframework.jms.annotation.JmsListener;
    import org.springframework.messaging.handler.annotation.SendTo;
    import org.springframework.stereotype.Component;
    
    @Component
    public class RabbitMQMessageListener1 {
        @RabbitListener(queues = {"directQueue1"})
        //监听哪个消息队列需要给出
        public void rabbitMQReceive1(String id) {
            System.out.println("消息队列 1 One Listener 已完成短信发送业务，id = " + id);
        }
    }
    ```

    ```java
    package com.kk.service.impl.rabbitmq.direct;
    
    import org.springframework.amqp.rabbit.annotation.RabbitListener;
    import org.springframework.stereotype.Component;
    
    @Component
    public class RabbitMQMessageListener2 {
        @RabbitListener(queues = {"directQueue1"})
        public void rabbitMQReceive2(String id) {
            System.out.println("消息队列 1 Two Listener 已完成短信发送业务，id = " + id);
        }
    }
    ```

    可以观察到，此时`directQueue1`队列因为有了两个监听器，所以会轮询地执行不同地操作。

- `SpringBoot`整合`RabbitMQ`之`Topic`主题模式

  1. 引入依赖

  2. 修改配置

  3. 创建队列、交换机、绑定队列和交换机

     ```java
     package com.kk.service.impl.rabbitmq.topic;
     
     import org.springframework.amqp.core.Binding;
     import org.springframework.amqp.core.BindingBuilder;
     import org.springframework.amqp.core.Queue;
     import org.springframework.amqp.core.TopicExchange;
     import org.springframework.context.annotation.Bean;
     import org.springframework.context.annotation.Configuration;
     
     @Configuration
     public class RabbitMQTopicConfiguration {
         @Bean
         public Queue getTopicQueue1() {
             return new Queue("topicQueue1");
         }
     
         @Bean
         public Queue getTopicQueue2() {
             return new Queue("topicQueue2");
         }
     
         @Bean
         public TopicExchange getTopicExchange() {
             return new TopicExchange("topicExchange");
         }
     
         @Bean
         public Binding topicQueueBindTopicExchange1() {
             return BindingBuilder.bind(getTopicQueue1()).to(getTopicExchange()).with("topic1");
         }
     
         @Bean
         public Binding topicQueueBindTopicExchange2() {
             return BindingBuilder.bind(getTopicQueue2()).to(getTopicExchange()).with("topic2");
         }
     }
     ```

  4. 创建监听器

     ```java
     package com.kk.service.impl.rabbitmq.topic;
     
     import org.springframework.amqp.rabbit.annotation.RabbitListener;
     import org.springframework.stereotype.Component;
     
     @Component
     public class RabbitMQTopicListener1 {
         @RabbitListener(queues = {"topicQueue1"})
         public void topicListener(String id) {
             System.out.println("【Topic 消息队列】 1 One Listener 已完成短信发送业务，id = " + id);
         }
     }
     ```

  5. 使用`AmqpTemplate`发送消息

     ```java
     package com.kk.service.impl.rabbitmq.topic;
     
     import com.kk.service.MessageService;
     import org.springframework.amqp.core.AmqpTemplate;
     import org.springframework.beans.factory.annotation.Autowired;
     import org.springframework.stereotype.Service;
     
     @Service
     public class RabbitMqMessageServiceImpl implements MessageService {
         @Autowired
         private AmqpTemplate amqpTemplate;
     
         @Override
         public void sendMessage(String id) {
             System.out.println("待发送短信的订单已纳入处理队列：" + id);
             //指定交换机、路由、要传递的值
             amqpTemplate.convertAndSend("topicExchange", "topic1", id);
         }
     
         @Override
         public String doMessage() {
             return "发送短信完毕";
         }
     }
     ```

  6. 代码跟直连模式下的代码没什么差别，那么主题模式的消息队列跟直连模式的消息队列有什么不同呢？事实上，主题模式的消息队列比直连模式的消息队列强大很多。

     我们在代码中有两个队列：一个是`topicQueue1`另外一个是`topicQueue2`，并且绑定到了同一个交换机上形成了两个`RoutingKey`，一个是`topic1`，一个是`topic2`。

     强大就强大在`RoutingKey`上，它可以通过`*`跟`#`去匹配字符：

     `*`：可以匹配任意一个单词，且必须要匹配上

     `#`：可以匹配任意字符

     如果两个`RoutingKey`在使用时都匹配上了，那么消息会发送给这个两个消息队列。但是在直连模式中，你在定义的配置类中标志的`RoutingKey`是什么，你在使用的时候就要一一对应上，否则无效。

     这就是主题模式的强大之处。

     代码如下：【记得在测试代码的时候要在管理页面中删除队列，否则可能会产生一些问题】

     这是配置类中的代码：

     ```java
      @Bean
      public Binding topicQueueBindTopicExchange1() {
          return BindingBuilder.bind(getTopicQueue1()).to(getTopicExchange()).with("topic.*.ids");
      }
      @Bean
      public Binding topicQueueBindTopicExchange2() {
          return BindingBuilder.bind(getTopicQueue2()).to(getTopicExchange()).with("topic.order.*");
      }
     ```

     这是`AmqpTemplate`使用的代码：注意观察这里的`convertAndSend`的第二个参数`topic_order_ids`，这个参数值既匹配上了第一个队列跟交换机的绑定，也匹配上了第二个队列跟交换机的绑定。所以消息会发送到这两个消息队列中，这是直连模式下所没有的功能。

     ```java
     package com.kk.service.impl.rabbitmq.topic;
     
     import com.kk.service.MessageService;
     import org.springframework.amqp.core.AmqpTemplate;
     import org.springframework.beans.factory.annotation.Autowired;
     import org.springframework.stereotype.Service;
     
     @Service
     public class RabbitMqMessageServiceImpl implements MessageService {
         @Autowired
         private AmqpTemplate amqpTemplate;
     
         @Override
         public void sendMessage(String id) {
             System.out.println("待发送短信的订单已纳入处理队列：" + id);
             //指定交换机、路由、要传递的值
             amqpTemplate.convertAndSend("topicExchange", "topic.order.ids", id);
         }
     
         @Override
         public String doMessage() {
             return "发送短信完毕";
         }
     }
     ```

  7. 然后修改下监听器，去监听两个消息队列

     ```java
     package com.kk.service.impl.rabbitmq.topic;
     
     import org.springframework.amqp.rabbit.annotation.RabbitListener;
     import org.springframework.stereotype.Component;
     
     @Component
     public class RabbitMQTopicListener1 {
         @RabbitListener(queues = {"topicQueue1"})
         public void topicListener1(String id) {
             System.out.println("【Topic 消息队列 --- 1】 1 One Listener 已完成短信发送业务，id = " + id);
         }
     
         @RabbitListener(queues = {"topicQueue2"})
         public void topicListener2(String id) {
             System.out.println("【Topic 消息队列 --- 2】 1 One Listener 已完成短信发送业务，id = " + id);
         }
     }
     ```

  8. 重启服务器观察效果

     ![](https://img-blog.csdnimg.cn/718a6f6e7d18449580dd26a1e0ce000a.png)

     可以观察到，因为在`AmqpTemplate`中使用的`Routingkey`参数为`topic.order.ids`，匹配上了配置类中的两个绑定：`topic.*.ids`以及`topic.order.*`，所以`RabbitMQ`交换机会往两个消息队列塞消息。

#### `RocketMQ`篇

1. 下载安装`RocketMQ`，配置环境变量：`ROCKETMQ_HOME PATH NAMESRV_ADDR[建议，不配的时候每次打开都需要手动写很麻烦]127.0.0.1:9876`

   默认端口为：9876 ---> 业务服务器`broker` ---> 生产者/消费者连接 ---> 连接`Name-Server`命名服务器

   所以如果不设置环境变量每次打开都需要设置：`SET NAMESRV_ADDR LOCALHOST:9876`

   启动测试，运行`mqnamesrv.cmd + mqbroker.cmd`，然后使用`tools.cmd`打开案例`jar`使用

2. 测试生产者：`tools.cmd org.apache.rocketmq.example.quickstart.Producer`

3. 测试消费者：`tools.cmd org.apache.rocketmq.example.quickstart.Consumer`

4. 引入依赖：

   之前学习过`RabbitMQ`的时候可以发现在`spring-boot-starter-amqp`中只有`RabbitMQ`，也就是说`SpringBoot`没有将`RocketMQ`整合进来，只能到`mvnrepository`中去找：

   ```xml
   <dependency>
       <groupId>org.apache.rocketmq</groupId>
       <artifactId>rocketmq-spring-boot-starter</artifactId>
       <version>2.2.2</version>
   </dependency>
   ```

5. 将之前的消息队列关闭包括：`@Configuration + @Service`

6. 修改配置：

   ```yaml
   rocketmq:
     name-server: localhost:9876
   ```

7. 创建程序

   `RokcetMQMessageServiceImpl`：【第一版】

   ```java
   package com.kk.service.impl.rocketmq;
   
   import com.kk.service.MessageService;
   import org.apache.rocketmq.spring.core.RocketMQTemplate;
   import org.springframework.beans.factory.annotation.Autowired;
   
   public class RocketMQMessageServiceImpl implements MessageService {
   
       @Autowired
       private RocketMQTemplate rocketMQTemplate;
   
   
       @Override
       public void sendMessage(String id) {
           System.out.println("待发送短信的订单已纳入处理队列（rocketmq）：" + id);
           rocketMQTemplate.convertAndSend("[RocketMQ] order_id" + id);
       }
   
       @Override
       public String doMessage() {
           return null;
       }
   }
   ```

8. 报错，因为在使用`RocketMQ`时需要先给生产者分组才可以使用 ---> 修改配置：

   ```yaml
   rocketmq:
     name-server: localhost:9876
     producer:
       group: group_rocketmq
   ```

9. 编写监听器，让消费者自动消费

   ```java
   package com.kk.service.impl.rocketmq;
   
   import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
   import org.springframework.stereotype.Component;
   
   @Component
   @RocketMQMessageListener(topic = "order_id", consumerGroup = "group_rocketmq")
   public class RocketMQListener implements org.apache.rocketmq.spring.core.RocketMQListener<String> {
       @Override
       public void onMessage(String id) {
           System.out.println("自动监听并消费消息：完成短信发送 ---> " + id);
       }
   }
   ```

10. 上述发送的是同步消息，可以发送异步消息

    ```java
    @Override
    public void sendMessage(String id) {
        System.out.println("待发送短信的订单已纳入处理队列（rocketmq）：" + id);
        //rocketMQTemplate.convertAndSend("order_id", id);
        rocketMQTemplate.asyncSend("order_id", id, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("消息发送成功！");
            }
            @Override
            public void onException(Throwable throwable) {
                System.out.println("消息发送失败！");
            }
        });
    }
    ```

#### `Kafka`篇

`Kafka`并不是专门做消息中间件的的，但是它拥有这个功能，有很多公司也在用这个功能。

启动`kafka`需要先启动`zookeeper`，`zookeeper`充当着命名服务器的作用。注册中心`Zookeeper`的端口为：`2181`而`Kafka`的端口为：`9092` 【`windows`系统`3.x`存在`bug`，建议使用`2.x`】

1. 启动注册中心

   ```powershell
   zookeeper-server-start.bat ../../config/zookeeper.properties
   ```

2. 启动`Kafka`

   ```powershell
   kafka-server-start.bat ../../config/server.properties
   ```

3. 创建`Topic`：

   ```powershell
   kafka-topics-bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic kk
   ```

4. 查看`Topic`：

   ```powershell
   kafka-topics.bat --zookeeper 127.0.0.1:2181 --list
   ```

5. 删除`Topic`：

   ```powershell
   kafka-topics.bat --delete --zookeeper 127.0.0.1:2181 --topic kk
   ```

6. 生产者功能测试：

   ```powershell
   kafka-console-producer.bat --bootstrap-server localhost:9092 --topic kk
   ```

7. 消费者功能测试：

   ```powershell
   kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic kk --from-beginning
   ```

8. 引入依赖 ---> 可以在`SpringBoot-Parent`中找到

   ```xml
   <dependency>
       <groupId>org.springframework.kafka</groupId>
       <artifactId>spring-kafka</artifactId>
       <version>${spring-kafka.version}</version>
   </dependency>
   ```

9. 修改配置

   ```yaml
   #告诉连接地址
   spring:
     kafka:
       bootstrap-servers: localhost:9092
   ```

10. 首先需要按照第`3`步创建一个`Topic`

    ```powershell
    kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic kk
    Created topic kk.
    ```

11. 创建`KafkaMessageServiceImpl`生产者

    ```java
    package com.kk.service.impl.kafka;
    
    import com.kk.service.MessageService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.kafka.core.KafkaTemplate;
    
    public class KafkaMessageServiceImpl implements MessageService {
    
        @Autowired
        private KafkaTemplate<String, String> kafkaTemplate;
    
        @Override
        public void sendMessage(String id) {
            System.out.println("订单" + id + "已纳入 Kafka 消息队列");
            //发送
            kafkaTemplate.send("kk", id);
        }
    
        @Override
        public String doMessage() {
            return null;
        }
    }
    ```

12. 创建自动监听消息的消费者

    ```java
    package com.kk.service.impl.kafka;
    
    import org.apache.kafka.clients.consumer.ConsumerRecord;
    import org.springframework.kafka.annotation.KafkaListener;
    import org.springframework.stereotype.Component;
    
    @Component
    public class KafkaMessageServiceListener {
        @KafkaListener(topics = {"kk"})
        public void onMessage(ConsumerRecord<String, String> consumerRecord) {
            System.out.println("Kafka 处理订单完：" + consumerRecord);
        }
    }
    ```

13. 报错，需要在配置文件中配置`group_id`

    ```yaml
    spring:
      kafka:
      consumer:
        bootstrap-servers: localhost:9092
        group-id: order
    ```

14. 如果没有添加进`topic`或者无法消费，可以重启`Zookeeper`和`Kafka`

注：最好是注释掉`RocketMQ`中的配置文件，避免造成`bug`：

```yaml
server:
  port: 80
spring:
  activemq:
    broker-url: tcp://localhost:61616
  jms:
    template:
      default-destination: destination_application_id
    pub-sub-domain: true
  rabbitmq:
    host: localhost
    prot: 5672
  kafka:
    consumer:
      bootstrap-servers: localhost:9092
      group-id: order
#rocketmq:
#  name-server: localhost:9876
#  producer:
#    group: group_rocketmq
```

## 监控

#### 监控的意义

【有助于快速定位服务，防止服务器崩溃】

- 监控服务状态是否宕机
- 监控服务运行指标（内存、虚拟机、线程、请求等）
- 监控日志
- 管理服务（服务下线）

#### 监控的实施

一般应用都不会是单体服务器这种架构的，都是多个服务器的，所以我们打造一个服务器用于专门做监控服务器，**主动去获取哪些可以被监控的服务器并且监控的内容为被监控服务器允许被监控服务器监控的**。

【主动获取掌握可控性，尊重主动上报追求自由性】

- **监控服务器主动获取信息**

  把所有的服务器对应的信息都放在同一个服务器中，该服务器专门提供日志信息。是主动去索取呢？还是被动的接收呢？当然是主动的好呀，更加可控方便，万一我想要最新的信息还要等它们吗？所以最好还是主动获取的好。

- **被监控服务器主动上报要求被监控，同时告诉哪些东西可以被监控**

  可以做一个配置，需要被监控的程序主动上报我需要被谁谁谁监控，这样做的目的是保护安全，要不然谁都可以来监控我那还得了。并且需要配置哪些东西开放给监控服务器监控。

#### 可视化监控平台

这种监控服务器自己打造是何等的麻烦，有人已经把这项工作做好了，我们直接拿来用即可 ---> `SpringBootAdmin`[非官方开发]

1. 添加依赖

   ```xml
   <dependency>
       <groupId>de.codecentric</groupId>
       <artifactId>spring-boot-admin-starter-server</artifactId>
       <version>2.7.2</version>
   </dependency>
   
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-web</artifactId>
   </dependency>
   ```

2. 修改服务端配置：端口

   ```java
   server:
     port: 8080
   ```

3. 引导类服务端添加：`@EnableAdminServer`【*****非常重要】

   ```java
   package com.kk;
   
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   
   @SpringBootApplication
   @EnableAdminServer
   public class SpringBootDemo25SpringBootAdminServerApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(SpringBootDemo25SpringBootAdminServerApplication.class, args);
       }
   
   }
   ```

4. 开启服务，输入`localhost:8080`即可

5. 启动某个被监控的应用 ---> 创建新的项目`client` ---> 勾选`ops ---> SpringBootAdmin Client`【版本同样需要跟`SpringBoot`版本保持一致】

   ```xml
   <dependency>
       <groupId>de.codecentric</groupId>
       <artifactId>spring-boot-admin-starter-client</artifactId>
       <version>2.7.2</version>
   </dependency>
   ```

6. 修改客户端配置：

   - `spring.boot.admin.client.url=http://localhost:8080`【告诉需要被哪个服务器监控】
   - 配置`server.port`
   - 给监控服务器看什么监控服务器才看得到：`management.endpoint.health.show-details=always【默认为 never】` ---> 开方健康指标
   - `management.endpoints.web.exposure.include="*"`【默认为`health`】 ---> 开放所有`endpoint`

   ```yaml
   spring:
     boot:
       admin:
         client:
           url: http://localhost:8080
   management:
     endpoint:
       health:
         show-details: always
     endpoints:
       web:
         exposure:
           # 默认为 health
           include: "*"
   server:
     port: 80
   ```

   启动服务器...

7. 还可以给之前做的`SpringBoot_demo07_SSMP`加上`SpringBootAdmin Client`客户端

   ```xml
   <dependency>
       <groupId>de.codecentric</groupId>
       <artifactId>spring-boot-admin-starter-client</artifactId>
       <version>2.7.2</version>
   </dependency>
   ```

   ```yaml
   spring:
     boot:
       admin:
         client:
           url: localhost:8080
   management:
     endpoint:
       health:
         show-details: always
     endpoints:
       web:
         exposure:
           # 默认为 health
           include: "*"
   ```

   启动服务器...

8. 重启`SpringBootAdmin Server`监控服务器

#### 监控原理

有个问题很好奇，这些数据是怎么传给`Web`端的，原来是因为`Actuator`的存在，通过访问`/actuator`就可以获取到信息，如果访问`/actuator/health`就获取`health`信息，如果访问`/actuator/info`就获取`info`信息。即：`/actuator/端点名称`【这个可以通过查看映射中的`/actuator`得到】

除此之外还可以通过`jconsole`【这个是`Java`自带的，在`cmd`中直接输入即可】查看所有的数据信息。

```yaml
server:
  port: 8083
spring:
  boot:
    admin:
      client:
        url: http://localhost:8080
management:
  endpoint:
    health:
      show-details: always
  endpoints:
    web:
      exposure:
        # 默认为 health
        include: "*"
```

这里的`endpoint`是最原始的设置，`endpoints`只是允许`web`端查看的数据，具体能不能看到还要先看`endpoint`，比如此时我将`info.enabled`设置为了`false`，但是`endpoints.web.exposure.include="*"`仍然是全部，那么此时报的是`12`个，原本是`13`个。

```yaml
management:
  endpoint:
    health:
      show-details: always
    info:
      enabled: false
  endpoints:
    web:
      exposure:
        # 默认为 health
        include: "*"
```

启用所有端点：

```yaml
management:
  endpoints:
    web:
      exposure:
        # 默认为 health
        include: "*"
  enabled-by-default: true
```

`include`单纯只是端点的暴露而`enabled-by-default`则是开放所有端口功能。

#### 监控信息`info`【自定义】

![](https://img-blog.csdnimg.cn/1dd75e3625664ffaa1b859719e14efcb.png)

按理说应该会显示信息，并且最起码你得显示这个应用是干什么的，作者是谁？

在`SpringBoot 2.6`以上需要手动开启设置信息：

```yaml
management:
  info:
    env:
      enabled: true
```

此时再设置`info`信息即可：

```yaml
info:
  project: @project.artifactId@ ---> 从 pom.xml 中获取
  author: name
```

![](https://img-blog.csdnimg.cn/9bb6db0007cd4a5692c8a2852d3d2e2f.png)

还可以通过编程的方式添加信息`info`：

```java
package com.kk.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class InfoConfig implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        String currentDate = simpleDateFormat.format(date);
        builder.withDetail("startTime", currentDate);
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("version", "0.0.1");
        infoMap.put("test", "test");
        builder.withDetails(infoMap);
    }
}
```

重启服务器验证显示信息 - 验证通过：

![](https://img-blog.csdnimg.cn/dbeaade0d6a3467eaf32d0d60a245f74.png)

#### 监控健康`health`【自定义】

`health`是无法像`info`那样在`application.yml`配置文件中自定义的。

`health`表示的应用的状态信息，要改可以，间接改，在应用中添加一些别的服务即可，比如`redis`。展示的内部组件的工作状态。

如果执意要自定义也不是不行，可以通过代码的方式自定义，继承`AbstractHealthIndicator`即可，并且需要设置一下状态，否则在页面中的状态为`unknown`：

```java
package com.kk.config;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class HealthConfig extends AbstractHealthIndicator {
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.withDetail("运行是否正常？", "运行正常");
        builder.status(Status.UP);
    }
}
```

![](https://img-blog.csdnimg.cn/bf8cb7875ff44bc58aea20f62a1787f4.png)

#### 监控性能`metrics`【自定义】

为了模拟，我们现在只要用户删除一本图书我们默认当作一次付费，然后我们在监控性能的指标上擦好看：使用`MeterRegistry meterRegistry`添加性能

```java
package com.kk.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.mapper.BookMapper;
import com.kk.pojo.Book;
import com.kk.service.IBookService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IBookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

    @Autowired
    private BookMapper bookMapper;

    private Counter counter;

    public IBookServiceImpl(MeterRegistry meterRegistry) {
        counter = meterRegistry.counter("用户付费操作次数：");
    }

    public IPage<Book> getPage(int current, int pageSize, Book book) {
        LambdaQueryWrapper<Book> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Strings.isNotEmpty(book.getType()), Book::getType, book.getType());
        lambdaQueryWrapper.like(Strings.isNotEmpty(book.getName()), Book::getName, book.getName());
        lambdaQueryWrapper.like(Strings.isNotEmpty(book.getDescription()), Book::getDescription, book.getDescription());
        IPage<Book> iPage = new Page<>(current, pageSize);
        bookMapper.selectPage(iPage, lambdaQueryWrapper);
        return iPage;
    }

    @Override
    public boolean remove(Wrapper<Book> queryWrapper) {
        counter.increment();
        return super.remove(queryWrapper);
    }
}
```

![](https://img-blog.csdnimg.cn/0060c6432b3b40018ee7c76d36df2626.png)

#### 自定义端点

```java
package com.kk.config;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "pay")
public class PayEndPoint {
    @ReadOperation
    public Object getPay() {
        return "唯物史观 辩证法 马克思 恩格斯 列宁 毛泽东！！！";
    }
}
```

![](https://img-blog.csdnimg.cn/bc6e156241a947ff9506e366aea6890a.png)

# `SpringBoot`原理篇

原理篇并不能帮助你写出很多代码，但是可以解决很多疑惑给你一个合理的解释，要不然很多东西都是无法想明白的。

## 自动配置

`SpringBoot`底层最主要的原理。

### 【前置课】`Spring bean`的加载方式

1. 创建`Maven`项目`SpringBoot_demo27_BeanInit`

2. 引入`Spring`的依赖：`spring-context`[包含`spring-core`等，比较大]，这个版本是有讲究的，打开之前任意一个项目，查看依赖，点击`spring-web`可以查看到`spring-core`为`5.3.21`。

   ```xml
   <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-context</artifactId>
       <version>5.3.21</version>
   </dependency>
   ```

3. 创建管控的`bean`：`bean package ---> Cat Mouse Dog`

   ```java
   package com.kk.bean;
   public class Cat {
   }
   
   package com.kk.bean;
   public class Dog {
   }
   
   package com.kk.bean;
   
   public class Mouse {
   }
   ```

4. 创建业务层`service`接口：`Bookservice`【`void check()`方法】

   ```java
   package com.kk.service;
   
   public interface BookService {
       public abstract void check();
   }
   ```

5. 创建业务层实现类：复制粘贴出一共`4`个的`BookServiceImpl`，方法内容为打印即可

   ```java
   package com.kk.service.impl;
   import com.kk.service.BookService;
   public class BookServiceImpl1 implements BookService {
       @Override
       public void check() {
           System.out.println("Book Service 1..");
       }
   }
   
   package com.kk.service.impl;
   import com.kk.service.BookService;
   public class BookServiceImpl2 implements BookService {
       @Override
       public void check() {
           System.out.println("Book Service 2....");
       }
   }
   
   package com.kk.service.impl;
   import com.kk.service.BookService;
   public class BookServiceImpl3 implements BookService {
       @Override
       public void check() {
           System.out.println("Book Service 3......");
       }
   }
   
   package com.kk.service.impl;
   import com.kk.service.BookService;
   public class BookServiceImpl4 implements BookService {
       @Override
       public void check() {
           System.out.println("Book Service 4........");
       }
   }
   
   ```

#### **<font color="red">第一种声明`Bean`的方式：`XML`方式</font>**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="cat" class="com.kk.bean.Cat"></bean>
    <bean class="com.kk.bean.Dog"></bean>
</beans>
```

创建一个应用程序运行这个`xml`文件：

1. 使用名称获取`bean`

   ```java
   package com.kk.app;
   
   import com.kk.bean.Cat;
   import org.springframework.context.ApplicationContext;
   import org.springframework.context.support.ClassPathXmlApplicationContext;
   
   public class App1 {
       public static void main(String[] args) {
           ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
           Object cat = applicationContext.getBean("cat");
           System.out.println(cat);
       }
   }
   ```

2. 使用类`class`获取`bean`：[如果不配`id`默认没有名称，无法使用名称获取`bean`，除此之外还可以使用名称+类的方式精准获取对象]
   ```java
   Object dog = applicationContext.getBean(Dog.class);
   System.out.println(dog);
   ```

3. 使用`applicationContext.getBeanDefinitionNames()`获取所有`bean`名称

   ```java
   <bean class="com.kk.bean.Dog"></bean>
   <bean class="com.kk.bean.Dog"></bean>
   <bean class="com.kk.bean.Dog"></bean>
   <bean class="com.kk.bean.Dog"></bean>
       
   public class App1 {
       public static void main(String[] args) {
           ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
           String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
           for(String beanDefinitionName : beanDefinitionNames) {
               System.out.println(beanDefinitionName);
           }
       }
   }
   
   cat
   com.kk.bean.Dog#0
   com.kk.bean.Dog#1
   com.kk.bean.Dog#2
   com.kk.bean.Dog#3
   ```

4. 使用`xml`声明第三方`bean`

   ```java
   <bean id="cat" class="com.kk.bean.Cat"></bean>
   <bean class="com.kk.bean.Dog"></bean>
   <bean class="com.kk.bean.Dog"></bean>
   <bean class="com.kk.bean.Dog"></bean>
   <bean class="com.kk.bean.Dog"></bean>
   <bean id="druid" class="com.alibaba.druid.pool.DruidDataSource"></bean>
   <bean class="com.alibaba.druid.pool.DruidDataSource"></bean>
   <bean class="com.alibaba.druid.pool.DruidDataSource"></bean>
   ```

   ```java
   String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
   for(String beanDefinitionName : beanDefinitionNames) {
       System.out.println(beanDefinitionName);
   }
   
   cat
   com.kk.bean.Dog#0
   com.kk.bean.Dog#1
   com.kk.bean.Dog#2
   com.kk.bean.Dog#3
   druid
   com.alibaba.druid.pool.DruidDataSource#0
   com.alibaba.druid.pool.DruidDataSource#1
   ```

使用`xml`方式定义`bean`的好处：只有一个配置文件，非常直观

使用`xml`方式定义`bean`的坏处：太过繁琐，每次创建一个`bean`都要在这里写

#### **<font color="red">第二种的声明`Bean`的方式：注解方式</font>**

```java
package com.kk.bean;

import org.springframework.stereotype.Component;

@Component(value = "Tom")
public class Cat {
}
```

```java
package com.kk.bean;

import org.springframework.stereotype.Component;

@Component(value = "Jerry")
public class Mouse {
}
```

`Spring`需要配置识别【`xmlns:context context`可以自己写】：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.kk.bean"/>
</beans>
```

运行应用程序：

```java
package com.kk.app;

import com.kk.bean.Dog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App2 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext2.xml");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) System.out.println(beanDefinitionName);
    }
}
```

所有初始化的`bean`名称如下：

```java
Tom
Jerry
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
```

**<font color="deepskyblue">第三方的`Bean`要想使用注解方式，直接去到源码中加入`@Component`还是其它显然是不现实的，所以要想使用注解方式加载第三方的`Bean`需要自定义配置类。</font>** ---> `@Configuration + @Bean`

**这里的`@Configuration`可以使用更原始的方式`@Component`，只不过`@Configuration`是专门用声明说这是一个配置类的，这跟`@Component`的关系就好比如：`@Repository @Service @Controller`跟`@Component`的关系。**

```java
package com.kk.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidDataSourceConfig {
    @Bean
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }
}
```

到这里就结束了吗？并不是，还记得当时在`applicationContext2.xml`中是如何配置的吗？

```xml
<context:component-scan base-package="com.kk.bean"/>
```

可见我们当时配的时候只扫描了`com.kk.bean`，而这个第三方的`bean`是在`com.kk.config`包下的，所以我们需要加入这个包，使其被扫描到：【多个包之间可以使用逗号、空格、分号】

```xml
<context:component-scan base-package="com.kk.bean;com.kk.config"/>
```

此时应用程序运行的结果为：可以看到有配置类`druidDataSourceConfig`以及配置类中定义的`bean`：`druiudDataSource`都已经被`Spring`装载到`ApplicationContext`容器中。

```java
Tom
Jerry
druidDataSourceConfig
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
druidDataSource
```

#### **<font color="red">第三种的声明`Bean`的方式：注解配置类方式</font>**

- **因为配置文件`applicationContext.xml`写起来非常麻烦，所以`Spring`官方觉得干脆这个配置文件也不要了。直接写个配置类，所有的配置信息都写在一个类中 ---> `SpringConfig3.java` ---> 使用`AnnotationConfigApplicationContext`加载配置类【该配置类也会被加载成`Bean`】**

  ```java
  package com.kk.config;
  
  import org.springframework.context.annotation.ComponentScan;
  
  @ComponentScan(basePackages = {"com.kk.bean", "com.kk.config"})
  public class SpringConfig3 {
  }
  ```

  使用`AnnotaionConfigApplicationContext`运行加载该配置类：

  ```java
  package com.kk.app;
  
  import com.kk.config.SpringConfig3;
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.annotation.AnnotationConfigApplicationContext;
  
  public class App3 {
      public static void main(String[] args) {
          ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig3.class);
          String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
          for (String beanDefinitionName : beanDefinitionNames) System.out.println(beanDefinitionName);
      }
  }
  ```

- **有时候，我们想在创建对象`bean`的时候就进行一些工作，此时就需要使用到`FactoryBean`，此时你在加载配置`bean`的时候配置出来的就不是实现了`FactoryBean`接口的类了，而是这个工厂类返回的对象。**

  例子：

  创建一个狗狗工厂：你可以在`getObject()`中做一些初始化工作，比如你现在要连接`Redis`数据库那么你一定先是去判断`Redis`数据库是否连接得通。这就需要一些初始化工作，然后才可以返回对象。

  ```java
  package com.kk.bean;
  
  import org.springframework.beans.factory.FactoryBean;
  
  public class DogFactoryBean implements FactoryBean<Dog> {
      @Override
      public Dog getObject() throws Exception {
          return new Dog();
      }
  
      @Override
      public Class<?> getObjectType() {
          return Dog.class;
      }
  
      @Override
      public boolean isSingleton() {
          return FactoryBean.super.isSingleton();
      }
  }
  ```

  配置`Bean`：

  ```java
  package com.kk.config;
  
  import com.kk.bean.DogFactoryBean;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.ComponentScan;
  
  @ComponentScan(basePackages = {"com.kk.bean", "com.kk.config"})
  public class SpringConfig3 {
      @Bean
      public DogFactoryBean dogFactoryBean() {
          return new DogFactoryBean();
      }
  }
  ```

  运行程序，可以观察到虽然这里配置的`@Bean`是`DogFactoryBean`但是返回的并不是`DogFactoryBean`，而是返回的`Dog`对象，就是因为`DogFactoryBean`实现了`FactoryBean`接口实现了`getObject()`方法而在这个方法中返回的就是`Dog`对象所以配置方法得到的也是`Dog`对象：【观察最后两行即可 ---> `Bean`对象的名称跟配置的方法名一样】

  ```java
  package com.kk.app;
  
  import com.kk.config.SpringConfig3;
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.annotation.AnnotationConfigApplicationContext;
  
  public class App3 {
      public static void main(String[] args) {
          ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig3.class);
          String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
          for (String beanDefinitionName : beanDefinitionNames) System.out.println(beanDefinitionName);
          Object dogFactoryBean1 = applicationContext.getBean("dogFactoryBean");
          Object dogFactoryBean2 = applicationContext.getBean("dogFactoryBean");
          Object dogFactoryBean3 = applicationContext.getBean("dogFactoryBean");
          System.out.println(dogFactoryBean1);
          System.out.println(dogFactoryBean2);
          System.out.println(dogFactoryBean3);
      }
  }
  
  org.springframework.context.annotation.internalConfigurationAnnotationProcessor
  org.springframework.context.annotation.internalAutowiredAnnotationProcessor
  org.springframework.context.annotation.internalCommonAnnotationProcessor
  org.springframework.context.event.internalEventListenerProcessor
  org.springframework.context.event.internalEventListenerFactory
  springConfig3
  Tom
  Jerry
  druidDataSourceConfig
  druidDataSource
  dogFactoryBean
  com.kk.bean.Dog@38c5cc4c
  com.kk.bean.Dog@37918c79
  com.kk.bean.Dog@78e94dcf
  ```

  这里为什么打印出来的`Dog`对象不是单例的呢？这是因为我在`DogFactoryBean.isSingleton()`中设置的返回值为：`false`，所以返回的不是单例对象而是多例对象【默认为`true`返回单例对象，我这里自己把它改了】。一般交给`Spring`管控的都是单例对象，否则也失去了`Spring`管控的意义了。

  ```java
  @Override
  public boolean isSingleton() {
      //return FactoryBean.super.isSingleton();
      return false;
  }
  ```

- **上面的情况都是一开始就采用注解配置类的方式，那如果现在有一个系统它以前就有`applicationContext.xml`配置文件的存在，难道我们还要一个个拆出来吗？遇到问题算谁的呢？这就涉及到系统迁移的问题，如何在旧有配置文件中引入注解配置类呢？也就是如何把旧有的配置文件信息加载到配置类中？**

  一开始啥都没有，`SpringConfig32.java`配置类长这样：

  ```java
  package com.kk.config;
  
  public class SpringConfig32 {
  }
  ```

  加载配置类启动应用程序可以看到只有`springConfig32`这一个`bean`：

  ```java
  package com.kk.app;
  
  import com.kk.config.SpringConfig32;
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.annotation.AnnotationConfigApplicationContext;
  
  public class App32 {
      public static void main(String[] args) {
          ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig32.class);
          String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
          for (String beanDefinitionName : beanDefinitionNames) System.out.println(beanDefinitionName);
      }
  }
  
  org.springframework.context.annotation.internalConfigurationAnnotationProcessor
  org.springframework.context.annotation.internalAutowiredAnnotationProcessor
  org.springframework.context.annotation.internalCommonAnnotationProcessor
  org.springframework.context.event.internalEventListenerProcessor
  org.springframework.context.event.internalEventListenerFactory
  springConfig32
  ```

  此时我想引入`applicationContext.xml`只需要使用`@ImportReSource()`注解引入即可：

  ```java
  package com.kk.config;
  
  import org.springframework.context.annotation.ImportResource;
  
  @ImportResource(value = {"applicationContext1.xml"})
  public class SpringConfig32 {
  }
  ```

  此时可以看到结果加载了在`applicationContext1.xml`配置的`bean`：

  ```java
  org.springframework.context.annotation.internalConfigurationAnnotationProcessor
  org.springframework.context.annotation.internalAutowiredAnnotationProcessor
  org.springframework.context.annotation.internalCommonAnnotationProcessor
  org.springframework.context.event.internalEventListenerProcessor
  org.springframework.context.event.internalEventListenerFactory
  springConfig32
  cat
  com.kk.bean.Dog#0
  com.kk.bean.Dog#1
  com.kk.bean.Dog#2
  com.kk.bean.Dog#3
  druid
  com.alibaba.druid.pool.DruidDataSource#0
  com.alibaba.druid.pool.DruidDataSource#1
  ```

- **`@Configuration(proxyBeanMethods = true/false`**

  如果配置类中不添加`@Configuration`的话，默认创建出来的`SpringConfig32`对象调用`cat()`方法，获取到的`cat`是不同的对象，但是使用`@Configuration(=true)`的话【这里的`proxyBeanMethods`默认为`true`，可不写】，获取到的都是同一个对象。

  这是因为`proxyBeanMethods`表达的意思是是否表示该类创建出来的对象是一个代理对象，通过同一个代理对象创建出来的对象都是同一个对象。这就是添加`@Configuration`的妙处所在，`@Configuration`表示的是同一个代理，`@Bean`则是说这是一个`Bean`，通过代理创建出来的`bean`都是同一个对象，堪称神配置。

  ```java
  package com.kk.config;
  
  import com.kk.bean.Cat;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.context.annotation.ImportResource;
  
  @Configuration(proxyBeanMethods = true)
  @ImportResource(value = {"applicationContext1.xml"})
  public class SpringConfig32 {
      @Bean
      public Cat cat() {
          return new Cat();
      }
  }
  ```

  ```java
  package com.kk.app;
  
  import com.kk.config.SpringConfig32;
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.annotation.AnnotationConfigApplicationContext;
  
  public class App32 {
      public static void main(String[] args) {
          ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig32.class);
          String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
          for (String beanDefinitionName : beanDefinitionNames) System.out.println(beanDefinitionName);
          SpringConfig32 springConfig32 = applicationContext.getBean("springConfig32", SpringConfig32.class);
          System.out.println(springConfig32.cat());
          System.out.println(springConfig32.cat());
          System.out.println(springConfig32.cat());
      }
  }
  ```

  没有`@Configuration`注解：

  ```java
  com.kk.bean.Cat@16267862
  com.kk.bean.Cat@453da22c
  com.kk.bean.Cat@71248c21
  ```

  有`@Configuration`注解：

  ```java
  com.kk.bean.Cat@45018215
  com.kk.bean.Cat@45018215
  com.kk.bean.Cat@45018215
  ```

  还记得当时`RabbitMQ`消息队列的配置类吗？这也说明了为什么需要使用`@Configuration`注解的原因。它跟单纯的`@Component`是有区别的。

  ```java
  package com.kk.service.impl.rabbitmq.direct;
  
  import org.springframework.amqp.core.Binding;
  import org.springframework.amqp.core.BindingBuilder;
  import org.springframework.amqp.core.DirectExchange;
  import org.springframework.amqp.core.Queue;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  
  //@Configuration
  public class RabbitMQConfiguration {
      @Bean
      //交给容器管理交换机对象，交换机负责绑定管理队列，并将消息推送到队列中
      public DirectExchange getDirectExchange() {
          return new DirectExchange("directExchange");
      }
  
      @Bean
      //创建队列1
      public Queue getDirectQueue1() {
          //durable：是否持久化，默认为 false
          //exclusive：是否设定当前连接为专用，默认为 false ，连接关闭后队列即被删除
          //autoDelete：是否自动删除，当生产者或消费者不再使用该队列时自动删除
          return new Queue("directQueue1");
      }
  
      @Bean
      //创建队列2
      public Queue getDirectQueue2() {
          return new Queue("directQueue2");
      }
  
      @Bean
      //交换机绑定队列1 ---> 交换机+队列 ---> 绑定形成路由
      public Binding bindingDirectQueue1() {
          return BindingBuilder.bind(getDirectQueue1()).to(getDirectExchange()).with("direct1");
      }
  
      @Bean
      //交换机绑定队列2 ---> 交换机+队列 ---> 绑定形成路由
      public Binding bindingDirectQueue2() {
          return BindingBuilder.bind(getDirectQueue2()).to(getDirectExchange()).with("direct2");
      }
  }
  ```

  当我们把这个`@Configuration`注解注释掉的时候，`getDirectQueue`和`getDirectExchange`都会报错，就是因为创建出来的消息队列和交换机对象不是同一个此时你每次发送消息给消息队列都会到不同的消息队列对象中去，这是不允许的，这样做也毫无意义，只有使用这个注解的时候创建的才是同一个对象。

#### **<font color="red">第四种声明`Bean`的方式：`@Import`方式</font>**

直接使用`@Import`注解导入要注入的`Bean`对应的字节码

```java
package com.kk.config;

import com.kk.bean.Cat;
import com.kk.bean.Dog;
import org.springframework.context.annotation.Import;

@Import({Dog.class, Cat.class})
public class SpringConfig4 {
}
```

```java
package com.kk.app;

import com.kk.bean.Dog;
import com.kk.config.SpringConfig4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App4 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig4.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) System.out.println(beanDefinitionName);
    }
}
```

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
springConfig4
com.kk.bean.Dog
com.kk.bean.Cat
```

注：如果某个类中加入了`@Component(value = "")`注解并且`value`有值则导入的`bean`不是全类名而是类名称。

这种方式可以有效降低源代码与 Spring 技术的耦合度，在 Spring 技术底层以及诸多框架的整合中大量使用。

#### **<font color="red">第五种声明`Bean`的方式：上下文容器加载完毕后自定配置</font>**

这种声明`bean`的方式并不常见也不常用，但是如果开发框架的话是很可能会用到的。

```java
package com.kk.app;

import com.kk.bean.Cat;
import com.kk.bean.Dog;
import com.kk.config.SpringConfig5;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App5 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig5.class);
        applicationContext.registerBean("Tom", Cat.class, 0);
        applicationContext.registerBean("Tom", Cat.class, 1);
        applicationContext.registerBean("Tom", Cat.class, 2);
        applicationContext.register(Dog.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) System.out.println(beanDefinitionName);
        System.out.println(applicationContext.getBean("Tom"));
    }
}
```

结果为：

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
springConfig5
Tom
dog
Cat{age=2}
```

可以看到虽然这里使用`applicationContext.registerBean("Tom", Cat.class, 0/1/2);`创建了`3`个`Tom Cat`，但是这里并没有报错，原因是这里有点类似于`Map`，新添加的会覆盖掉前面添加的内容，所以这里打印出来的`Cat`的`age = 2`。

其次，如果使用的是`applicationContext.register(Dog.class);`获取到的`Dog`对象名将直接采用的是类名小写形式。

#### **<font color="red">第六种声明`Bean`的方式：实现`ImportSelector`接口</font>**

**该方式在源码中大量使用非常重要**，就是因为该方式是一种选择器模式，可以做判定返回指定类型的对象，比如你如果在这个类中有这个注解，我给你返回的是`A`对象，但是你没有这个注解我就给你返回`B`对象。

除此之外有必要说一下的就是实现`ImportSelector`接口需要实现`selectImports(AnnotationMetadata metadata)`方法，这里的`AnnotationMetadata`参数指的是引入当前实现了`ImportSelector`接口的类的所在类。比如我有一个配置类叫做`SpringConfig6`，我在这里使用`@Import(value = {MyImportSelector.class})`那么这个`SpringConfig6`就是`AnnotationMetadata`注解元数据。

配置类代码：

```java
package com.kk.config;

import com.kk.util.MyImportSelector;
import org.springframework.context.annotation.Import;

@Import(value = {MyImportSelector.class})
public class SpringConfig6 {
}
```

导入选择器类代码：

```java
package com.kk.util;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.kk.bean.Dog"};
    }
}
```

应用程序代码：

```java
package com.kk.app;

import com.kk.config.SpringConfig6;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App6 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig6.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) System.out.println(beanDefinitionName);
    }
}
```

可以看到结果加载了一个`Dog`类对象：

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
springConfig6
com.kk.bean.Dog
```

让我们进一步来看看这个引入选择器`ImportSelector`的强大魅力，在选择器里头我们可以通过`get is has`等获取到一些信息包括状态。然后判定需要注入哪些`Bean`。

比如判断元数据是否拥有某个注解：

```java
System.out.println("是否拥有 @Configuration 注解："+importingClassMetadata.hasAnnotation("org.springframework.context.annotation.Configuration"));

是否拥有 @Configuration 注解：true
```

再比如可以查看元数据中有哪些参数：

```java
//查看 @ComponentScan(basePackages = {}) 注解中配置参数
Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes("org.springframework.context.annotation.ComponentScan");
System.out.println(annotationAttributes);
```

然后我们来做一下，如果有`@Configuration`注解我们注入`Dog`，如果没有注入`Mouse`：

```java
package com.kk.util;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //判断元注解是否存在 @Configuration 注解
        System.out.println("是否拥有 @Configuration 注解："+importingClassMetadata.hasAnnotation("org.springframework.context.annotation.Configuration"));
        //查看 @ComponentScan(basePackages = {}) 注解中配置参数
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes("org.springframework.context.annotation.ComponentScan");
        System.out.println(annotationAttributes);
        boolean flag = importingClassMetadata.hasAnnotation("org.springframework.context.annotation.Configuration");
        if(flag) {
            return new String[]{"com.kk.bean.Dog"};
        }else {
            return new String[]{"com.kk.bean.Mouse"};
        }
    }
}
```

#### **<font color="red">第七种声明`Bean`的方式：实现`ImportBeanDefinitionRegistrar`接口</font>**

这第七种方式比第六种的还要稍稍高端一些，实现`ImportBeanDefinitionRegistrar`接口默认没有让你重写任何方法原因是这个接口的两个方法都使用的是`default`修饰的`JDK1.8`新增。在接口中通过`default`关键字声明的方法可以实现类似一个普通类中的方法。可以减少代码量。

这里为什么取名叫做：`ImportBeanDefinitionRegistrar`即引入`Bean`初始化器登记员【很形象，你想注入某个`Bean`到我这里来登记】，这是因为`Spring`内部造`Bean`其实是通过`BeanDefinition`初始化来的，所以名字中会有`BeanDefinition`。

通过`Alt + Ins`可以查看当前可以重写的方法：

1. `registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator)`
2. `registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry)`

可以看到这两个方法就参数个数不同，前者多了一个`BeanNameGenerator `，通过名字我们就猜到了，为什么有时候注入的`Bean`的名字可以是全类名可以是单纯的小写类名还可以是自定义的，原来都是通过`BeanNameGenerator`管控的。

- `AnnotationMetadata importingClassMetadata`就是元数据，这个是老朋友了，在第六种方式就已经学习过了
- `BeanDefinitionRegistry registry`注册注入`Bean`

该种方式可以深入到注册`Bean`的某些过程了：

```java
package com.kk.util;

import com.kk.bean.Dog;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Dog.class).getBeanDefinition();
        registry.registerBeanDefinition("Little Whit Dog", beanDefinition);
    }
}
```

```java
package com.kk.config;

import com.kk.util.MyImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

@Import(value = {MyImportBeanDefinitionRegistrar.class})
public class SpringConfig7 {
}
```

```java
package com.kk.app;

import com.kk.config.SpringConfig7;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App7 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig7.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) System.out.println(beanDefinitionName);
    }
}
```

结果为：

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
springConfig7
Little Whit Dog
```

可以看到引入了名为`Little Whit Dog`的对象。

#### **<font color="red">第八种声明`Bean`的方式：实现`BeanDefinitionRegistryPostProcessor`接口</font>**

该方式是最终声明的【不去考虑有多个实现了`BeanDefinitionRegistryPostProcessor`的情况】，它可以避免多个类同时引入被覆盖的现象。

比如我在这个实现了`BeanDefinitionRegistryPostProcessor`的实现类中使用的是`BookServiceImpl1`：

```java
package com.kk.util;

import com.kk.service.impl.BookServiceImpl1;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(BookServiceImpl1.class).getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("bookService", beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
```

配置类代码如下：

```java
package com.kk.config;

import com.kk.service.impl.BookServiceImpl2;
import org.springframework.context.annotation.Import;

@Import(value = {BookServiceImpl2.class})
public class SpringConfig8 {
}
```

在`BookServiceImpl2.class`我们引入`@Service`并取名为`bookService`：

```java
package com.kk.config;

import com.kk.service.impl.BookServiceImpl2;
import org.springframework.context.annotation.Import;

@Import(value = {BookServiceImpl2.class})
public class SpringConfig8 {
}
```

`BookServiceImpl2.java`代码如下：

```java
package com.kk.service.impl;

import com.kk.service.BookService;
import org.springframework.stereotype.Service;

@Service(value = "bookService")
public class BookServiceImpl2 implements BookService {
    @Override
    public void check() {
        System.out.println("Book Service 2....");
    }
}
```

现暂不引入`MyBeanDefinitionRegistryPostProcessor`实现类。可以看到结果如下：
```java
Book Service 2....
```

现在我们使用`ImportBeanDefinitionRegistrar`再去注入一个同名`bookService`的`Bean`：

```java
package com.kk.util;

import com.kk.service.impl.BookServiceImpl3;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrarNext1 implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(BookServiceImpl3.class).getBeanDefinition();
        registry.registerBeanDefinition("bookService", beanDefinition);
    }
}
```

然后在配置类中使用注解`@Import`引入：

```java
package com.kk.config;

import com.kk.service.impl.BookServiceImpl2;
import com.kk.util.MyImportBeanDefinitionRegistrarNext;
import org.springframework.context.annotation.Import;

@Import(value = {BookServiceImpl2.class, MyImportBeanDefinitionRegistrarNext.class})
public class SpringConfig8 {
}
```

因为都叫`bookService`所以肯定会有覆盖的，我们来看看哪个优先级高：

```java
Book Service 3......
```

现在我们把`SpringConfig8.java`配置类中`@Import`注解的值调换一下，看是否跟顺序有关：

```java
package com.kk.config;

import com.kk.service.impl.BookServiceImpl2;
import com.kk.util.MyImportBeanDefinitionRegistrarNext;
import org.springframework.context.annotation.Import;

@Import(value = {BookServiceImpl2.class, MyImportBeanDefinitionRegistrarNext.class})
public class SpringConfig8 {
}
```

打印结果仍然是`Book Service 3`：

```java
Book Service 3......
```

看来`ImportBeanDefinitionRegistrar`的优先级是要比使用`@Service`的优先级导入级别要高的，那如果有两个`ImportBeanDefinitionRegistrar`呢？此时优先级是按照排列顺序来吗？

```java
package com.kk.util;

import com.kk.service.impl.BookServiceImpl3;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrarNext2 implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(BookServiceImpl4.class).getBeanDefinition();
        registry.registerBeanDefinition("bookService", beanDefinition);
    }
}
```

然后在配置类中引入：

```java
package com.kk.config;

import com.kk.service.impl.BookServiceImpl2;
import com.kk.util.MyImportBeanDefinitionRegistrarNext1;
import com.kk.util.MyImportBeanDefinitionRegistrarNext2;
import org.springframework.context.annotation.Import;

@Import(value = {MyImportBeanDefinitionRegistrarNext1.class,BookServiceImpl2.class, MyImportBeanDefinitionRegistrarNext2.class})
public class SpringConfig8 {
}
```

打印结果为：

```java
Book Service 4......
```

我们将两个登记员的位置调换一下：

```java
package com.kk.config;

import com.kk.service.impl.BookServiceImpl2;
import com.kk.util.MyImportBeanDefinitionRegistrarNext1;
import com.kk.util.MyImportBeanDefinitionRegistrarNext2;
import org.springframework.context.annotation.Import;

@Import(value = {MyImportBeanDefinitionRegistrarNext2.class,MyImportBeanDefinitionRegistrarNext1.class,BookServiceImpl2.class})
public class SpringConfig8 {
}
```

结果为：

```java
Book Service 3......
```

看来如果有多个`ImportBeanDefinitionRegistrar`的话，注入`Bean`的顺序将按照配置类中配置的顺序来。

现在我们将`BeanDefinitionRegistryPostProcessor`导入配置类，为了更直接地呈现效果我这里将其直接放在第一个位置，也即是按理说优先级是最低的位置：

```java
package com.kk.config;

import com.kk.service.impl.BookServiceImpl2;
import com.kk.util.MyBeanDefinitionRegistryPostProcessor;
import com.kk.util.MyImportBeanDefinitionRegistrarNext1;
import com.kk.util.MyImportBeanDefinitionRegistrarNext2;
import org.springframework.context.annotation.Import;

@Import(value = {MyBeanDefinitionRegistryPostProcessor.class,MyImportBeanDefinitionRegistrarNext2.class,MyImportBeanDefinitionRegistrarNext1.class,BookServiceImpl2.class})
public class SpringConfig8 {
}
```

得到结果：

```java
Book Service 1..
```

这时你就彻底明白了第八种加载方式的优先级是要高于第七种加载方式的优先级的。

但是现在如果我们使用第五种加载`Bean`的方式，我们可以发现它的优先级比第八种加载`Bean`的方式还要高：

```java
package com.kk.app;

import com.kk.config.SpringConfig8;
import com.kk.service.BookService;
import com.kk.service.impl.BookServiceImpl2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App8 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig8.class);
        applicationContext.register(BookServiceImpl2.class);
        BookService bookService = (BookService) applicationContext.getBean("bookService");
        bookService.check();
    }
}
```

得到结果：

```java
Book Service 2....
```

#### 声明`Bean`方式的总结

加载`Bean`一共有八大方式：

1. 在`applicationContext.xml`使用`<bean/>`配置
2. 在`applicationContext.xml`使用`<context:component-scan>`配置 + `@Component`及其衍生注解
3. 配置类 + 扫描 + 注解
   - `@Bean`定义`FactoryBean`接口
   - `@ImportResource`导入配置文件
   - `@Configuration`注解的`ProxyBeanMethods`属性
4. 配置类中使用`@Import`导入`Bean`类
5. `AnnotationConfigApplicationContext`调用`register`方法
6. 配置类中使用`@Import`导入实现了`@ImportSelector`接口的类
7. 配置类中使用`@Import`导入实现了`@ImportBeanDefinitionRegistrar`接口的类
8. 配置类中使用`@Import`导入实现了`@BeanDefinitionRegistryPostProcessor`接口的类

### 【前置课】`Spring bean`的加载控制

#### 编程方式控制加载

在`Bean`加载的过程中就对`Bean`加载进行控制，该控制可以在第`5、6、7、8`中加载`Bean`的方式完成。我们可以挑一种来测试下，这里我们使用第六种加载`Bean`的方式：导入实现了`ImportSelector`接口的类。

可以看到该实现类中`Class.forName()`中`Wolf`是不存在的，只有`Cat Mouse Dog`。所以不加载任何`Bean`

```java
package com.kk.util;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        try {
            Class clazz = Class.forName("com.kk.bean.Wolf");
            System.out.println("clazz = " + clazz);
            if(clazz != null) return new String[]{"com.kk.bean.Cat"};
        } catch (ClassNotFoundException e) {
            return new String[0];
        }
        return null;
    }
}
```

```java
package com.kk.config;

import com.kk.util.MyImportSelector;
import org.springframework.context.annotation.Import;

@Import(value = {MyImportSelector.class})
public class SpringConfig {
}
```

```java
package com.kk.app;

import com.kk.config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyApp {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) System.out.println(beanDefinitionName);
    }
}
```

打印结果为：

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
springConfig
```

若是将`Wolf`更改为：`Mouse`则将加载`Bean`：

```java
clazz = class com.kk.bean.Mouse[自定义输出]
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
springConfig
com.kk.bean.Cat
```

如此，我们就使用编程的方式完成了`Bean`的控制。

#### 注解方式控制加载

编程方式控制加载写的非常繁琐，于是就冒出来个简化的注解控制加载。

使用`@ConditionalOnxxx`注解为`Bean`加载设置条件。

在`Spring`中本来是想使用`@Conditional`，但是点开这个`@Conditional`注解你可以发现你需要实现`Condition`接口你才可以使用该注解：

```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.springframework.context.annotation;

import org.springframework.core.type.AnnotatedTypeMetadata;

@FunctionalInterface
public interface Condition {
    boolean matches(ConditionContext var1, AnnotatedTypeMetadata var2);
}
```

`Spring`是这样子的但是`SpringBoot`不是这样子的，我们在这个注解页面中点击`Ctrl + H`查看实现过该接口的注解，可以发现都是在`spring.boot.*`包中，所以我们这里直接使用`SpringBoot`提供的`@ConditionalOnxxx`注解即可。不过你首先需要导入`SpringBoot`相关依赖才可以使用，为了避免出现什么问题你可以选择将`spring`相关依赖注释掉。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <version>2.7.2</version>
</dependency>
```

这里新创建一个`SpringConfigNext`以及新的一个`MyAppNext`：

```java
package com.kk.config;

import com.kk.bean.Cat;
import org.springframework.context.annotation.Bean;

public class SpringConfigNext {
    @Bean
    public Cat cat() {
        return new Cat();
    }
}
```

```java
package com.kk.app;

import com.kk.config.SpringConfigNext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyAppNext {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfigNext.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) System.out.println(beanDefinitionName);
    }
}
```

启动应用程序可以看到：

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
springConfigNext
cat
```

我们给这个`Cat`类改个名字。

```java
package com.kk.config;

import com.kk.bean.Cat;
import org.springframework.context.annotation.Bean;

public class SpringConfigNext {
    @Bean
    public Cat Tom() {
        return new Cat();
    }
}
```

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
springConfigNext
Tom
```

添加`@ConditionalOnClass(value = {Mouse.class})`表示有这个`Class`我们再去加载`Tom`

```java
package com.kk.bean;

import org.springframework.stereotype.Component;

@Component(value = "Jerry")
public class Mouse {
}
```

```java
package com.kk.config;

import com.kk.bean.Cat;
import com.kk.bean.Mouse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

public class SpringConfigNext {
    @Bean
    @ConditionalOnClass(value = {Mouse.class})
    public Cat Tom() {
        return new Cat();
    }
}
```

但是我们一般不这么用，因为`Mouse.class`存不存在这事情编译器就已经帮你做了，我们需要使用的是字符串。这里的意思是有了`Mouse`类才加载`Cat`类。否则不加载该`Bean`。

```java
package com.kk.config;

import com.kk.bean.Cat;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

public class SpringConfigNext {
    @Bean
    @ConditionalOnClass(name = {"com.kk.bean.Mouse"})
    public Cat Tom() {
        return new Cat();
    }
}
```

现在我们再引入一个`@ConditionalOnMissingClass`注解，表明只有当这个类不存在的时候才加载`Bean`，下面代码表示虽然有`Mouse`但是这里有`Dog`，`Cat`照样不会加载。

```java
package com.kk.config;

import com.kk.bean.Cat;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;

public class SpringConfigNext {
    @Bean
    @ConditionalOnMissingClass(value = {"com.kk.bean.Dog"})
    @ConditionalOnClass(name = {"com.kk.bean.Mouse"})
    public Cat Tom() {
        return new Cat();
    }
}
```

再来看一个注解`@ConditionalOnBean(name= {"Jerry"})`表示只有加载了这个`Bean`才会去加载`Cat Tom`这个`Bean`：

```java
package com.kk.config;

import com.kk.bean.Cat;
import com.kk.bean.Mouse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(value = {Mouse.class})
public class SpringConfigNext {
    @Bean
    //@ConditionalOnMissingClass(value = {"com.kk.bean.Dog"})
    @ConditionalOnClass(name = {"com.kk.bean.Mouse"})
    @ConditionalOnBean(name = "Jerry")
    public Cat Tom() {
        return new Cat();
    }
}
```

此时我们将`Jerry`更改为`Jerry1`，此时将不加载`Cat`：

```java
package com.kk.bean;

import org.springframework.stereotype.Component;

@Component(value = "Jerry1")
public class Mouse {
}
```

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
springConfigNext
Jerry1
```

到这里就跟`SpringBoot`的底层原理越来越像了，`SpringBoot`底层就有好多好多这种`@ConditionalOnxxx`去做一个`Bean`加载控制的处理。

### `bean`依赖属性配置

### 自动配置原理

### 变更自动配置

## 自定义`starter`

用了很多`starter`觉得很好用，但不知道是怎么做出来的，这一节将告诉你`starter`是如何一步步打造出来并执行的。

## 核心原理

`SpringBoot`的核心原理。 
