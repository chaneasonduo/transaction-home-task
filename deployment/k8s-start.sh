#!/bin/bash
# Kubernetes 启动脚本：一键部署/更新 Spring Boot 应用
# 用法：bash k8s-start.sh
# 需先配置好 kubectl 连接到目标集群

set -e

YAML_FILE="$(dirname "$0")/deployment.yaml"

if [ ! -f "$YAML_FILE" ]; then
  echo "[ERROR] 未找到 deployment.yaml 文件：$YAML_FILE"
  exit 1
fi

echo "[INFO] 应用 Kubernetes 配置..."
kubectl apply -f "$YAML_FILE"

if [ $? -eq 0 ]; then
  echo "[SUCCESS] 已成功部署/更新 Kubernetes 资源。"
  echo "[INFO] 可用命令查看状态：kubectl get pods,svc -n <namespace>"
else
  echo "[ERROR] 部署失败，请检查 YAML 文件和集群配置。"
  exit 2
fi
