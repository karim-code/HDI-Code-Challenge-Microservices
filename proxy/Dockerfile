FROM adoptopenjdk/openjdk8:latest
ENV MONGO_URL mongodb://localhost/microservice_sample
EXPOSE 8080
RUN mkdir -p /app/
ADD build/libs/userregistration-0.0.1-SNAPSHOT.jar /app/userregistration-01.jar
ENTRYPOINT ["java", "-jar", "/app/userregistration.jar"]