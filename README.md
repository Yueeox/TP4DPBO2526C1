# TP4DPBO2526C1
Tugas Praktikum 4 DPBO kelas C1 Java Swing using intelliJ IDEA 2026.2.3
# Janji
Saya Wingko Prajna dengan NIM 2503358 mengerjakan TP 4 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

# Desain .form
<img src="DOKUMENTASI/desain.png" width=700px>

# Penjelasan dan Alur Program `DataMahasiswa`

## 1. Gambaran Umum

Program **DataMahasiswa** adalah aplikasi desktop berbasis **Java Swing** yang berfungsi untuk mengelola data mahasiswa dengan operasi **CRUD** (Create, Read, Update, Delete). Program ini dibangun menggunakan pola **MVC sederhana**:

| Komponen | File | Peran |
|---|---|---|
| **Model** | `Mahasiswa.java` | Menyimpan struktur data satu mahasiswa |
| **View** | `MahasiswaForm.java` + `MahasiswaForm.form` | Tampilan GUI (form input, tabel, tombol) |
| **Controller + Main** | `DataMahasiswa.java` | Logika CRUD, listener, dan entry point program |

Data disimpan **sementara di memori** menggunakan `ArrayList<Mahasiswa>`, sehingga data akan hilang saat program ditutup (belum ada penyimpanan ke database/file).

---

## 2. Struktur Class

### 2.1 `Mahasiswa.java` — Model

Menyimpan atribut satu mahasiswa:

| Atribut | Tipe | Keterangan |
|---|---|---|
| `nim` | `String` | Nomor Induk Mahasiswa |
| `nama` | `String` | Nama lengkap |
| `ttl` | `String` | Tanggal lahir (format bebas, mis. `dd-MM-yyyy`) |
| `prodi` | `String` | Program studi |
| `status` | `StatusMahasiswa` | Enum: `AKTIF`, `CUTI`, `TIDAK_ADA_KETERANGAN`, `LULUS` |
| `email` | `String` | Alamat email |

Dilengkapi **constructor** dan **getter/setter** untuk semua atribut.

### 2.2 `MahasiswaForm.java` — View

Mendeklarasikan komponen GUI yang terhubung dengan file `MahasiswaForm.form`:

- **Input**: `nimInput`, `namaInput`, `ttlInput`, `prodiInput`, `emailInput`, `statusComboBox`
- **Tombol**: `addButton`, `updateButton`, `deleteButton`, `cancelButton`
- **Tabel**: `mahasiswaTabel` (dibungkus `mahasiswaScrollPane`)
- **Panel root**: `mainPanel`

### 2.3 `DataMahasiswa.java` — Controller

Mengandung:

- `ArrayList<Mahasiswa> listMahasiswa` → penyimpanan data
- `int selectedIndex` → indeks baris yang dipilih di tabel (`-1` = tidak ada)
- Method CRUD: `insertData()`, `updateData()`, `deleteData()`, `clearForm()`
- Method bantu: `populateList()`, `setTable()`
- `main()` → menjalankan window

---

## 3. Alur Program

### 3.1 Inisialisasi (saat program dijalankan)

```
main()
  └── new DataMahasiswa()
        ├── form = new MahasiswaForm()
        ├── listMahasiswa = new ArrayList<>()
        ├── populateList()          → isi 3 data awal
        ├── setTable()              → pasang model ke mahasiswaTabel
        ├── statusComboBox.setModel(...) → isi enum StatusMahasiswa
        ├── updateButton & deleteButton disembunyikan
        └── pasang semua listener (Add, Update, Delete, Cancel, Mouse)
```

Setelah constructor selesai, `main()` menampilkan window:

```java
window.setContentPane(window.form.getMainPanel());
window.setSize(900, 600);
window.setLocationRelativeTo(null);
window.setVisible(true);
```

---

### 3.2 Operasi **Create** (Tambah Data)

```
User mengisi form → klik tombol Add
  └── addButton.actionPerformed()
        └── insertData()
              ├── Ambil nilai dari semua input
              ├── Validasi: NIM & Nama tidak boleh kosong
              ├── listMahasiswa.add(new Mahasiswa(...))
              ├── mahasiswaTabel.setModel(setTable())   → refresh tabel
              ├── clearForm()                            → kosongkan form
              └── Tampilkan pesan "Data berhasil ditambahkan"
```

---

