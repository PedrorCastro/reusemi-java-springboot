FROM openjdk:17-jdk-slim

# Defina encoding
ENV LANG C.UTF-8
ENV LC_ALL C.UTF-8

WORKDIR /app

# Copie o JAR buildado localmente (mais rápido)
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]