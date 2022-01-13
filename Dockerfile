FROM openjdk:11
WORKDIR /app
ADD /target/document-0.0.1-SNAPSHOT.jar /app/document-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/document-0.0.1-SNAPSHOT.jar"]