FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY target/config-server-1.0.jar /app

EXPOSE 8888

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "config-server-1.0.jar"]