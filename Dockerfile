FROM openjdk:8-alpine

COPY target/uberjar/ossss.jar /ossss/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/ossss/app.jar"]
