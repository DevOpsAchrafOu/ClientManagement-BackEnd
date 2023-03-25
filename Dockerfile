#
# Build stage
#
FROM maven:3.8.2-jdk-1.8 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:1.8-jdk-slim
COPY --from=build /target/smartClientManagementBackEnd-0.0.1-SNAPSHOT-dev.jar smartClientManagementBackEnd.jar
# ENV PORT=8081
EXPOSE 8081
ENTRYPOINT ["java","-jar","smartClientManagementBackEnd.jar"]