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
- Transaction-safe operations (borrow/return)
- Professional code structure suitable for production

---

## 🏗️ Architecture

src/
└── main/java/com/lucadron
├── model/ # POJO classes (Book, Member, BorrowedBook)
├── repository/ # JDBC repositories (CRUD + queries)
├── service/ # Business logic (validation, rules)
├── controller/ # Console UI (menus, prompts)
├── i18n/ # LanguageManager + locale handling
└── Main.java # Application entry point

---

## 🌐 Internationalization (i18n)

The system supports **3 languages**:

- 🇬🇧 English (default)
- 🇩🇪 German
- 🇹🇷 Turkish

Upon startup:

Select language / Sprache auswählen / Dil seçiniz:
1 - English
2 - Deutsch
3 - Türkçe

Translations are stored under:

src/main/resources/messages_en.properties
src/main/resources/messages_de.properties
src/main/resources/messages_tr.properties


---

## 🗄️ Database Setup (PostgreSQL)

Run the following SQL script:

database/init.sql


Tables created:

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
| DB Access | JDBC |
| Testing | JUnit 5 + Mockito |
| Packaging | Maven Shade Plugin (fat jar) |
| i18n | ResourceBundle |

---

## 🧪 Unit Tests

The project includes **5 meaningful test cases** covering:

- Validation rules  
- Borrowing constraints  
- Error handling  
- Repository interaction (mocked)  

Test runner:

```
mvn test
```

---

## 🚀 Running the Application

### Build:

```bash
mvn clean package
```

### Run:

```bash
java -jar target/library-management-system-1.0-SNAPSHOT.jar
```

