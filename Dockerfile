FROM openjdk:21

WORKDIR /app

ADD build .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "linktracker-coursework/linktracker-coursework-0.0.1-SNAPSHOT.jar"]