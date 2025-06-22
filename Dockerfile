FROM maven:3.9.9-eclipse-temurin-21 AS build
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn clean package

FROM eclipse-temurin:21-jdk
ARG JAR_FILE=/tmp/target/*.jar
COPY --from=build ${JAR_FILE} application.jar
CMD apt-get update -y
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/application.jar"]