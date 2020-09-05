FROM openjdk:latest

ADD target/maths-mysql.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8086
