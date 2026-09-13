FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY backend /app

RUN apt-get update \
    && apt-get install -y --no-install-recommends maven \
    && mvn clean verify \
    && apt-get purge -y maven \
    && apt-get autoremove -y \
    && rm -rf /var/lib/apt/lists/*

EXPOSE 8080

CMD ["java", "-jar", "target/backend-1.0.0.jar"]
