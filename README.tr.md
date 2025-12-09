# 📚 Kütüphane Yönetim Sistemi (Java Konsol Uygulaması)

[🇬🇧 English Documentation](README.md)  
[🇩🇪 Deutsche Dokumentation](README.de.md)

---

## 🎯 Proje Genel Bakış

Bu proje, Java geliştirici vaka çalışmasının bir parçası olarak geliştirilmiş **konsol tabanlı bir Kütüphane Yönetim Sistemi**'dir.  
Şunları gösterir:

- Temiz **katmanlı mimari**
- **JDBC** veritabanı entegrasyonu (PostgreSQL)
- **Uluslararasılaştırma (i18n)** desteği (TR/EN/DE)
- JUnit 5 + Mockito ile **birim testleri**
- **Flyway migrasyonları** ile veritabanı şema yönetimi
- **Docker Compose** ile tamamen otomatik kurulum
- İşlem güvenli ödünç alma/iade işlemleri
- Üretime uygun profesyonel kod yapısı

---

## 🏗️ Mimari

```
src/
└── main/java/com/lucadron
    ├── model/       # POJO sınıfları (Book, Member, BorrowedBook)
    ├── repository/  # JDBC repository'ler (CRUD + sorgular)
    ├── service/     # İş mantığı (doğrulama, kurallar)
    ├── controller/  # Konsol UI (menüler, istemler)
    ├── i18n/        # LanguageManager + yerel ayar yönetimi
    └── Main.java    # Uygulama giriş noktası
```

---

## ✅ Proje Gereksinimleri & Uygulama Durumu

### Temel Gereksinimler (✓ Tamamlandı)
- ✅ **Alan Sınıfları**: Book, Member ile kurucular, getter/setter, toString
- ✅ **Kitap Ekle**: Yeni kitap girişleri oluşturma
- ✅ **Üye Ekle**: Yeni kütüphane üyeleri kaydetme
- ✅ **Kitap Ödünç Al**: İşlem güvenli ödünç alma sistemi
- ✅ **Kitap İade Et**: İşlem güvenli iade sistemi
- ✅ **Üyenin Kitaplarını Listele**: Belirli bir üyenin ödünç aldığı tüm kitapları görüntüleme
- ✅ **Tüm Kitapları Listele**: Tüm kitap envanterini görüntüleme

### Veritabanı Gereksinimleri (✓ Tamamlandı)
- ✅ **PostgreSQL Veritabanı**: Tam JDBC entegrasyonu
- ✅ **Tablolar**: `books`, `members`, `borrowed_books`
- ✅ **Flyway Migrasyonları**: Otomatik şema yönetimi
- ✅ **Örnek Veri**: Önceden yüklenmiş test verileri dahil

### Mimari Gereksinimler (✓ Tamamlandı)
- ✅ **Katmanlı Mimari**: model → repository → service → controller
- ✅ **Endişelerin Ayrılması**: Net katman sınırları
- ✅ **Profesyonel Yapı**: Üretime hazır kod organizasyonu

### Bonus Özellikler (✓ Uygulandı)
- ✅ **Girdi Doğrulama**: Kapsamlı doğrulama kuralları
- ✅ **3 Kitap Limiti**: Üyeler aynı anda en fazla 3 kitap ödünç alabilir
- ✅ **Hata Yönetimi**: Zaten ödünç alınmış kitaplar tekrar ödünç alınamaz
- ✅ **Birim Testleri**: JUnit 5 + Mockito test kapsamı
- ✅ **Uluslararasılaştırma**: Çoklu dil desteği (TR/EN/DE)
- ✅ **Docker Desteği**: Konteyner ile dağıtım
- ⚠️ **Kısmi Arama**: *Bu sürümde uygulanmadı*

---

## 🌐 Uluslararasılaştırma (i18n)

Sistem **3 dili** destekler:

- 🇹🇷 Türkçe
- 🇬🇧 İngilizce
- 🇩🇪 Almanca

Başlangıçta:

```
Select language / Sprache auswählen / Dil seçiniz:
1 - English
2 - Deutsch
3 - Türkçe
```

Çeviriler şu konumda saklanır:

```
src/main/resources/messages_en.properties
src/main/resources/messages_de.properties
src/main/resources/messages_tr.properties
```

---

## 🗄️ Veritabanı & Migrasyon

