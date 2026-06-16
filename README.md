# Probabilistic IR Model

Sistem Information Retrieval berbasis model probabilistik yang mengimplementasikan empat model ranking dokumen: 
**BIM**, **BM25**, **BM11**, dan **Two-Poisson**. Sistem ini dibangun menggunakan dataset Cranfield dan 
mendukung dua mode: pencarian query manual dan evaluasi otomatis.

## Cara Menjalankan
1. Clone repo dengan `git clone https://github.com/vandykas/probabilistic-ir-model.git`
2. Buka folder hasil clone di code editor
3. Jalankan file Main yang berada di `src/main/java/`
4. Pilih mode dan k yang digunakan untuk pseudo relevance feedback

### Mode Normal
Mode ini tidak menggunakan query dari cranfield sehingga user dapat memasukkan 
querynya sendiri. Hasil ranking berisi id dokumen dan skor akan ditampilkan untuk
semua model.

### Mode Evaluasi
Mode ini menggunakan query evaluasi dari cranfield. Mode ini bertujuan untuk
mengukur kinerja model berdasarkan precision, recall, precision at k, dan 11 point
interpolated average precision. Query yang tersedia mencapai 225 buah dan user dapat 
menentukan berapa banyak query evaluasi yang ingin digunakan.
