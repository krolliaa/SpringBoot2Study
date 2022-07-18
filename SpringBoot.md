# `SpringBoot`

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
    2.  表示中途出现异常导致返回`null`

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
