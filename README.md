# Transaction Home Task

一个基于 Spring Boot 的交易管理系统，支持 RESTful API、分页、单元测试、Docker 部署与性能测试。

## 目录结构与主要模块

- `src/main/java/com/example/transaction/`
  - `controller/`    —— 交易相关 REST API 控制器
  - `service/`       —— 业务逻辑层，含接口与实现
  - `dao/`           —— 数据访问层（内存实现，支持分页）
  - `model/`         —— 交易实体类
  - `request/`       —— 请求参数对象
  - `exception/`     —— 自定义异常
- `src/test/java/com/example/transaction/`
  - `controller/`    —— Controller 层单元测试
  - `service/`       —— Service 层单元测试
  - `dao/`           —— DAO 层单元测试
- `.performanceTests/ab-test.sh` —— ab 性能测试脚本
- `k8s-start.sh`     —— 一键打包、构建镜像并部署到 k8s 的脚本
- `pom.xml`          —— Maven 依赖与插件配置

## 依赖
- jdk 21
- Spring Boot 3.5
- Spring Data Commons（分页支持）
- Lombok
- Spring Boot Actuator（健康监控）
- JUnit 5 + Mockito（单元测试）
- Docker 支持
- Apache Bench（ab）性能测试

---

## 使用github codespaces沙盒环境
可直接在github codespaces中运行，相关依赖配置在.devcontainer目录

## 如何用 Docker 启动项目

```sh
# 运行脚本
bash build_and_run_docker.sh
```

## 如何发布到k8s
```sh
bash build_and_run_k8s.sh
```

## 如何运行单元测试

在项目根目录下执行：

```sh
mvn test
```
