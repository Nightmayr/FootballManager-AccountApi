
FROM openjdk
COPY --from=maven-build /build/target/Account-0.0.1-SNAPSHOT.jar .
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/Account-0.0.1-SNAPSHOT.jar"]
EXPOSE 8081

