# Bank Sederhana Tanpa Bunga

## Latar Belakang
Sebuah kelompok simpan pinjam di sebuah desa dimana sejumlah orang bergabung, dengan
setiap orang bisa menyetorkan dana simpanan, dan kemudian juga melakukan pinjaman.

## Data
Kelompok tsb mempunyai 3 anggota
1. Wawan Setiawan, tgl lahir 10 Januari 1990, beralamat di Kompleks Asia Serasi No 100
2. Teguh Sudibyantoro, tgl lahir 10 Februari 1991, beralamat di Jalan Pemekaran No 99
3. Joko Widodo, tgl lahir 10 Maret 1992, beralamat di Dusun Pisang Rt 10 Rw 20

### Kelompok simpan pinjam tsb melakukan transaksi sbb :
1. Wawan Setiawan menyerahkan dana Rp 1.000.000 pada tanggal 17 Agustus 2020.
2. Teguh Sudibyantoro menyerahkan dana Rp 5.000.000 pada tanggal 18 Agustus 2020.
3. Joko Widodo meminjam dana Rp 2.000.000 pada tanggal 30 September 2020.
4. Joko Widodo mengembalikan dana Rp 1.000.000 pada tanggal 10 November 2020
5. Wawan Setiawan menyerahkan dana Rp 5.000.000 pada tanggal 1 Desember 2020
6. Teguh Sudibyantoro mengambil dana Rp 2.000.000 pada tanggal 1 Desember 2020

## Arsitektur
1. Database PostgreSQL
2. SpringBoot untuk REST API

## Struktur Tabel

Bisa dilihat di sini agar lebih jelas : ![ERD](https://dbdiagram.io/d/6306f8a1f1a9b01b0fdbf2f2)

## HTTP Request Sample

### Menambah anggota di PostgreSQL

<pre>
curl --location --request POST 'localhost/api/v1/members' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Wawan Setiawan",
    "address": "Kompleks Asia Serasi No 100",
    "nik": "33030509087610011",
    "dob": "1990-01-10"
}'

curl --location --request POST 'localhost/api/v1/members' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Teguh Sudibyantoro",
    "address": "Jalan Pemekaran No. 99",
    "nik": "33030509087611111",
    "dob": "1991-02-10"
}'

curl --location --request POST 'localhost/api/v1/members' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Joko Widodo",
    "address": "Dusun Pisang Rt 10 Rw 20",
    "nik": "33030509087611112",
    "dob": "1992-03-10"
}'
</pre>

### Melihat daftar anggota di PostgreSQL

### Melakukan transaksi dengan menyimpan ke PostgreSQL

### Melihat history transaksi antara satu tanggal ke tanggal lain

### Melihat transaksi satu anggota