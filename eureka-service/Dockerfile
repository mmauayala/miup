# Usar una imagen base de OpenJDK 17
FROM maven:3.9-eclipse-temurin-17 AS build

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

COPY pom.xml ./
COPY .mvn .mvn

# Copy application source code
COPY src src

RUN mvn clean install -DskipTests  

# Usar una imagen más ligera para la ejecución
FROM eclipse-temurin:17-jre

# Establecer el directorio de trabajo
WORKDIR /eureka-service

# Definir un nombre específico para el JAR
ARG JAR_FILE=target/eureka-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} eureka-service.jar

# Exponer el puerto (ajusta según el microservicio)
EXPOSE 8761  

# Comando para ejecutar el microservicio
ENTRYPOINT ["java", "-jar", "eureka-service.jar"]