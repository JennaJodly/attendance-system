# Multi-stage build for production
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app
# Copy pom.xml first for better caching
COPY backend/pom.xml .
RUN mvn dependency:go-offline -B
# Copy source and build
COPY backend/src src
RUN mvn clean package -DskipTests

# Production stage
FROM openjdk:17-jdk-slim
# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
WORKDIR /app
# Copy jar from build stage
COPY --from=build /app/target/attendance-1.0.jar app.jar
# Expose port
EXPOSE 10000
# Health check endpoint
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:$PORT/actuator/health || exit 1
# Run the application with dynamic port
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-10000} -Dspring.profiles.active=prod -jar app.jar"]