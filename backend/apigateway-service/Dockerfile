FROM openjdk:11-ea-jdk-slim
VOLUME /tmp
COPY build/libs/apigateway-service-0.0.1-SNAPSHOT.jar ApigatewayServer.jar
ENTRYPOINT ["java","-jar","ApigatewayServer.jar"]