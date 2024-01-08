# 项目说明

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