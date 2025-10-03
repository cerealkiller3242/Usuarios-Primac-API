FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

# Copy maven executable and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Build all dependencies for offline use
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline -B

# Copy source code
COPY src src

# Build the application
RUN --mount=type=cache,target=/root/.m2 mvn package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# Install curl for healthcheck
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy the built jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8085

# Add healthcheck
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8085/actuator/health || exit 1

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]