#!/bin/bash

#!/bin/bash
set -e

# 获取脚本自身路径，兼容软链接
SCRIPT_PATH="$(readlink -f "$0")"
SCRIPT_DIR="$(dirname "${SCRIPT_PATH}")"

cd ${SCRIPT_DIR}

# ab-test.sh: 使用 Apache Bench 对 TransactionController 暴露的接口进行压力测试
# 用法：bash ab-test.sh
# 可自定义总请求数(-n)、并发数(-c)和目标地址

BASE_URL="http://localhost:8080/api/transactions"
TOTAL_REQUESTS=1000
CONCURRENCY=50

# GET /api/transactions
ab -n $TOTAL_REQUESTS -c $CONCURRENCY "$BASE_URL?page=0&size=10"

# POST /api/transactions 示例（需根据实际业务调整请求体）
ab -n $TOTAL_REQUESTS -c $CONCURRENCY -p post.json -T 'application/json' "$BASE_URL"

# GET /api/transactions/{id} 示例（假设存在 id=1）
ab -n $TOTAL_REQUESTS -c $CONCURRENCY "$BASE_URL/1"

# 说明：
# - post.json 文件需在同目录下，内容如：
#   {"amount":100,"description":"test","type":"TRANSFER"}
# - 可根据实际接口调整参数和 body
