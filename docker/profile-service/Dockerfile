# Template for docker files for java coachcore microservices

FROM openjdk:25-jdk-bullseye

RUN mkdir /app
COPY ./target /app
WORKDIR /app

EXPOSE 3080

ENTRYPOINT ["java", "-jar", "profile-service-0.0.1-SNAPSHOT.jar"]