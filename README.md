# 项目说明

### 自定义组件
| 组件名称         | 作用                          |
| ---------------- | ----------------------------- |
| platform-banner  | banner控制台输出              |
| platform-core    | 基础核心包                    |
| platform-mybatis | 数据处理相关                  |
| platform-swagger | 基于SpringDoc的自定义文档组件 |
| platform-web     | 服务相关                      |


### 操作说明

> idea 不会读取环境变量，所以需要在 idea 中配置环境变量

 步骤1: 打开 idea，点击菜单栏的 Run -> Edit Configurations...

 步骤2: 在弹出的窗口中，找到你的应用程序配置（通常是Spring Boot Application），然后在右侧的 "Modify options" 标签页中，会看到 "Environment variables" 部分。在这里，你可以手动添加环境变量键值对。


> 打包
```shell
# 打包传参  -D 参数
mvn clean package -DDB_USERNAME=root -DDB_PASSWORD=123456
# 打包跳过测试
mvn clean package -DskipTests=true
```

> 安装依赖

如果 `mvn install` 、`mvn clean` 依赖过程中出现问题, 可以通过 `install` ` platform-dependencies` 处理。

> spotless 格式化替换命令
```shell
# 执行下面命令报错
mvn spotless:apply
```
```text
# 报错信息
[ERROR] No plugin found for prefix 'spotless' in the current project and in the plugin groups [io.spring.javaformat, org.apache.maven.plugins, org.codehaus.mojo] available from the repositories [local (/Users/ming/local/repo), alimaven (http://maven.aliyun.com/nexus/content/groups/public/)] -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/NoPluginFoundForPrefixException
```
```shell
# 替换命令
mvn com.diffplug.spotless:spotless-maven-plugin:2.34.0:apply
```