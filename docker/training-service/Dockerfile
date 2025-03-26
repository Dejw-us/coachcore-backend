# Template for docker files for java coachcore microservices

FROM openjdk:25-jdk-bullseye

RUN mkdir /app
COPY ./target /app
WORKDIR /app

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "training-service-1.4.1.jar"]