# Usar una imagen con OpenJDK 17 y Maven
FROM maven:3.9.4-openjdk-17-slim AS build

# Directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y descarga las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el resto del código
COPY . .

# Construye el proyecto
RUN mvn package -DskipTests

# Imagen final para ejecución con OpenJDK 17
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia el JAR generado desde la imagen anterior
COPY --from=build /app/target/code-with-quarkus-1.0.0-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "code-with-quarkus-1.0.0-SNAPSHOT.jar"]

