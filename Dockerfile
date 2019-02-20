FROM maven:latest as maven-build
WORKDIR /build
COPY . .
RUN mvn spring-boot:run
EXPOSE 8081
