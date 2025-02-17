# image for newsletter service

FROM openjdk:25-jdk-bullseye

RUN mkdir /app
COPY ./target /app
WORKDIR /app

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "newsletter-0.0.1-SNAPSHOT.jar"]