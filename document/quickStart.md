# **社团管理系统快速启动文档**

## 一、系统环境及软件版本

以下是运行本项目所需的软件及其环境，点击可进入

1. [**Redis**](https://gitee.com/TSGU-OSC/OSC_main/tree/master/blog/RedisMarkdown/redisInstall)
	* 版本：5.0.14.1 (Windows版)
2. [**MinIO**](https://gitee.com/TSGU-OSC/OSC_main/blob/master/blog/MinioMarkdown/README.md)
	* 版本：8.2.0
3. [**MySQL**](https://gitee.com/TSGU-OSC/OSC_main/blob/master/blog/MysqlMarkdown/README.md)
	* 版本：8.0.34
4. [**Node.js**](https://gitee.com/TSGU-OSC/OSC_main/blob/master/blog/nodeJsMarkdown/README.md)
	* 版本：16.16.0
5. [**npm**](https://gitee.com/TSGU-OSC/OSC_main/blob/master/blog/nodeJsMarkdown/README.md)
	* 版本：9.6.6
6. **JDK**
	* 版本：17

## 二、安装与配置

请确保按照上述版本要求安装相应的软件，并正确配置环境变量。具体安装步骤和配置方法可以参考各软件的官方文档。

## 三、启动社团管理系统

在完成所有依赖软件的安装和配置后，可以按照社团管理系统的启动指南进行启动操作。

1. 执行sql脚本

请先执行management-system\project\osc-back\sql中的sql脚本。

2. 启动后端

在下载完本仓库后调整到含有的jar包的目录下，win+R并输入cmd在打开的黑框中输入
```
java -jar OSC-0.0.1-SNAPSHOT.jar --spring.datasource.username=你的MySQL用户名 --spring.datasource.password=你的MySQL密码 --spring.data.redis.password=你的redis密码
```

> **注意：**本项目MySQL端口为`3306`,redis端口号为`6379`,前端端口为`8080`

这样后端就启用啦

3.  启动前端

用vscode打开dist目录后ctrl+j打开终端后输入
```
npm i
```
若遇到报错则输入以下代码取消ssl验证：
```
npm config set strict-ssl false
```

随后更换npm镜像源：
```
npm config set registry http://registry.npm.taobao.org
```

之后再重新输入npm i

然后在终端输入serve后按住ctrl键点击Local后的网址。
```
serve
```

- 若遇到serve : 无法加载文件
> 以管理员身份运行PowerShell （注意不是cmd），执行：
```
set-ExecutionPolicy RemoteSigned
```
> 按 ‘Y’ 即可。
> 再重新输入serve执行以上操作

这样我们就成功啦，以下是展示图：

![image-01](./img/image-quickStart-01.png)



请注意，具体的启动步骤可能因系统架构和配置而有所不同，请参考社团管理系统的相关文档或联系系统管理员以获取更详细的指导。