#!/bin/bash
set -e

# 获取脚本自身路径，兼容软链接
SCRIPT_PATH="$(readlink -f "$0")"
SCRIPT_DIR="${SCRIPT_PATH}"
PROJECT_ROOT="${SCRIPT_DIR}"


# 1. 打包 Spring Boot 应用
echo "[STEP 1] Maven 打包..."
mvn clean package -DskipTests

# 2. 构建 Docker 镜像

echo "[STEP 2] Docker 构建镜像..."
docker build -t transaction-app .

# 3. 启动 Docker 容器（如已存在同名容器，先移除）
if [ $(docker ps -aq -f name=transaction-app-running) ]; then
  echo "[INFO] 移除已存在的 transaction-app-running 容器..."
  docker rm -f transaction-app-running
fi

echo "[STEP 3] 启动 Docker 容器..."
docker run -d --name transaction-app-running -p 8080:8080 transaction-app

echo "启动成功！访问 http://localhost:8080"
