# 📚 Bibliotheksverwaltungssystem (Java Konsolenanwendung)

[🇬🇧 English Documentation](README.md)  
[🇹🇷 Türkçe Doküman](README.tr.md)

---

## 🎯 Projektübersicht

Dieses Projekt ist ein **konsolenbasiertes Bibliotheksverwaltungssystem**, das als Teil einer Java-Entwickler-Fallstudie entwickelt wurde.  
Es demonstriert professionelle Java-Entwicklungsfähigkeiten mit sauberer Architektur, Datenbankintegration und modernen Entwicklungspraktiken.

**Hauptmerkmale:**
- Saubere **Schichtenarchitektur** (Model → Repository → Service → Controller)
- **JDBC** Datenbankintegration mit PostgreSQL
- **Flyway** Datenbank-Migrationen
- **Internationalisierung (i18n)** Unterstützung (DE/EN/TR)
- **Unit-Tests** mit JUnit 5 + Mockito
- **Docker Compose** für automatisierte Bereitstellung
- Transaktionssichere Operationen
- **Lagerverwaltung** mit Mengenverfolgung
- **Erweiterte Suchfunktion** (teilweise Schlüsselwortübereinstimmung)

---

## 🏗️ Architektur

```
src/
└── main/java/com/lucadron
    ├── model/       # Domain-Klassen (Book, Member, BorrowedBook)
    ├── repository/  # Datenzugriffsschicht (JDBC)
    ├── service/     # Geschäftslogik & Validierung
    ├── controller/  # Konsolen-UI
    ├── i18n/        # Sprachverwaltung
    └── Main.java    # Anwendungseinstiegspunkt
```

---

## ✅ Anforderungserfüllung

### Kernfunktionen ✓
- ✅ **Buch hinzufügen**: Neue Bucheinträge mit Lagerbestand erstellen
- ✅ **Mitglied hinzufügen**: Bibliotheksmitglieder mit Validierung registrieren
- ✅ **Buch ausleihen**: Transaktionssicheres Ausleihen mit Lagerverwaltung
- ✅ **Buch zurückgeben**: Transaktionssichere Rückgaben mit Lageraktualisierung
- ✅ **Mitgliedsbücher auflisten**: Ausgeliehene Bücher mit Mitglieds- und Buchnamen anzeigen
- ✅ **Alle Bücher auflisten**: Vollständiges Inventar mit Verfügbarkeitsstatus

### Bonus-Funktionen ✓
- ✅ **Teilsuche**: Bücher nach Titel oder Autor suchen (Groß-/Kleinschreibung unabhängig)
- ✅ **Eingabevalidierung**: Umfassende Validierung für alle Eingaben
- ✅ **3-Bücher-Limit**: Mitglieder können maximal 3 Bücher gleichzeitig ausleihen
- ✅ **Lagerkontrolle**: Fehlerbehandlung für nicht verfügbare Bücher
- ✅ **Unit-Tests**: 10 Testfälle zur Abdeckung von Validierung, Geschäftsregeln und Fehlerszenarien

### Zusätzliche Verbesserungen ✓
- ✅ **Lagerverwaltung**: Mehrere Exemplare pro Buch mit Mengenverfolgung
- ✅ **Alle Mitglieder auflisten**: Alle registrierten Mitglieder anzeigen
- ✅ **Verbesserte Ausgabe**: Lesbare Details ausgeliehener Bücher mit Namen
- ✅ **Mehrsprachige Unterstützung**: Vollständige i18n-Implementierung (DE/EN/TR)
- ✅ **Docker-Unterstützung**: Containerisierte Bereitstellung mit PostgreSQL

---

## 🌐 Internationalisierung

Das System unterstützt **3 Sprachen** mit vollständigen Übersetzungen:

```
Select language / Sprache auswählen / Dil seçiniz:
1 - English
2 - Deutsch
3 - Türkçe
```

