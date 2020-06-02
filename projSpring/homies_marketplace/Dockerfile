FROM openjdk:8-jdk-alpine
MAINTAINER josefrias
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} homiesdocker.jar
ENTRYPOINT ["java","-jar","/homiesdocker.jar"]
