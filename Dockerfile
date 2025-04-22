# Usa una imagen oficial de Java como base
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el JAR de tu proyecto al contenedor
COPY target/code-with-quarkus-1.0.0-SNAPSHOT.jar /app

# Expone el puerto que usará tu aplicación (cambia si es necesario)
EXPOSE 8080

# Comando para ejecutar el JAR
CMD ["java", "-jar", "code-with-quarkus-1.0.0-SNAPSHOT.jar"]

