FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY target/order-1.0.jar /app

EXPOSE 8040

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "order-1.0.jar"]