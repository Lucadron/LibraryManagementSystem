# 📚 Bibliotheksverwaltungssystem (Java Konsolenanwendung)

[🇬🇧 English Version](README.md)  
[🇹🇷 Türkçe Doküman](README.tr.md)

---

## 🎯 Projektübersicht

Dieses Projekt ist ein **konsolenbasiertes Bibliotheksverwaltungssystem**, entwickelt für eine Java Developer Fallstudie.

Hauptmerkmale:

- Saubere **mehrschichtige Architektur**
- **JDBC + PostgreSQL** Integration
- **Internationalisierung (EN/DE/TR)**
- **JUnit 5 + Mockito** Unit-Tests
- Sichere Transaktionen (Ausleihen / Zurückgeben)
- Produktionsreife Projektstruktur

---

## 🏗️ Architektur

```
src/
 └── main/java/com/lucadron
     ├── model/           # POJOs
     ├── repository/      # JDBC Datenzugriffsschicht
     ├── service/         # Geschäftslogik
     ├── controller/      # Konsolenmenü und UI
     ├── i18n/            # Sprachverwaltung
     └── Main.java        # Einstiegspunkt
```

---

## 🌐 Internationalisierung (i18n)

Unterstützte Sprachen:

- 🇬🇧 Englisch (Standard)
- 🇩🇪 Deutsch
- 🇹🇷 Türkisch

Startdialog:

```
Select language / Sprache auswählen / Dil seçiniz:
1 - English
2 - Deutsch
3 - Türkçe
```

Sprachdateien befinden sich unter:

```
src/main/resources/messages_en.properties
src/main/resources/messages_de.properties
src/main/resources/messages_tr.properties
```

---

## 🗄️ Datenbank (PostgreSQL)

SQL-Skript:

```
database/init.sql
```

Erstellt Tabellen:

- `books`
- `members`
- `borrowed_books`

Beispieldaten werden eingefügt.

---

## 🔧 Technologien

| Komponente | Technologie |
|------------|-------------|
| Sprache | Java 21 |
| Build Tool | Maven |
| Datenbank | PostgreSQL |
| Zugriff | JDBC |
| Tests | JUnit 5 + Mockito |
| Packaging | Maven Shade Plugin |
| i18n | ResourceBundle |

---

## 🧪 Unit Tests

Enthalten sind **5 Tests**, die folgende Fälle prüfen:

- Validierung  
- Maximale Anzahl ausgeliehener Bücher  
- Bereits ausgeliehenes Buch  
- Repository-Interaktionen (Mocking)  

Ausführen:

```
mvn test
```

---

## 🚀 Anwendung starten

### Build:

```bash
mvn clean package
```

### Start:

```bash
java -jar target/library-management-system-1.0-SNAPSHOT.jar
```

---
