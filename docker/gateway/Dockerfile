# Template for docker files for java coachcore microservices

FROM openjdk:25-jdk-bullseye

RUN mkdir /app
COPY ./target /app
WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "gateway-1.1.5.jar"]