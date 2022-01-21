FROM openjdk:11
WORKDIR /app
ADD /target/documentapp.jar /app/documentapp.jar
ENTRYPOINT ["java", "-jar", "/app/documentapp.jar"]