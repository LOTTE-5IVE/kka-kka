FROM openjdk:11-ea-jdk-slim
VOLUME /tmp
COPY build/libs/config-service-0.0.1-SNAPSHOT.jar ConfigServer.jar
ENTRYPOINT ["java","-jar","ConfigServer.jar"]