# Step 1: Use an official JDK image (Java 21) to build the app
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Step 2: Set working directory inside container
WORKDIR /app

# Step 3: Copy project files
COPY . .

# Step 4: Build the project (skip tests for faster builds)
RUN mvn clean package -DskipTests

# Step 5: Use a lightweight JRE image (Java 21) for running the app
FROM eclipse-temurin:21-jre

# Step 6: Set working directory
WORKDIR /app

# Step 7: Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Step 8: Expose port (Spring Boot default is 8080)
EXPOSE 8080

# Step 9: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]