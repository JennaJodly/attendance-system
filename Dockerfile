# # Use OpenJDK 17 as base image
# FROM openjdk:17-jdk-slim

# # Set working directory
# WORKDIR /app

# # Copy Maven files first for better layer caching
# COPY backend/pom.xml .
# COPY backend/mvnw .
# COPY backend/mvnw.cmd .
# COPY backend/.mvn .mvn

# # Make mvnw executable
# RUN chmod +x ./mvnw

# # Download dependencies (this layer will be cached if pom.xml doesn't change)
# RUN ./mvnw dependency:resolve

# # Copy source code
# COPY backend/src src

# # Build the application
# RUN ./mvnw clean package -DskipTests

# # Expose port 8080
# EXPOSE 8080

# # Run the application
# CMD ["java", "-jar", "target/attendance-1.0.jar"]

# Use OpenJDK 17
FROM openjdk:17-jdk-slim

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy only what is needed
COPY backend/pom.xml .
COPY backend/src ./src

# Copy mvn wrapper if used (optional)
# COPY backend/mvnw . 
# COPY backend/.mvn .mvn
# RUN chmod +x mvnw

# Download dependencies
RUN mvn dependency:resolve

# Expose port (Spring Boot default)
EXPOSE 8080

# Run the app using Spring Boot plugin
CMD ["mvn", "spring-boot:run"]
