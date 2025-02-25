FROM eclipse-temurin:17
WORKDIR /app
COPY build/libs/*.jar app.jar
RUN mkdir -p /app/data
CMD ["java", "-jar", "app.jar"]