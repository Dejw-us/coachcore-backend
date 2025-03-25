# Template for docker files for java coachcore microservices

FROM openjdk:25-jdk-bullseye

RUN mkdir /app
COPY ./target /app
WORKDIR /app

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "oauth2-server-1.0.0.jar"]