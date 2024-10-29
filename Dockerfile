FROM openjdk:17-jdk

ARG JAR_FILE=./build/libs/*.jar

COPY ${JAR_FILE} /app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=secret", "-jar", "/app.jar"]