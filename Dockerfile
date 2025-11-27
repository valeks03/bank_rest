#FROM maven:3.9-eclipse-temurin-21 AS build
#
#WORKDIR /app
#
#COPY pom.xml .
#
#RUN mvn -q -DskipTests dependency:go-offline
#
#COPY src ./srс
#
#RUN mvn -q -DskipTests package

FROM eclipse-temurin:21-jre

WORKDIR /app

#ARG JAR_FILE=target/*.jar
COPY target/varta-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]