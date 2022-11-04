FROM openjdk:11-ea-jdk-slim
VOLUME /tmp
COPY build/libs/main-service-0.0.1-SNAPSHOT.jar MainService.jar
ENTRYPOINT ["java","-jar","MainService.jar"]