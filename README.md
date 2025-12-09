# 📚 Library Management System (Java Console Application)

[🇹🇷 Türkçe Doküman](README.tr.md)  
[🇩🇪 Deutsche Dokumentation](README.de.md)

---

## 🎯 Project Overview

This project is a **console-based Library Management System** developed as part of a Java developer case study.  
It demonstrates professional Java development skills with clean architecture, database integration, and modern development practices.

**Key Features:**
- Clean **layered architecture** (Model → Repository → Service → Controller)
- **JDBC** database integration with PostgreSQL
- **Flyway** database migrations
- **Internationalization (i18n)** support (EN/DE/TR)
- **Unit testing** with JUnit 5 + Mockito
- **Docker Compose** for automated deployment
- Transaction-safe operations
- **Stock management** with quantity tracking
- **Advanced search** functionality (partial keyword matching)

---

## 🏗️ Architecture

```
src/
└── main/java/com/lucadron
    ├── model/       # Domain classes (Book, Member, BorrowedBook)
    ├── repository/  # Data access layer (JDBC)
    ├── service/     # Business logic & validation
    ├── controller/  # Console UI
    ├── i18n/        # Language management
    └── Main.java    # Application entry point
```

---

## ✅ Requirements Implementation

### Core Features ✓
- ✅ **Add Book**: Create new book entries with stock quantity
- ✅ **Add Member**: Register library members with validation
- ✅ **Borrow Book**: Transaction-safe borrowing with stock management
- ✅ **Return Book**: Transaction-safe returns with stock updates
- ✅ **List Member's Books**: View borrowed books with member and book names
- ✅ **List All Books**: Complete inventory with availability status

### Bonus Features ✓
- ✅ **Partial Search**: Search books by title or author (case-insensitive)
- ✅ **Input Validation**: Comprehensive validation for all inputs
- ✅ **3-Book Limit**: Members can borrow maximum 3 books simultaneously
- ✅ **Stock Control**: Error handling for unavailable books
- ✅ **Unit Tests**: 10 test cases covering validation, business rules, and error scenarios

### Additional Enhancements ✓
- ✅ **Stock Management**: Multiple copies per book with quantity tracking
- ✅ **List All Members**: View all registered members
- ✅ **Enhanced Output**: Human-readable borrowed book details with names
- ✅ **Multi-language Support**: Full i18n implementation (EN/DE/TR)
- ✅ **Docker Support**: Containerized deployment with PostgreSQL

---

## 🌐 Internationalization

The system supports **3 languages** with complete translations:

```
Select language / Sprache auswählen / Dil seçiniz:
1 - English
2 - Deutsch
3 - Türkçe
```

Language files: `src/main/resources/messages_{en|de|tr}.properties`

---

## 🗄️ Database

**PostgreSQL** database managed by **Flyway** migrations:

**Tables:**
- `books` (id, title, author, year, is_borrowed, quantity)
- `members` (id, name, email)
- `borrowed_books` (id, member_id, book_id, borrow_date)

**Migrations:**
- `V1__init_library_schema.sql` - Initial schema + sample data
- `V2__add_quantity_column.sql` - Stock management feature

Migrations run automatically at startup.

---

## 🔧 Technology Stack

| Component | Technology |
|-----------|------------|
| Language | Java 21 |
| Build Tool | Maven |
| Database | PostgreSQL 16 |
| Migration | Flyway 10.10.0 |
| DB Access | JDBC |
| Testing | JUnit 5 + Mockito |
| Packaging | Maven Shade Plugin (Fat JAR) |
| Deployment | Docker Compose |
| i18n | ResourceBundle |

---

## 🚀 Running the Application

### Option A: Docker (Recommended)

**Start everything:**
```bash
docker compose up --build
```

This will:
- Start PostgreSQL database
- Run Flyway migrations
- Launch the application
- Display the interactive menu

**Stop:**
```bash
docker compose down
```

**Interactive mode:**
```bash
docker compose up -d
docker exec -it library-app bash
java -jar app.jar
```

---

### Option B: Local Setup

**1. Build:**
```bash
mvn clean package
```

**2. Setup PostgreSQL:**
- Create database manually
```bash
  CREATE DATABASE library_db
  CREATE USER library_user WITH PASSWORD 'StrongPassword123!';
GRANT ALL PRIVILEGES ON DATABASE library_db TO library_user;
```

- Run migration scripts from `src/main/resources/db/migration/`

**3. Configure:**
Edit `src/main/resources/application.properties`:
```properties
db.url=jdbc:postgresql://localhost:5432/library_db
db.user=library_user
db.password=StrongPassword123!
```

**4. Run:**
```bash
java -jar target/library-management-system-1.0-SNAPSHOT.jar
```

---

## 🧪 Testing

**Run all tests:**
```bash
mvn test
```

**Test Coverage:**
- Member validation (name, email format)
- Book validation (title, author, year, quantity)
- Borrow constraints (3-book limit, stock availability)
- Return operations
- Search functionality
- Error handling scenarios

---

## 📋 Menu Options

```
1 - Add book
2 - Add member
3 - Borrow book
4 - Return book
5 - List books borrowed by member
6 - List all books
7 - Search books (by title or author)
8 - List all members
0 - Exit
```

---

## 📝 Delivery Checklist

✅ **All core requirements implemented**  
✅ **All bonus features implemented**  
✅ **GitHub repository with complete source code**  
✅ **README with detailed instructions (EN/DE/TR)**  
✅ **SQL scripts for database setup (Flyway migrations)**  
✅ **Sample data included**  
✅ **Unit tests with JUnit 5 + Mockito**  
✅ **Docker Compose for one-command deployment**  
✅ **Production-ready code structure**

---
