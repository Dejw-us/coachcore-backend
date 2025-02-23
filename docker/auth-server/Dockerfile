# Template for docker files for java coachcore microservices

FROM openjdk:25-jdk-bullseye

RUN mkdir /app
COPY ./target /app
WORKDIR /app

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "auth-server-0.0.1-SNAPSHOT.jar"]