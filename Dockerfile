# ============================
# 1) STAGE: BUILD
# ============================
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn -q dependency:go-offline

COPY src ./src

RUN mvn -q -DskipTests package


# ============================
# 2) STAGE: RUNTIME
# ============================
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copia el JAR generado del stage anterior
COPY --from=build /app/target/*.jar app.jar

# Render SIEMPRE usa el puerto 8080 internamente
ENV PORT=8080
EXPOSE 8080

# Activamos el perfil supabase
ENV SPRING_PROFILES_ACTIVE=supabase

# Ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
