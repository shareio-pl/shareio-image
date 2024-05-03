FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:resolve-plugins

COPY src/main src/main

RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/image-0.0.1-SNAPSHOT.jar .

RUN apk add curl

EXPOSE 8084

CMD ["java", "-jar", "image-0.0.1-SNAPSHOT.jar"]
