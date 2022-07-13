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