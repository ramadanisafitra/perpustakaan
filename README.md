# TechnicalTest Perpustakaan
`Proyek Perpustakaan adalah sebuah aplikasi sederhana untuk mengelola peminjaman dan pengembalian buku di perpustakaan. Aplikasi ini memungkinkan pelanggan perpustakaan untuk meminjam buku dan mengembalikannya sesuai dengan aturan yang berlaku.`
# Teknologi yang Digunakan
1. Java
2. Spring Boot
3. Spring Data JPA
4. MySQL
# API yang Tersedia
Berikut ini adalah API yang dapat digunakan dalam aplikasi Perpustakaan:
1. **POST /api/books** : Memasukkan data buku baru ke dalam perpustakaan. 
2. **GET /api/books** : Mendapatkan daftar semua data buku yang tersedia di perpustakaan.
3. **GET /api/books/{id}** : Mendapatkan informasi detail buku berdasarkan ID.
4. **PUT /api/books/{id}** : Mengubah informasi buku yang ada berdasarkan ID.
5. **DELETE /api/books/{id}** : Menghapus buku dari perpustakaan berdasarkan ID.
6. **POST /api/customers** : Memasukkan data peminjam buku baru ke dalam perpustakaan.
7. **GET /api/customers** : Mendapatkan daftar semua data peminjam buku perpustakaan.
8. **GET /api/customers/{id}** : Mendapatkan informasi detail peminjam buku berdasarkan ID.
9. **PUT /api/customers/{id}** : Mengubah informasi peminjam buku yang ada berdasarkan ID.
10. **DELETE /api/customers/{id}** : Menghapus peminjam dari perpustakaan berdasarkan ID.
11. **POST /api/booklending** : Meminjam buku dengan memberikan ID buku dan ID peminjam.
12. **POST /api/returnbook** : Mengembalikan buku dengan memberikan ID buku dan ID pelanggan.
13. **GET /api/getborrowerhistorylist** : Mendapatkan daftar semua riwayat peminjaman pelanggan.
# Fungsi /api/bookLending
``Fungsi bookLending digunakan untuk melakukan proses penginputan peminjaman buku oleh Admin. Fungsi ini menerima objek BorrowBookRequest yang berisi informasi yang diperlukan untuk melakukan peminjaman, seperti ID buku yang akan dipinjam, ID peminjam yang melakukan peminjaman, dan tanggal peminjaman.Proses peminjaman buku melibatkan beberapa langkah sebagai berikut:``
1. Mengecek keberadaan buku berdasarkan ID buku yang diberikan dalam permintaan. Jika buku tidak ditemukan, fungsi akan melempar pengecualian NotFoundException dengan pesan "Book not found.". 
2. Mengecek keberadaan peminjam berdasarkan ID peminjam yang diberikan dalam permintaan. Jika peminjam tidak ditemukan, fungsi akan melempar pengecualian NotFoundException dengan pesan "Customer not found.". 
3. Mengecek ketersediaan stok buku. Jika stok buku adalah 0, berarti buku tidak tersedia untuk dipinjam, dan fungsi akan melempar pengecualian IllegalArgumentException dengan pesan "Book is not available for borrowing.". 
4. Mengecek apakah peminjam memenuhi syarat untuk melakukan peminjaman. Jika peminjam sedang meminjam buku lain, fungsi akan melempar pengecualian IllegalArgumentException dengan pesan "Customer is already borrowing a book.". 
5. Membuat objek BorrowerHistory yang merepresentasikan peminjaman buku oleh peminjam. Objek ini akan berisi informasi buku yang dipinjam, peminjam yang melakukan peminjaman, tanggal peminjaman, dan tanggal pengembalian yang dihitung dengan menambahkan 30 hari ke tanggal peminjaman. 
6. Menyimpan objek BorrowerHistory ke dalam repository borrowerHistoryRepository. 
7. Mengurangi stok buku dengan 1. 
8. Menyimpan perubahan stok buku ke dalam repository bookRepository.
# Fungsi /api/returnBook
`Fungsi returnBook digunakan untuk melakukan proses pengembalian buku oleh peminjam. Fungsi ini menerima objek ReturnBookRequest yang berisi informasi yang diperlukan untuk melakukan pengembalian, seperti nomor identitas peminjam dan tanggal pengembalian.Proses pengembalian buku melibatkan beberapa langkah sebagai berikut:
`
1. Mengecek keberadaan peminjam berdasarkan nomor identitas (no KTP) yang diberikan dalam permintaan. Jika perminjam tidak ditemukan, fungsi akan melempar pengecualian NotFoundException dengan pesan "Customer not found.". 
2. Mengambil semua catatan peminjaman peminjam dari repository borrowerHistoryRepository. 
3. Jika tidak ada catatan peminjaman yang ditemukan, fungsi akan melempar pengecualian NotFoundException dengan pesan "No borrow records found for the customer.". 
4. Mengubah tanggal pengembalian yang ada dalam setiap catatan peminjaman dengan tanggal pengembalian yang diberikan dalam permintaan. 
5. Mengubah status isDeleted menjadi true dalam setiap catatan peminjaman yang sesuai dengan tanggal pengembalian yang diberikan. 
6. Menyimpan perubahan catatan peminjaman ke dalam repository borrowerHistoryRepository. 
7. Menambah stok buku dengan 1. 
8. Menyimpan perubahan stok buku ke dalam repository bookRepository. 

Disini akan menampilkan juga status nya terlambat mengembalikan atau tidak. 

    Untuk yang terlambatkan akan mendapatkan response seperti ini 
    {
    "status": "Late",
    "message": "Book is returned late by 1 days."
    }

    Untuk yang tepat waktu atau kurang dari 30 hari response nya seperti ini :
    {
    "status": "On Time",
    "message": "Book is returned on time."
    }
  
# Fungsi endpoint /api/getborrowerhistorylist
` Ini berfungsi untuk mengambil data siapa saja yang meminjam buku ,data yang di ambil hanya data yg belum mengembalikan saja sedangkan untuk yang telah mengembalikan datanya tidak tampilkan karna isDeleted di tabel di set true ketika mengembalikan buku`

