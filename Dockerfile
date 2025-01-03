FROM openjdk:latest

RUN mkdir /app
COPY /target /app
WORKDIR /app

ENTRYPOINT ["java", "-jar", "api-0.0.1-SNAPSHOT.jar"]
