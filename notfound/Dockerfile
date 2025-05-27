# Utilise une image avec Maven et OpenJDK 21
FROM maven:3.9.5-eclipse-temurin-21 AS build

# Définit le répertoire de travail
WORKDIR /app

# Copie le pom.xml
COPY pom.xml .

# Télécharge les dépendances (cache layer)
RUN mvn dependency:go-offline -B

# Copie le code source
COPY src ./src

# Compile l'application
RUN mvn clean package -DskipTests

# Image de production
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copie le JAR depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Expose le port
EXPOSE 8080

# Lance l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