Sprachdateien: `src/main/resources/messages_{en|de|tr}.properties`

---

## 🗄️ Datenbank

**PostgreSQL** Datenbank verwaltet durch **Flyway** Migrationen:

**Tabellen:**
- `books` (id, title, author, year, is_borrowed, quantity)
- `members` (id, name, email)
- `borrowed_books` (id, member_id, book_id, borrow_date)

**Migrationen:**
- `V1__init_library_schema.sql` - Initiales Schema + Beispieldaten
- `V2__add_quantity_column.sql` - Lagerverwaltungsfunktion

Migrationen laufen automatisch beim Start.

---

## 🔧 Technologie-Stack

| Komponente | Technologie |
|------------|-------------|
| Sprache | Java 21 |
| Build-Tool | Maven |
| Datenbank | PostgreSQL 16 |
| Migration | Flyway 10.10.0 |
| DB-Zugriff | JDBC |
| Testing | JUnit 5 + Mockito |
| Packaging | Maven Shade Plugin (Fat JAR) |
| Bereitstellung | Docker Compose |
| i18n | ResourceBundle |

---

## 🚀 Anwendung ausführen

### Option A: Docker (Empfohlen)

**Alles starten:**
```bash
docker compose up --build
```

Dies wird:
- PostgreSQL-Datenbank starten
- Flyway-Migrationen ausführen
- Anwendung starten
- Interaktives Menü anzeigen

**Stoppen:**
```bash
docker compose down
```

**Interaktiver Modus:**
```bash
docker compose up -d
docker exec -it library-app bash
java -jar app.jar
```

---

### Option B: Lokales Setup

**1. Build:**
```bash
mvn clean package
```

**2. PostgreSQL einrichten:**
- Datenbank manuell erstellen

```bash
CREATE DATABASE library_db
CREATE USER library_user WITH PASSWORD 'StrongPassword123!';
GRANT ALL PRIVILEGES ON DATABASE library_db TO library_user;
```

- Migrationsskripte aus `src/main/resources/db/migration/` ausführen

**3. Konfigurieren:**
`src/main/resources/application.properties` bearbeiten:
```properties
db.url=jdbc:postgresql://localhost:5432/library_db
db.user=library_user
db.password=StrongPassword123!
```

**4. Ausführen:**
```bash
java -jar target/library-management-system-1.0-SNAPSHOT.jar
```

---

## 🧪 Testing

**Alle Tests ausführen:**
```bash
mvn test
```

**Testabdeckung:**
- Mitgliedsvalidierung (Name, E-Mail-Format)
- Buchvalidierung (Titel, Autor, Jahr, Menge)
- Ausleihbeschränkungen (3-Bücher-Limit, Lagerverfügbarkeit)
- Rückgabeoperationen
- Suchfunktionalität
- Fehlerbehandlungsszenarien

---

## 📋 Menüoptionen

```
1 - Buch hinzufügen
2 - Mitglied hinzufügen
3 - Buch ausleihen
4 - Buch zurückgeben
5 - Von Mitglied ausgeliehene Bücher auflisten
6 - Alle Bücher auflisten
7 - Bücher suchen (nach Titel oder Autor)
8 - Alle Mitglieder auflisten
0 - Beenden
```

---

## 📝 Lieferungs-Checkliste

✅ **Alle Kernanforderungen implementiert**  
✅ **Alle Bonus-Funktionen implementiert**  
✅ **GitHub-Repository mit vollständigem Quellcode**  
✅ **README mit detaillierten Anweisungen (DE/EN/TR)**  
✅ **SQL-Skripte für Datenbank-Setup (Flyway-Migrationen)**  
✅ **Beispieldaten enthalten**  
✅ **Unit-Tests mit JUnit 5 + Mockito**  
✅ **Docker Compose für Ein-Befehl-Bereitstellung**  
✅ **Produktionsreife Code-Struktur**

---
