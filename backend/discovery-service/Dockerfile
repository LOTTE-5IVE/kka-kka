FROM openjdk:11-ea-jdk-slim
VOLUME /tmp
COPY build/libs/discovery-service-0.0.1-SNAPSHOT.jar DiscoveryServer.jar
ENTRYPOINT ["java","-jar","DiscoveryServer.jar"]