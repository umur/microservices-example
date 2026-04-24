FROM eclipse-temurin:21-jre-alpine
RUN addgroup -S cinetrack && adduser -S cinetrack -G cinetrack
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN chown cinetrack:cinetrack app.jar
USER cinetrack
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
