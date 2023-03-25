#
# Build stage
#
#FROM maven:3.8.2-jdk-8 AS build
#COPY . .
#RUN mvn clean package -DskipTests
FROM maven:3.8.2-jdk-8 AS build
COPY . .
RUN mvn install -U -DskipTests
#
# Package stage
#
FROM openjdk:8-jdk-slim
COPY --from=build /target/smartClientManagementBackEnd-0.0.1-SNAPSHOT-dev.jar smartClientManagementBackEnd.jar
# ENV PORT=8081
EXPOSE 8081
ENTRYPOINT ["java","-jar","smartClientManagementBackEnd.jar"]