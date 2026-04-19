# Use Java 17
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy everything
COPY backend /app

# Build the project
RUN apt-get update && apt-get install -y maven && mvn clean package

# Expose port
EXPOSE 8080

# Run the jar
CMD ["java", "-jar", "target/backend-1.0.0.jar"]