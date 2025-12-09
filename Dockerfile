FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/library-management-system-1.0-SNAPSHOT.jar app.jar

ENV DB_URL=jdbc:postgresql://postgres:5432/library_db
ENV DB_USER=library_user
ENV DB_PASSWORD=StrongPassword123!
ENV DB_AUTOCOMMIT=false

CMD ["java", "-jar", "app.jar"]
