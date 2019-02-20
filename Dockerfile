FROM maven:latest as maven-build
WORKDIR /build
COPY . .
RUN mvn package spring-boot:repackage

FROM openjdk:8-jdk-alpine
# VOLUME /tmp
COPY --from=maven-build /build/target/Account-0.0.1-SNAPSHOT.jar .
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8081

