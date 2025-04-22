# Usar una imagen con OpenJDK 21
FROM maven:3.9.4-openjdk-21 AS build

# Directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y descarga las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el resto del código
COPY . .

# Construye el proyecto
RUN mvn package -DskipTests

# Imagen final para ejecución
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copia el JAR generado desde la imagen anterior
COPY --from=build /app/target/code-with-quarkus-1.0.0-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "code-with-quarkus-1.0.0-SNAPSHOT.jar"]