Veritabanı **Flyway** tarafından yönetilir ve migrasyonlar uygulama başlangıcında otomatik olarak çalışır.

Migrasyon dosyaları:

```
src/main/resources/db/migration/V1__init_library_schema.sql
```

Bu şunları oluşturur:

- `books`
- `members`
- `borrowed_books`

Örnek veriler otomatik olarak eklenir.

---

## 🔧 Teknoloji Yığını

| Katman | Teknoloji |
|--------|-----------|
| Dil | Java 21 |
| Derleme Aracı | Maven |
| Veritabanı | PostgreSQL |
| Migrasyon | Flyway |
| DB Erişim | JDBC |
| Test | JUnit 5 + Mockito |
| Paketleme | Maven Shade Plugin (fat jar) |
| Çalışma Zamanı | Docker Compose |
| i18n | ResourceBundle |

---

## 🚀 Uygulamayı Çalıştırma

### SEÇENEK A — Docker ile Çalıştırma (test edenler için önerilir)

#### 1️⃣ Konteynerleri Derle & Başlat

```bash
docker compose up --build
```

Docker şunları yapacak:
- PostgreSQL'i başlat
- Flyway migrasyonunu çalıştır
- Uygulamayı konteyner içinde başlat
- Konsol menüsünü göster

Durdurmak için:

```bash
docker compose down
```

Uygulama konteyneri içinde etkileşimli çalıştırma:

```bash
docker compose up -d
docker exec -it library-app bash
java -jar app.jar
```

---

### SEÇENEK B — Yerel Olarak Çalıştırma (Docker olmadan)

#### 1️⃣ JAR Derle

```bash
mvn clean package
```

Bu şunu üretir:

```
target/library-management-system-1.0-SNAPSHOT.jar
```

#### 2️⃣ PostgreSQL'i manuel olarak başlat

Yerel PostgreSQL örneğinizde `database/CreateDatabase.sql` dosyasını çalıştırın.

Ardından:

```bash
java -jar target/library-management-system-1.0-SNAPSHOT.jar
```

---

## 🧪 Birim Testleri

Proje şunları kapsayan **5 anlamlı test senaryosu** içerir:

- Doğrulama kuralları
- Ödünç alma kısıtlamaları (3 kitap limiti)
- Hata yönetimi (zaten ödünç alınmış kitaplar)
- Repository etkileşimi (mock'lanmış)

Test çalıştırıcı:

```bash
mvn test
```

---

## 📌 Docker Servisleri

`docker-compose.yml` şunları tanımlar:

- **library-postgres** → PostgreSQL 16
- **library-app** → Java konsol uygulaması

Ortam değişkenleri varsayılan DB yapılandırmasını geçersiz kılar.

---

## 📋 Teslimat Gereksinimleri

✅ **Tamamlanan Kontrol Listesi:**
- ✅ Proje GitHub'da paylaşıldı
- ✅ Detaylı talimatlar içeren README
- ✅ Veritabanı tabloları için SQL betiği (Flyway migrasyonları)
- ✅ Örnek başlangıç verileri dahil
- ✅ Birim testleri uygulandı (bonus özellik)
- ✅ Girdi doğrulama (bonus özellik)
- ✅ 3 kitap ödünç alma limiti (bonus özellik)
- ✅ Zaten ödünç alınmış kitaplar için hata mesajları (bonus özellik)
- ✅ Çoklu dil desteği (ekstra özellik)
- ✅ Docker konteynerizasyonu (ekstra özellik)

---

## 🎁 Gereksinimlerin Ötesinde Ek Özellikler

Bu uygulama temel gereksinimleri şunlarla aşmaktadır:

- **Uluslararasılaştırma (i18n)**: Tam çoklu dil desteği
- **Docker Entegrasyonu**: Tek komutla dağıtım
- **Flyway Migrasyonları**: Profesyonel veritabanı sürümleme
- **Fat JAR Paketleme**: Bağımsız çalıştırılabilir
- **Üretime Hazır Yapı**: Kurumsal seviye kod organizasyonu
- **Kapsamlı Test**: Mock'lanmış repository katmanı testleri

---

## 📝 Notlar

- **Kitap başlığına göre kısmi arama** mevcut sürümde uygulanmayan tek bonus özelliktir
- Tüm temel gereksinimler ve çoğu bonus özellik başarıyla tamamlanmıştır
- Sistem üretime hazırdır ve Java en iyi uygulamalarını takip eder
