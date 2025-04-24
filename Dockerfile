# Usar una imagen con Maven y OpenJDK 17
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y descarga las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el resto del código
COPY . .

# Construye el proyecto
#RUN mvn package -DskipTests
RUN mvn package -DskipTests -Dquarkus.package.type=uber-jar

# Imagen final para ejecución con OpenJDK 17
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copia el JAR generado desde la imagen anterior
#COPY --from=build /app/target/code-with-quarkus-1.0.0-SNAPSHOT.jar /app
COPY --from=build /app/target/code-with-quarkus-1.0.0-SNAPSHOT-runner.jar /app/app.jar

EXPOSE 8080

#CMD ["java", "-jar", "code-with-quarkus-1.0.0-SNAPSHOT.jar"]
CMD ["java", "-jar", "app.jar"]
