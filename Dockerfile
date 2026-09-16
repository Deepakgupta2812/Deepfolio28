FROM eclipse-temurin:21-jdk AS build

WORKDIR /workspace
COPY deepak-updated/deepak/deepak/ .
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /workspace/target/deepak-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
