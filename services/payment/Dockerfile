FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY target/payment-1.0.jar /app

EXPOSE 8060

ENTRYPOINT [ "java", "-XX:MaxRAMPercentage=75.0", "-jar", "payment-1.0.jar" ]