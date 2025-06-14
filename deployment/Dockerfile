FROM eclipse-temurin:21-jre
WORKDIR /app
# 假设jar包在项目根目录target下，构建镜像时需先mvn package
COPY ../target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
