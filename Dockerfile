FROM eclipse-temurin:21-jre
WORKDIR /app

COPY target/transaction-0.0.1-SNAPSHOT.jar transaction-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "transaction-0.0.1-SNAPSHOT.jar"]
