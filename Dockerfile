FROM amazoncorretto:18.0.2

WORKDIR /app

ADD build .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "JavaTemplates-CourseWork/JavaTemplates-CourseWork-0.0.1-SNAPSHOT.jar"]