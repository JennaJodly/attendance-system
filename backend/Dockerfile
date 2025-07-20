# Use base Java 17 image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR into the image
COPY target/attendance-1.0.jar app.jar

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
