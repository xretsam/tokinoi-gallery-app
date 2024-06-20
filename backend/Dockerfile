FROM maven:3.9.7-eclipse-temurin-17-alpine AS build

WORKDIR /app

COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/target/tokinoi-gallery-app-0.0.1-SNAPSHOT.jar ./tokinoi-gallery-app-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "tokinoi-gallery-app-0.0.1-SNAPSHOT.jar"]