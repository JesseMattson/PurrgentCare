FROM openjdk:19
WORKDIR /app
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "PurrgentCare-0.0.1-SNAPSHOT.jar"]