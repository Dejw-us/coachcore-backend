# Template for docker files for java coachcore microservices

FROM openjdk:25-jdk-bullseye

RUN mkdir /app
COPY ./target /app
WORKDIR /app

EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "${APP_FILE_NAME}"]