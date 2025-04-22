# Imagen base con Maven y JDK
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Directorio de trabajo
WORKDIR /app

# Copia pom.xml y descarga dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el resto del código
COPY src ./src

# Construye el proyecto
RUN mvn package -DskipTests

# Imagen final para ejecución
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copia el jar generado desde la imagen anterior
COPY --from=build /app/target/code-with-quarkus-1.0.0-SNAPSHOT.jar /app

# Expone el puerto 8080
EXPOSE 8080

# Comando para ejecutar el jar
CMD ["java", "-jar", "code-with-quarkus-1.0.0-SNAPSHOT.jar"]

