#
# Build stage
#
#FROM maven:3.8.2-jdk-8 AS build
#COPY . .
#RUN mvn clean package -DskipTests
FROM maven:3.8.2-jdk-8 AS build
COPY . .
#RUN mvn clean install -U -DskipTests -Pprod-deploy
RUN mvn clean package -DskipTests -Pprod
#
# Package stage
#
FROM openjdk:8-jdk-slim
COPY --from=build /target/smartClientManagementBackEnd-0.0.1-SNAPSHOT-prod.jar smartClientManagementBackEnd.jar
# ENV PORT=8081
EXPOSE 8081
ENTRYPOINT ["java","-jar","smartClientManagementBackEnd.jar"]