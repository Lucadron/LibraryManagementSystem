# 📚 Library Management System (Java + PostgreSQL + Docker)

**Languages:**  
[🇹🇷 Türkçe](README.tr.md) • [🇩🇪 Deutsch](README.de.md)

---

## 🎯 Overview

This is a **console-based Library Management System** built with **Java 21**, **JDBC**, **PostgreSQL**, **Flyway migrations**,  
and **Docker Compose** for fully automated setup.

The system supports:

- Clean *layered architecture*
- PostgreSQL with automatic schema migration (Flyway)
- Multi-language console UI (EN / DE / TR)
- Transaction-safe borrow/return operations
- Unit tests (JUnit 5 + Mockito)
- Fat JAR packaging via Maven Shade Plugin

---

## 🏗️ Architecture

```bash
src/
└── main/java/com/lucadron
├── model/ # Book, Member, BorrowedBook
├── repository/ # JDBC repositories
├── service/ # Business logic + validation
├── controller/ # Console menu
├── i18n/ # Language manager
└── Main.java # Application entry point
```

---

## 🌐 Internationalization (i18n)

The system supports **3 languages**:

1 - English
2 - Deutsch
3 - Türkçe

Language files are located in:

```bash
src/main/resources/messages_en.properties
src/main/resources/messages_de.properties
src/main/resources/messages_tr.properties
```
---

## 🗄️ Database & Migration

Database is managed by **Flyway** and migrations run automatically at application startup.

Migration files:

```bash
src/main/resources/db/migration/V1__init_library_schema.sql
```

This creates:

- `books`
- `members`
- `borrowed_books`

---

## 🚀 Running the Application

## OPTION A — Run with Docker (recommended for testers)

### 1️⃣ Build & Start Containers

```bash
docker compose up --build
```

Docker will:

Start PostgreSQL

Run Flyway migration

Start the application inside a container

Show the console menu

To stop:

```bash
docker compose down
```
Running interactively inside the app container:

```bash
docker compose up -d
docker exec -it library-app bash
java -jar app.jar
```

---

OPTION B — Run Locally (without Docker)
### 1️⃣ Build JAR

```bash
mvn clean package
```

This produces:

```bash
target/library-management-system-1.0-SNAPSHOT.jar
```
---

### 2️⃣ Start PostgreSQL manually
Run database/CreateDatabase.sql in your local PostgreSQL instance.

Then:

```bash
java -jar target/library-management-system-1.0-SNAPSHOT.jar
```
---

## 🧪 Unit Tests
Run:

```bash
mvn test
```

Includes:

Borrowing rules

Error handling

Validation logic

Repository mocking

---

## 🛠️ Technologies
| Layer | Technology |
|-------|------------|
| Language | Java 21 |
| Build Tool | Maven |
| Database | PostgreSQL |
| Migration | Flyway |
| DB Access | JDBC |
| Testing | JUnit 5 + Mockito |
| Packaging | Maven Shade Plugin |
| Runtime | Docker Compose |
| i18n | ResourceBundle |
---

## 📌 Docker Services
docker-compose.yml defines:

library-postgres → PostgreSQL 16

library-app → Java console application

Environment variables override default DB config.
