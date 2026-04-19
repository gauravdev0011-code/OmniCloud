FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy backend source
COPY backend /app

# Install Maven and build project
RUN apt-get update && \
    apt-get install -y maven && \
    mvn clean package -DskipTests

# Expose app port
EXPOSE 8080

# Run the Spring Boot jar
CMD ["java", "-jar", "target/backend-1.0.0.jar"]