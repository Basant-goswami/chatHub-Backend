# Use OpenJDK 17 as base image (adjust if your app uses a different Java version)
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy your Maven/Gradle built jar file into container
COPY target/chatBackend-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on (default: 8080)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
