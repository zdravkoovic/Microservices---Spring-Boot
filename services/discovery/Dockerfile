FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY target/discovery-1.0.jar /app

EXPOSE 8761

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "discovery-1.0.jar"]