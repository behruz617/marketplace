# Java 21 istifadə edirik
FROM openjdk:21-jdk-slim

# App üçün iş qovluğu
WORKDIR /app

# JAR faylını build/lib-sdən konteynerə köçür
COPY build/libs/*.jar app.jar

# App işlədiləcək port
EXPOSE 8080

# App-ı işə sal
ENTRYPOINT ["java", "-jar", "app.jar"]
