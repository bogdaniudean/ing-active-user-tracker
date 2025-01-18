# Stage 1: Build
FROM gradle:8.4.0-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle shadowJar -x test

# Stage 2: Run
FROM amazoncorretto:21 AS runtime
WORKDIR /app
COPY --from=builder /app/build/libs/server-ktor-all.jar app.jar
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]