### 3.3 Operasi **Read** (Menampilkan & Memilih Data)

**Menampilkan data ke tabel:**

```
setTable()
  ├── Buat DefaultTableModel dengan kolom:
  │     No | NIM | Nama | TTL | Prodi | Status | Email
  ├── isCellEditable() → false (tabel read-only)
  └── Loop listMahasiswa → tambahkan setiap baris ke model
```

**Memilih baris tabel:**

```
User klik baris di mahasiswaTabel
  └── mousePressed()
        ├── selectedIndex = getSelectedRow()
        ├── Ambil objek Mahasiswa dari listMahasiswa
        ├── Isi semua input dengan data mahasiswa tersebut
        ├── statusComboBox.setSelectedItem(status)
        └── Tampilkan tombol updateButton & deleteButton
```

---

### 3.4 Operasi **Update** (Ubah Data)

```
User memilih baris → ubah data → klik tombol Update
  └── updateButton.actionPerformed()
        └── updateData()
              ├── Guard: jika selectedIndex < 0, batalkan
              ├── Ambil nilai dari input
              ├── Validasi NIM & Nama
              ├── Ambil objek Mahasiswa ke-selectedIndex
              ├── Set semua atribut dengan nilai baru
              ├── mahasiswaTabel.setModel(setTable())   → refresh
              ├── clearForm()
              └── Pesan "Data berhasil diubah"
```

---

### 3.5 Operasi **Delete** (Hapus Data)

```
User memilih baris → klik tombol Delete
  └── deleteButton.actionPerformed()
        ├── JOptionPane.showConfirmDialog(...)   → konfirmasi
        └── Jika YES:
              └── deleteData()
                    ├── Guard: selectedIndex valid
                    ├── listMahasiswa.remove(selectedIndex)
                    ├── mahasiswaTabel.setModel(setTable())  → refresh
                    ├── clearForm()
                    └── Pesan "Data berhasil dihapus"
```

---

### 3.6 Operasi **Cancel** (Bersihkan Form)

```
User klik tombol Cancel
  └── cancelButton.actionPerformed()
        └── clearForm()
              ├── Kosongkan semua JTextField
              ├── statusComboBox.setSelectedIndex(0)
              ├── Sembunyikan updateButton & deleteButton
              └── selectedIndex = -1
```

---

## 4. Diagram Alur Sederhana

```
┌─────────────────────────────────────────────────────────────┐
│                        DataMahasiswa                        │
│                                                             │
│  ┌───────────────┐        ┌──────────────────────────────┐  │
│  │  Input Form   │        │        JTable Tabel          │  │
│  │  (View)       │        │  No│NIM│Nama│TTL│Prodi│...   │  │
│  └───────┬───────┘        └──────────────┬───────────────┘  │
│          │                               │                  │
│          ▼                               ▼                  │
│  ┌──────────────────────────────────────────────────────┐   │
│  │              Controller (Listener)                   │   │
│  │  Add → insertData()                                  │   │
│  │  Update → updateData()                               │   │
│  │  Delete → konfirmasi → deleteData()                  │   │
│  │  Cancel → clearForm()                                │   │
│  │  Klik Tabel → isi form + tampilkan tombol            │   │
│  └──────────────────────┬───────────────────────────────┘   │
│                         │                                   │
│                         ▼                                   │
│              ┌─────────────────────┐                        │
│              │ ArrayList<Mahasiswa>│  ← penyimpanan data    │
│              └─────────────────────┘                        │
└─────────────────────────────────────────────────────────────┘
```

---

## 5. Aturan Validasi & State

| Aturan | Implementasi |
|---|---|
| NIM & Nama wajib diisi | `if (nim.isEmpty() \|\| nama.isEmpty())` → tampilkan error |
| Tabel read-only | `isCellEditable()` → `false` |
| Tombol Update & Delete hanya muncul saat ada baris dipilih | `setVisible(false)` di awal & `clearForm()`, `setVisible(true)` di `mousePressed()` |
| `selectedIndex` = `-1` berarti tidak ada baris dipilih | Di-set di `clearForm()` dan dicek di `updateData()` |
| Konfirmasi sebelum hapus | `JOptionPane.showConfirmDialog(...)` di listener `deleteButton` |

---

# Dokumentasi
<video src="DOKUMENTASI/DokumentasiTP4.mp4" controls width="100%"></video>
