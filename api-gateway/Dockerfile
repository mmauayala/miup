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
WORKDIR /api-gateway

# Definir un nombre específico para el JAR
ARG JAR_FILE=target/api-gateway-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} api-gateway.jar

# Exponer el puerto (ajusta según el microservicio)
EXPOSE 9191  

# Comando para ejecutar el microservicio
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]