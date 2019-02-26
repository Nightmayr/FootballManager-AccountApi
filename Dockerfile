
FROM openjdk
ADD target/Account-0.0.1-SNAPSHOT.jar Account-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/Account-0.0.1-SNAPSHOT.jar"]
EXPOSE 8081

