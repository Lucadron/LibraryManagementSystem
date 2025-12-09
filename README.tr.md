# 📚 Kütüphane Yönetim Sistemi (Java Konsol Uygulaması)

[🇬🇧 English Documentation](README.md)  
[🇩🇪 Deutsche Dokumentation](README.de.md)

---

## 🎯 Proje Hakkında

Bu proje, bir Java geliştirici vaka çalışması kapsamında geliştirilmiş **konsol tabanlı Kütüphane Yönetim Sistemi**'dir.  
Profesyonel Java geliştirme yeteneklerini temiz mimari, veritabanı entegrasyonu ve modern geliştirme pratikleriyle sergiler.

**Öne Çıkan Özellikler:**
- Temiz **katmanlı mimari** (Model → Repository → Service → Controller)
- PostgreSQL ile **JDBC** veritabanı entegrasyonu
- **Flyway** veritabanı migrasyonları
- **Uluslararasılaştırma (i18n)** desteği (TR/EN/DE)
- JUnit 5 + Mockito ile **birim testleri**
- Otomatik dağıtım için **Docker Compose**
- İşlem güvenli operasyonlar
- Miktar takipli **stok yönetimi**
- **Gelişmiş arama** özelliği (kısmi kelime eşleştirme)

---

## 🏗️ Mimari

```
src/
└── main/java/com/lucadron
    ├── model/       # Domain sınıfları (Book, Member, BorrowedBook)
    ├── repository/  # Veri erişim katmanı (JDBC)
    ├── service/     # İş mantığı & doğrulama
    ├── controller/  # Konsol arayüzü
    ├── i18n/        # Dil yönetimi
    └── Main.java    # Uygulama giriş noktası
```

---

## ✅ Gereksinim Karşılama

### Temel Özellikler ✓
- ✅ **Kitap Ekle**: Stok miktarıyla yeni kitap girişi
- ✅ **Üye Ekle**: Doğrulamalı üye kaydı
- ✅ **Kitap Ödünç Al**: Stok yönetimli işlem güvenli ödünç alma
- ✅ **Kitap İade Et**: Stok güncellemeli işlem güvenli iade
- ✅ **Üyenin Kitaplarını Listele**: Üye ve kitap adlarıyla ödünç alınan kitapları görüntüleme
- ✅ **Tüm Kitapları Listele**: Müsaitlik durumuyla tam envanter

### Bonus Özellikler ✓
- ✅ **Kısmi Arama**: Başlık veya yazara göre arama (büyük/küçük harf duyarsız)
- ✅ **Girdi Doğrulama**: Tüm girdiler için kapsamlı doğrulama
- ✅ **3 Kitap Limiti**: Üyeler aynı anda en fazla 3 kitap ödünç alabilir
- ✅ **Stok Kontrolü**: Müsait olmayan kitaplar için hata yönetimi
- ✅ **Birim Testleri**: Doğrulama, iş kuralları ve hata senaryolarını kapsayan 10 test

### Ek Geliştirmeler ✓
- ✅ **Stok Yönetimi**: Miktar takipli kitap başına çoklu kopya
- ✅ **Tüm Üyeleri Listele**: Kayıtlı tüm üyeleri görüntüleme
- ✅ **Geliştirilmiş Çıktı**: İsimlerle okunabilir ödünç alınan kitap detayları
- ✅ **Çoklu Dil Desteği**: Tam i18n implementasyonu (TR/EN/DE)
- ✅ **Docker Desteği**: PostgreSQL ile konteyner tabanlı dağıtım

---

## 🌐 Uluslararasılaştırma

Sistem tam çevirilerle **3 dili** destekler:

```
Select language / Sprache auswählen / Dil seçiniz:
1 - English
2 - Deutsch
3 - Türkçe
```

Dil dosyaları: `src/main/resources/messages_{en|de|tr}.properties`

---

## 🗄️ Veritabanı

**Flyway** migrasyonlarıyla yönetilen **PostgreSQL** veritabanı:

**Tablolar:**
- `books` (id, title, author, year, is_borrowed, quantity)
- `members` (id, name, email)
- `borrowed_books` (id, member_id, book_id, borrow_date)

**Migrasyonlar:**
- `V1__init_library_schema.sql` - İlk şema + örnek veri
- `V2__add_quantity_column.sql` - Stok yönetimi özelliği

Migrasyonlar başlangıçta otomatik çalışır.

---

## 🔧 Teknoloji Yığını

| Bileşen | Teknoloji |
|---------|-----------|
| Dil | Java 21 |
| Derleme Aracı | Maven |
| Veritabanı | PostgreSQL 16 |
| Migrasyon | Flyway 10.10.0 |
| DB Erişimi | JDBC |
| Test | JUnit 5 + Mockito |
| Paketleme | Maven Shade Plugin (Fat JAR) |
| Dağıtım | Docker Compose |
| i18n | ResourceBundle |

---

## 🚀 Uygulamayı Çalıştırma

### Seçenek A: Docker (Önerilen)

**Her şeyi başlat:**
```bash
docker compose up --build
```

Bu işlem:
- PostgreSQL veritabanını başlatır
- Flyway migrasyonlarını çalıştırır
- Uygulamayı başlatır
- Etkileşimli menüyü gösterir

**Durdur:**
```bash
docker compose down
```

**Etkileşimli mod:**
```bash
docker compose up -d
docker exec -it library-app bash
java -jar app.jar
```

---

### Seçenek B: Yerel Kurulum

**1. Derle:**
```bash
mvn clean package
```

**2. PostgreSQL Kur:**
- Veritabanını manuel oluştur

 ```bash
CREATE DATABASE library_db
CREATE USER library_user WITH PASSWORD 'StrongPassword123!';
GRANT ALL PRIVILEGES ON DATABASE library_db TO library_user;
```

- `src/main/resources/db/migration/` klasöründeki migrasyon scriptlerini çalıştır

**3. Yapılandır:**
`src/main/resources/application.properties` dosyasını düzenle:
```properties
db.url=jdbc:postgresql://localhost:5432/library_db
db.user=library_user
db.password=StrongPassword123!
```

**4. Çalıştır:**
```bash
java -jar target/library-management-system-1.0-SNAPSHOT.jar
```

---

## 🧪 Test

**Tüm testleri çalıştır:**
```bash
mvn test
```

**Test Kapsamı:**
- Üye doğrulama (isim, email formatı)
- Kitap doğrulama (başlık, yazar, yıl, miktar)
- Ödünç alma kısıtlamaları (3 kitap limiti, stok müsaitliği)
- İade işlemleri
- Arama fonksiyonu
- Hata yönetimi senaryoları

---

## 📋 Menü Seçenekleri

```
1 - Kitap ekle
2 - Üye ekle
3 - Kitap ödünç al
4 - Kitap iade et
5 - Üyenin ödünç aldığı kitapları listele
6 - Tüm kitapları listele
7 - Kitap ara (başlık veya yazara göre)
8 - Tüm üyeleri listele
0 - Çıkış
```

---

## 📝 Teslimat Kontrol Listesi

✅ **Tüm temel gereksinimler karşılandı**  
✅ **Tüm bonus özellikler uygulandı**  
✅ **Tam kaynak kodlu GitHub deposu**  
✅ **Detaylı talimatlarla README (TR/EN/DE)**  
✅ **Veritabanı kurulumu için SQL scriptleri (Flyway migrasyonları)**  
✅ **Örnek veriler dahil**  
✅ **JUnit 5 + Mockito ile birim testleri**  
✅ **Tek komutla dağıtım için Docker Compose**  
✅ **Üretime hazır kod yapısı**

---
