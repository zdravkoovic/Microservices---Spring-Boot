FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY target/notification-1.0.jar /app

EXPOSE 8070

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "notification-1.0.jar"]