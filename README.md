# 📚 Library Management System (Java Console Application)

[🇹🇷 Türkçe Doküman](README.tr.md)  
[🇩🇪 Deutsche Dokumentation](README.de.md)

---

## 🎯 Project Overview

This project is a **console-based Library Management System** developed as part of a Java developer case study.  
It demonstrates:

- Clean **layered architecture**
- **JDBC** database integration (PostgreSQL)
- **Internationalization (i18n)** support (EN/DE/TR)
- **Unit testing** with JUnit 5 + Mockito
- **Flyway migrations** for database schema management
- **Docker Compose** for fully automated setup
- Transaction-safe operations (borrow/return)
- Professional code structure suitable for production

---

## 🏗️ Architecture

```
src/
└── main/java/com/lucadron
    ├── model/       # POJO classes (Book, Member, BorrowedBook)
    ├── repository/  # JDBC repositories (CRUD + queries)
    ├── service/     # Business logic (validation, rules)
    ├── controller/  # Console UI (menus, prompts)
    ├── i18n/        # LanguageManager + locale handling
    └── Main.java    # Application entry point
```

---

## ✅ Project Requirements & Implementation Status

### Core Requirements (✓ Completed)
- ✅ **Domain Classes**: Book, Member with constructors, getters/setters, toString
- ✅ **Add Book**: Create new book entries
- ✅ **Add Member**: Register new library members
- ✅ **Borrow Book**: Transaction-safe borrowing system
- ✅ **Return Book**: Transaction-safe return system
- ✅ **List Member's Books**: Display all books borrowed by a specific member
- ✅ **List All Books**: View complete book inventory

### Database Requirements (✓ Completed)
- ✅ **PostgreSQL Database**: Full JDBC integration
- ✅ **Tables**: `books`, `members`, `borrowed_books`
- ✅ **Flyway Migrations**: Automated schema management
- ✅ **Sample Data**: Pre-loaded test data included

### Architecture Requirements (✓ Completed)
- ✅ **Layered Architecture**: model → repository → service → controller
- ✅ **Separation of Concerns**: Clear layer boundaries
- ✅ **Professional Structure**: Production-ready code organization

### Bonus Features (✓ Implemented)
- ✅ **Input Validation**: Comprehensive validation rules
- ✅ **3-Book Limit**: Members can borrow maximum 3 books simultaneously
- ✅ **Error Handling**: Already borrowed books cannot be borrowed again
- ✅ **Unit Tests**: JUnit 5 + Mockito test coverage
- ✅ **Internationalization**: Multi-language support (EN/DE/TR)
- ✅ **Docker Support**: Containerized deployment
- ⚠️ **Partial Search**: *Not implemented in this version*

---

## 🌐 Internationalization (i18n)

The system supports **3 languages**:

- 🇬🇧 English (default)
- 🇩🇪 German
- 🇹🇷 Turkish

Upon startup:

```
Select language / Sprache auswählen / Dil seçiniz:
1 - English
2 - Deutsch
3 - Türkçe
```

Translations are stored under:

```
src/main/resources/messages_en.properties
src/main/resources/messages_de.properties
src/main/resources/messages_tr.properties
```

---

## 🗄️ Database & Migration

Database is managed by **Flyway** and migrations run automatically at application startup.

Migration files:

```
src/main/resources/db/migration/V1__init_library_schema.sql
```

This creates:

- `books`
- `members`
- `borrowed_books`

Sample data is inserted automatically.

---

## 🔧 Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java 21 |
| Build Tool | Maven |
| Database | PostgreSQL |
| Migration | Flyway |
| DB Access | JDBC |
| Testing | JUnit 5 + Mockito |
| Packaging | Maven Shade Plugin (fat jar) |
| Runtime | Docker Compose |
| i18n | ResourceBundle |

---

## 🚀 Running the Application

### OPTION A — Run with Docker (recommended for testers)

#### 1️⃣ Build & Start Containers

```bash
docker compose up --build
```

Docker will:
- Start PostgreSQL
- Run Flyway migration
- Start the application inside a container
- Show the console menu

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

### OPTION B — Run Locally (without Docker)

#### 1️⃣ Build JAR

```bash
mvn clean package
```

This produces:

```
target/library-management-system-1.0-SNAPSHOT.jar
```

#### 2️⃣ Start PostgreSQL manually

Run `database/CreateDatabase.sql` in your local PostgreSQL instance.

Then:

```bash
java -jar target/library-management-system-1.0-SNAPSHOT.jar
```

---

## 🧪 Unit Tests

The project includes **5 meaningful test cases** covering:

- Validation rules  
- Borrowing constraints (3-book limit)
- Error handling (already borrowed books)
- Repository interaction (mocked)  

Test runner:

```bash
mvn test
```

---

## 📌 Docker Services

`docker-compose.yml` defines:

- **library-postgres** → PostgreSQL 16
- **library-app** → Java console application

Environment variables override default DB config.

---

## 📋 Delivery Requirements

✅ **Completed Checklist:**
- ✅ Project shared on GitHub
- ✅ README with detailed instructions
- ✅ SQL script for database tables (Flyway migrations)
- ✅ Sample initial data included
- ✅ Unit tests implemented (bonus feature)
- ✅ Input validation (bonus feature)
- ✅ 3-book borrowing limit (bonus feature)
- ✅ Error messages for already borrowed books (bonus feature)
- ✅ Multi-language support (extra feature)
- ✅ Docker containerization (extra feature)

---

## 🎁 Additional Features Beyond Requirements

This implementation exceeds the base requirements with:

- **Internationalization (i18n)**: Full multi-language support
- **Docker Integration**: One-command deployment
- **Flyway Migrations**: Professional database versioning
- **Fat JAR Packaging**: Standalone executable
- **Production-Ready Structure**: Enterprise-level code organization
- **Comprehensive Testing**: Mocked repository layer tests

---

## 📝 Notes

- **Partial search by book title** is the only bonus feature not implemented in the current version
- All core requirements and most bonus features have been successfully completed
- The system is production-ready and follows Java best practices
```
