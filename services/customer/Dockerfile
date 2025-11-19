FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY target/customer-1.0.jar /app

EXPOSE 8030

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "customer-1.0.jar"]