FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/discovery-service-0.0.1-SNAPSHOT.jar DiscoveryServer.jar
ENTRYPOINT ["java","-jar","DiscoveryServer.jar"]