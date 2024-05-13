# Stage 1: Build the application
FROM maven:3.8.6-jdk-17 as builder
WORKDIR /pogoda

# Copy the source code and other necessary files
COPY ./pom.xml ./pom.xml
COPY ./src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Setup the run environment
FROM openjdk:17-jre-slim
WORKDIR /pogoda

# Copy the built JAR file from the build stage
COPY --from=builder /pogoda/target/*.jar pogoda.jar

# Expose the port your app runs on
EXPOSE 8080

# Command to execute the application
CMD ["java", "-jar", "pogoda.jar"]
