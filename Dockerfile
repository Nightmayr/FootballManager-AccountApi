# FROM maven:latest as maven-build
# WORKDIR /build
# COPY . .
# RUN mvn package spring-boot:repackage
# EXPOSE 8081

# FROM openjdk:8-jdk-alpine

FROM openjdk:8-jdk-alpine
# VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8081

