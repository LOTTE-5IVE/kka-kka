FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/apigateway-service-0.0.1-SNAPSHOT.jar ApigatewayServer.jar
ENTRYPOINT ["java","-jar","ApigatewayServer.jar"]