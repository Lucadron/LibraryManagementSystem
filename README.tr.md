# 📚 Kütüphane Yönetim Sistemi (Java Konsol Uygulaması)

[🇬🇧 View in English](README.md)  
[🇩🇪 Deutsche Dokumentation](README.de.md)

---

## 🎯 Proje Özeti

Bu proje, bir Java geliştirici değerlendirme ödevi kapsamında hazırlanan **konsol tabanlı Kütüphane Yönetim Sistemi** uygulamasıdır.

Öne çıkan noktalar:

- Temiz **katmanlı mimari**
- **JDBC + PostgreSQL** veritabanı entegrasyonu
- **3 dilli (EN/DE/TR) i18n** yapısı
- **JUnit 5 + Mockito** ile birim testleri
- Transaction-safe ödünç alma & iade mekanizması
- Üretim kalitesinde kod organizasyonu

---

## 🏗️ Mimari

```
src/
 └── main/java/com/lucadron
     ├── model/           # POJO sınıfları
     ├── repository/      # JDBC veri erişim katmanı
     ├── service/         # İş kuralları ve doğrulama
     ├── controller/      # Konsol menüsü ve UI
     ├── i18n/            # Dil yöneticisi
     └── Main.java        # Uygulama giriş noktası
```

---

## 🌐 Uluslararasılaştırma (i18n)

Uygulama **3 dili destekler**:

- 🇬🇧 İngilizce (varsayılan)
- 🇩🇪 Almanca
- 🇹🇷 Türkçe

Başlangıçta dil seçimi yapılır:

```
Select language / Sprache auswählen / Dil seçiniz:
1 - English
2 - Deutsch
3 - Türkçe
```

Metinler şu dizindedir:

```
src/main/resources/messages_en.properties
src/main/resources/messages_de.properties
src/main/resources/messages_tr.properties
```

---

## 🗄️ Veritabanı (PostgreSQL)

Aşağıdaki script çalıştırılmalıdır:

```
database/init.sql
```

Tablolar:

- `books`
- `members`
- `borrowed_books`

Örnek veriler otomatik eklenir.

---

## 🔧 Kullanılan Teknolojiler

| Katman | Teknoloji |
|--------|-----------|
| Dil | Java 21 |
| Build Tool | Maven |
| Veritabanı | PostgreSQL |
| DB Erişimi | JDBC |
| Test | JUnit 5 + Mockito |
| Paketleme | Maven Shade Plugin |
| i18n | ResourceBundle |

---

## 🧪 Birim Testleri

Toplam **5 test**, şu senaryoları kapsar:

- Hatalı giriş doğrulama  
- Ödünç alma limit kontrolü  
- Zaten ödünç alınmış kitap  
- Repository etkileşimleri (mock)  

Çalıştırmak için:

```
mvn test
```

---

## 🚀 Uygulamanın Çalıştırılması

### Derleme:

```bash
mvn clean package
```

### Çalıştırma:

```bash
java -jar target/library-management-system-1.0-SNAPSHOT.jar
```

---

