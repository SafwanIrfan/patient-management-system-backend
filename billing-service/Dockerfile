# base image coming from maven as a builder step to build the app
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

# it will download all the dependencies using pom.xml file
RUN mvn dependency:go-offline -B

# copy src folder
COPY src ./src

# package everything up
RUN mvn clean package

FROM eclipse-temurin:21-jre AS runner

WORKDIR /app

# copy the jar that comes from the target folder and that folder has
# auto generated code that we need for our grpc server for that
COPY --from=builder ./app/target/billing-service-0.0.1-SNAPSHOT.jar ./app.jar

# we expose port
EXPOSE 8081
EXPOSE 9091

# entrypoint on how to start a container based on this image
ENTRYPOINT ["java", "-jar", "app.jar"]