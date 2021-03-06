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
