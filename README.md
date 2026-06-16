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

## Model yang Digunakan

### BIM (Binary Independence Model)

Binary Independence Model(BIM) adalah sebuah model probabilistik dasar yang mengasumsikan bahwa dokumen hanya direpresentasikan berdasarkan keberadaan atau ketidakberadaan suatu term. Model ini juga mengasumsikan bahwa kemunculan setiap term bersifat independen terhadap term lainnya. 

Rumus:

rel(D,Q) = Σ wt

Term:

rel(D,Q) : Skor relevansi dokumen terhadap query

wt : Bobot term yang dihitung berdasarkan probabilitas kemunculan term pada dokumen relevan dan tidak relevan

### BM25

BM25 merupakan model probabilistik yang paling banyak digunakan dalam Information Retrieval modern. Model ini mempertimbangkan frekuensi term, kelangkaan term dalam koleksi dokumen, dan panjang dokumen untuk menghasilkan skor relevansi. 

Rumus:

rel(D,Q) = Σ [ ft,D (k + 1) wt ] / [ ft,D + k ((ld / lavg)b + (1 - b)) ]

Term:

rel(D,Q) : Skor relevansi dokumen terhadap query

ft,D : Frekuensi term t dalam dokumen D

k : Parameter term frequency saturation

ld : Panjang dokumen D

lavg : Rata-rata panjang dokumen dalam koleksi dokumen

wt : Bobot term

b : Parameter normalisasi panjang dokumen (biasanya 0.75)

### BM11

BM11 adalah salah satu anggota awal keluarga Best Match dan merupakan kasus khusus dari BM25. BM11 menerapkan normalisasi panjang dokumen secara penuh dengan menetapkan parameter b=1b = 1b=1.

Rumus:

rel(D,Q) = Σ [ ft,D (k + 1) wt ] / [ ft,D + k ]

Term:

rel(D,Q) : Skor relevansi dokumen terhadap query

ft,D : Frekuensi term t dalam dokumen D

k : Parameter term frequency saturation

wt : Bobot term

### Two-Poisson

Two-Poisson Model merupakan model probabilistik yang didasarkan pada asumsi bahwa distribusi kemunculan suatu term dapat dibagi menjadi dua kelompok, dokumen relevan dan dokumen tidak relevan. 

Rumus:

rel(D,Q) = Σ [ ft,D (k + 1) wt ] / [ ft,D + k ]

Term:

rel(D,Q) : Skor relevansi dokumen terhadap query

ft,D : Frekuensi term t dalam dokumen D

k : Parameter term frequency saturation

wt : Bobot term

