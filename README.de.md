# 📚 Bibliotheksverwaltungssystem (Java Konsolenanwendung)

[🇬🇧 English Documentation](README.md)  
[🇹🇷 Türkçe Doküman](README.tr.md)

---

## 🎯 Projektübersicht

Dieses Projekt ist ein **konsolenbasiertes Bibliotheksverwaltungssystem**, das als Teil einer Java-Entwickler-Fallstudie entwickelt wurde.  
Es demonstriert:

- Saubere **Schichtenarchitektur**
- **JDBC** Datenbankintegration (PostgreSQL)
- **Internationalisierung (i18n)** Unterstützung (DE/EN/TR)
- **Unit-Tests** mit JUnit 5 + Mockito
- **Flyway-Migrationen** für Datenbank-Schema-Verwaltung
- **Docker Compose** für vollautomatisches Setup
- Transaktionssichere Ausleihe-/Rückgabe-Operationen
- Professionelle, produktionsreife Codestruktur

---

## 🏗️ Architektur

```
src/
└── main/java/com/lucadron
    ├── model/       # POJO-Klassen (Book, Member, BorrowedBook)
    ├── repository/  # JDBC-Repositories (CRUD + Abfragen)
    ├── service/     # Geschäftslogik (Validierung, Regeln)
    ├── controller/  # Konsolen-UI (Menüs, Eingabeaufforderungen)
    ├── i18n/        # LanguageManager + Locale-Verwaltung
    └── Main.java    # Anwendungseinstiegspunkt
```

---

## ✅ Projektanforderungen & Implementierungsstatus

### Kernanforderungen (✓ Abgeschlossen)
- ✅ **Domain-Klassen**: Book, Member mit Konstruktoren, Gettern/Settern, toString
- ✅ **Buch hinzufügen**: Neue Bucheinträge erstellen
- ✅ **Mitglied hinzufügen**: Neue Bibliotheksmitglieder registrieren
- ✅ **Buch ausleihen**: Transaktionssicheres Ausleihsystem
- ✅ **Buch zurückgeben**: Transaktionssicheres Rückgabesystem
- ✅ **Mitgliedsbücher auflisten**: Alle von einem bestimmten Mitglied ausgeliehenen Bücher anzeigen
- ✅ **Alle Bücher auflisten**: Vollständiges Bücherinventar anzeigen

### Datenbankanforderungen (✓ Abgeschlossen)
- ✅ **PostgreSQL-Datenbank**: Vollständige JDBC-Integration
- ✅ **Tabellen**: `books`, `members`, `borrowed_books`
- ✅ **Flyway-Migrationen**: Automatische Schema-Verwaltung
- ✅ **Beispieldaten**: Vorinstallierte Testdaten enthalten

### Architekturanforderungen (✓ Abgeschlossen)
- ✅ **Schichtenarchitektur**: model → repository → service → controller
- ✅ **Trennung der Zuständigkeiten**: Klare Schichtgrenzen
- ✅ **Professionelle Struktur**: Produktionsreife Code-Organisation

### Bonus-Funktionen (✓ Implementiert)
- ✅ **Eingabevalidierung**: Umfassende Validierungsregeln
- ✅ **3-Bücher-Limit**: Mitglieder können maximal 3 Bücher gleichzeitig ausleihen
- ✅ **Fehlerbehandlung**: Bereits ausgeliehene Bücher können nicht erneut ausgeliehen werden
- ✅ **Unit-Tests**: JUnit 5 + Mockito Testabdeckung
- ✅ **Internationalisierung**: Mehrsprachige Unterstützung (DE/EN/TR)
- ✅ **Docker-Unterstützung**: Containerisierte Bereitstellung
- ⚠️ **Teilsuche**: *In dieser Version nicht implementiert*

---

## 🌐 Internationalisierung (i18n)

Das System unterstützt **3 Sprachen**:

- 🇩🇪 Deutsch
- 🇬🇧 Englisch
- 🇹🇷 Türkisch

Beim Start:

```
Select language / Sprache auswählen / Dil seçiniz:
1 - English
2 - Deutsch
3 - Türkçe
```

Übersetzungen sind gespeichert unter:

```
src/main/resources/messages_en.properties
src/main/resources/messages_de.properties
src/main/resources/messages_tr.properties
```

---

