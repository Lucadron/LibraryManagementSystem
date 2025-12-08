# ============================
# 1. Aşama: Build (Maven + JDK)
# ============================
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# ÖNCE pom.xml kopyalanır (dependency cache için)
COPY pom.xml .

# Ardından kaynak kodlar
COPY src ./src

# Testler istersen burada çalıştırabilirsin; case için hız adına skip ediyoruz
RUN mvn clean package -DskipTests

# ============================
# 2. Aşama: Runtime (JRE only)
# ============================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Build aşamasında üretilen fat jar'ı kopyala
COPY --from=build /app/target/library-management-system-1.0-SNAPSHOT-shaded.jar app.jar

# Docker ortamı için default env değişkenleri
ENV DB_URL=jdbc:postgresql://postgres:5432/library_db
ENV DB_USER=library_user
ENV DB_PASSWORD=StrongPassword123!
ENV DB_AUTOCOMMIT=false

# Uygulamayı başlat
CMD ["java", "-jar", "app.jar"]
