FROM openjdk:8-jdk-alpine
MAINTAINER josefrias
VOLUME /tmp
EXPOSE 8080
ADD target/homies_marketplace-0.0.1-SNAPSHOT.jar homiesdocker.jar
ENTRYPOINT ["java","-jar","/homiesdocker.jar"]