## 🗄️ Datenbank & Migration

Die Datenbank wird von **Flyway** verwaltet und Migrationen laufen automatisch beim Anwendungsstart.

Migrationsdateien:

```
src/main/resources/db/migration/V1__init_library_schema.sql
```

Dies erstellt:

- `books`
- `members`
- `borrowed_books`

Beispieldaten werden automatisch eingefügt.

---

## 🔧 Technologie-Stack

| Schicht | Technologie |
|---------|-------------|
| Sprache | Java 21 |
| Build-Tool | Maven |
| Datenbank | PostgreSQL |
| Migration | Flyway |
| DB-Zugriff | JDBC |
| Testing | JUnit 5 + Mockito |
| Packaging | Maven Shade Plugin (Fat JAR) |
| Laufzeit | Docker Compose |
| i18n | ResourceBundle |

---

## 🚀 Anwendung ausführen

### OPTION A — Mit Docker ausführen (empfohlen für Tester)

#### 1️⃣ Container erstellen & starten

```bash
docker compose up --build
```

Docker wird:
- PostgreSQL starten
- Flyway-Migration ausführen
- Anwendung innerhalb eines Containers starten
- Konsolenmenü anzeigen

Zum Stoppen:

```bash
docker compose down
```

Interaktiv im App-Container ausführen:

```bash
docker compose up -d
docker exec -it library-app bash
java -jar app.jar
```

---

### OPTION B — Lokal ausführen (ohne Docker)

#### 1️⃣ JAR erstellen

```bash
mvn clean package
```

Dies erzeugt:

```
target/library-management-system-1.0-SNAPSHOT.jar
```

#### 2️⃣ PostgreSQL manuell starten

Führen Sie `database/CreateDatabase.sql` in Ihrer lokalen PostgreSQL-Instanz aus.

Dann:

```bash
java -jar target/library-management-system-1.0-SNAPSHOT.jar
```

---

## 🧪 Unit-Tests

Das Projekt enthält **5 aussagekräftige Testfälle**, die Folgendes abdecken:

- Validierungsregeln
- Ausleihbeschränkungen (3-Bücher-Limit)
- Fehlerbehandlung (bereits ausgeliehene Bücher)
- Repository-Interaktion (gemockt)

Test-Runner:

```bash
mvn test
```

---

## 📌 Docker-Dienste

`docker-compose.yml` definiert:

- **library-postgres** → PostgreSQL 16
- **library-app** → Java-Konsolenanwendung

Umgebungsvariablen überschreiben die Standard-DB-Konfiguration.

---

## 📋 Lieferanforderungen

✅ **Abgeschlossene Checkliste:**
- ✅ Projekt auf GitHub geteilt
- ✅ README mit detaillierten Anweisungen
- ✅ SQL-Skript für Datenbanktabellen (Flyway-Migrationen)
- ✅ Beispiel-Anfangsdaten enthalten
- ✅ Unit-Tests implementiert (Bonus-Funktion)
- ✅ Eingabevalidierung (Bonus-Funktion)
- ✅ 3-Bücher-Ausleihlimit (Bonus-Funktion)
- ✅ Fehlermeldungen für bereits ausgeliehene Bücher (Bonus-Funktion)
- ✅ Mehrsprachige Unterstützung (Zusatzfunktion)
- ✅ Docker-Containerisierung (Zusatzfunktion)

---

## 🎁 Zusätzliche Funktionen über die Anforderungen hinaus

Diese Implementierung übertrifft die Grundanforderungen mit:

- **Internationalisierung (i18n)**: Vollständige mehrsprachige Unterstützung
- **Docker-Integration**: Bereitstellung mit einem Befehl
- **Flyway-Migrationen**: Professionelle Datenbankversionierung
- **Fat JAR-Packaging**: Eigenständige ausführbare Datei
- **Produktionsreife Struktur**: Code-Organisation auf Unternehmensniveau
- **Umfassende Tests**: Getestete Repository-Schicht mit Mocks

---

## 📝 Hinweise

- **Teilsuche nach Buchtitel** ist die einzige Bonus-Funktion, die in der aktuellen Version nicht implementiert wurde
- Alle Kernanforderungen und die meisten Bonus-Funktionen wurden erfolgreich abgeschlossen
- Das System ist produktionsreif und folgt Java-Best-Practices
