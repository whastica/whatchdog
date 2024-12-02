FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/Whatchdog-0.0.1-SNAPSHOT.jar /app/Whatchdog.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "Whatchdog.jar" ]

