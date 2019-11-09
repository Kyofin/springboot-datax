![Java](https://woolson.gitee.io/npmer-badge/Java-555555-1.8-44cc11-check-ffffff-square-gradient-shadow.svg)
![springboot](https://woolson.gitee.io/npmer-badge/springboot-555555-2.x-44cc11-check-ffffff-square-gradient-shadow.svg)
## preparation
- Language: Java 8
- Environment: MacOS, 16G RAM
- Database: Mysql5.7

## introduction
- 使用springboot启动datax。


## todo list

* [x] springboot重构项目
* [x] 通过restful接口调度datax完成抽取数据作业
* [x] 通过restful接口传入job配置json生成临时文件，根据文件配置调度datax执行该作业
* [x] 集成swagger，方便调试
* [x] 集成mybatis plus和Mysql数据库存放应用数据
* [x] 网页端修改并持久化job配置的json到数据库
* [x] 网页端实时查看抽取日志，类似Jenkins的日志控制台输出功能
* [ ] 网页端各种读写插件模板生成，可以在页面组装使用
* [ ] 精简assembly打包结构
* [ ] 实现datax分布式作业
* [ ] 实现部分写插件支持自动建表功能

## 部分截图
- 查看作业日志
![](https://raw.githubusercontent.com/huzekang/picbed/master/20190919144220.png)


- 配置数据源
![](https://raw.githubusercontent.com/huzekang/picbed/master/20190919144322.png)

- 根据数据源读取表字段
![](https://raw.githubusercontent.com/huzekang/picbed/master/20190919144410.png)

- 启动一个配置好的作业
![](https://raw.githubusercontent.com/huzekang/picbed/master/20190919144522.png)

## 前端项目
[github地址](https://github.com/zhouhongfa/datax-vue-admin.git)
## 运行项目
### 1. 下载[阿里datax](https://github.com/alibaba/DataX.git)打包之后的文件到本地，或者在github拉取datax代码打包

### 2. 配置`DATAX_HOME`环境变量指向阿里的datax目录
即图中显示的目录
![](https://raw.githubusercontent.com/huzekang/picbed/master/20190919145146.png)
```
 export DATAX_HOME=/openSource/AliBabaDataX/target/datax/datax
```

### 3. 执行datax-web/db下面的sql文件并修改`application.yml`数据库配置信息
数据库名 `datax_web` (与 配置文件中要一致)。

配置文件在 `datax-web` 模块下的 `\src\main\resources\application.yml`

### 4. 在`application.yml`配置数据抽取日志文件保存路径
                          
```
etlLogDir: /temp/datax-web/
```

### 5. 使用maven打包编译`datax-web`项目
在此工程的根目录下执行命令
```
mvn clean install -DskipTests

```

### 6. 编译项目成功后，进入`datax-web/target`目录启动工程
```java
 java  -DDATAX_HOME=/Users/huzekang/openSource/AliBabaDataX/target/datax/datax -jar datax-web-0.0.1-SNAPSHOT.jar
```
如果没在环境变量里配置`DATAX_HOME`,在启动命令中像上面这样带上也是可以的
### 7. 访问浏览器`http://localhost:8066`即可看到正常使用了
![](https://raw.githubusercontent.com/huzekang/picbed/master/20190708102445.png)
