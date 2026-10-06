// Import library Swing untuk komponen GUI
import javax.swing.*;

// Import DefaultTableModel untuk mengatur model JTable
import javax.swing.table.DefaultTableModel;

// Import event listener (ActionListener, MouseAdapter, MouseEvent)
import java.awt.event.*;

// Import ArrayList untuk menyimpan data mahasiswa
import java.util.ArrayList;

// Class utama program, extends JFrame agar bisa menjadi window
public class DataMahasiswa extends JFrame {

    // Objek form yang berisi semua komponen GUI
    private MahasiswaForm form;

    // List untuk menyimpan data mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;

    // Index baris tabel yang sedang dipilih (-1 = tidak ada)
    private int selectedIndex = -1;

    // Constructor: dijalankan saat objek DataMahasiswa dibuat
    public DataMahasiswa() {
        // Inisialisasi form
        form = new MahasiswaForm();

        // Inisialisasi list kosong
        listMahasiswa = new ArrayList<>();

        // Isi data awal ke dalam list
        populateList();

        // Pasang model tabel ke JTable
        form.mahasiswaTabel.setModel(setTable());

        // Isi combo box status dengan nilai enum StatusMahasiswa
        form.statusComboBox.setModel(
                new DefaultComboBoxModel<>(Mahasiswa.StatusMahasiswa.values())
        );

        // Sembunyikan tombol update dan delete di awal
        form.updateButton.setVisible(false);
        form.deleteButton.setVisible(false);

        // ================= LISTENER =================

        // Listener tombol Add: memanggil insertData()
        form.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData();
            }
        });

        // Listener tombol Update: memanggil updateData()
        form.updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });

        // Listener tombol Delete: konfirmasi dulu, baru hapus
        form.deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tampilkan dialog konfirmasi
                int konfirmasi = JOptionPane.showConfirmDialog(
                        null,
                        "Yakin ingin menghapus data ini?",
                        "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION
                );
                // Jika user memilih YES, jalankan deleteData()
                if (konfirmasi == JOptionPane.YES_OPTION) {
                    deleteData();
                }
            }
        });

        // Listener tombol Cancel: bersihkan form
        form.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // Listener klik baris tabel: isi form dengan data baris terpilih
        form.mahasiswaTabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Ambil index baris yang diklik
                selectedIndex = form.mahasiswaTabel.getSelectedRow();

                // Jika tidak ada baris dipilih, hentikan
                if (selectedIndex == -1) return;

                // Ambil objek Mahasiswa sesuai baris terpilih
                Mahasiswa m = listMahasiswa.get(selectedIndex);

                // Isi semua input dengan data mahasiswa
                form.nimInput.setText(m.getNim());
                form.namaInput.setText(m.getNama());
                form.ttlInput.setText(m.getTtl());
                form.prodiInput.setText(m.getProdi());
                form.statusComboBox.setSelectedItem(m.getStatus());
                form.emailInput.setText(m.getEmail());

                // Tampilkan tombol update dan delete
                form.updateButton.setVisible(true);
                form.deleteButton.setVisible(true);
            }
        });
    }

    // ================= DATA AWAL =================

    // Method untuk mengisi data awal ke listMahasiswa
    private void populateList() {
        // Data mahasiswa pertama
        listMahasiswa.add(new Mahasiswa(
                "2501234", "Yusuf Willman Hamman", "12-05-2006",
                "Ilmu Komputer", Mahasiswa.StatusMahasiswa.AKTIF, "ucup@upi.edu"));

        // Data mahasiswa kedua
        listMahasiswa.add(new Mahasiswa(
                "2502345", "MG", "20-08-2006",
                "Ilmu Komputer", Mahasiswa.StatusMahasiswa.AKTIF, "mg@upi.edu"));

        // Data mahasiswa ketiga
        listMahasiswa.add(new Mahasiswa(
                "2203456", "Muhammad Irfan", "01-01-2004",
                "Ilmu Komputer Komputer", Mahasiswa.StatusMahasiswa.LULUS, "iffn@upi.edu"));
    }

    // ================= SET TABEL =================

    // Method untuk membuat dan mengisi model tabel
    public DefaultTableModel setTable() {
        // Nama-nama kolom tabel
        Object[] cols = { "No", "NIM", "Nama", "TTL", "Prodi", "Status", "Email" };

        // Buat model tabel dengan override isCellEditable
        DefaultTableModel model = new DefaultTableModel(null, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Tabel read-only, tidak bisa diedit langsung
                return false;
            }
        };

        // Loop semua data mahasiswa, masukkan ke tabel
        for (int i = 0; i < listMahasiswa.size(); i++) {
            Mahasiswa m = listMahasiswa.get(i);
            Object[] row = {
                    i + 1,                          // Nomor urut
                    m.getNim(),                     // NIM
                    m.getNama(),                    // Nama
                    m.getTtl(),                     // TTL
                    m.getProdi(),                   // Prodi
                    m.getStatus().toString(),       // Status (enum jadi String)
                    m.getEmail()                    // Email
            };
            model.addRow(row);
        }
        return model;
    }

    // ================= INSERT =================

    // Method untuk menambahkan data baru
    public void insertData() {
        // Ambil nilai dari input form
        String nim = form.nimInput.getText().trim();
        String nama = form.namaInput.getText().trim();
        String ttl = form.ttlInput.getText().trim();
        String prodi = form.prodiInput.getText().trim();
        Mahasiswa.StatusMahasiswa status =
                (Mahasiswa.StatusMahasiswa) form.statusComboBox.getSelectedItem();
        String email = form.emailInput.getText().trim();

        // Validasi sederhana: NIM dan Nama tidak boleh kosong
        if (nim.isEmpty() || nama.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "NIM dan Nama tidak boleh kosong!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tambahkan data baru ke list
        listMahasiswa.add(new Mahasiswa(nim, nama, ttl, prodi, status, email));

        // Refresh tabel
        form.mahasiswaTabel.setModel(setTable());

        // Bersihkan form
        clearForm();

        // Tampilkan pesan sukses
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
    }

    // ================= UPDATE =================

    // Method untuk mengubah data yang sudah ada
    public void updateData() {
        // Jika tidak ada baris dipilih, batalkan
        if (selectedIndex < 0) return;

        // Ambil nilai dari input form
        String nim = form.nimInput.getText().trim();
        String nama = form.namaInput.getText().trim();
        String ttl = form.ttlInput.getText().trim();
        String prodi = form.prodiInput.getText().trim();
        Mahasiswa.StatusMahasiswa status =
                (Mahasiswa.StatusMahasiswa) form.statusComboBox.getSelectedItem();
        String email = form.emailInput.getText().trim();

        // Validasi: NIM dan Nama tidak boleh kosong
        if (nim.isEmpty() || nama.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "NIM dan Nama tidak boleh kosong!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ambil objek Mahasiswa yang akan diubah
        Mahasiswa m = listMahasiswa.get(selectedIndex);

        // Set semua atribut dengan nilai baru
        m.setNim(nim);
        m.setNama(nama);
        m.setTtl(ttl);
        m.setProdi(prodi);
        m.setStatus(status);
        m.setEmail(email);

        // Refresh tabel
        form.mahasiswaTabel.setModel(setTable());

        // Bersihkan form
        clearForm();

        // Tampilkan pesan sukses
        JOptionPane.showMessageDialog(null, "Data berhasil diubah");
    }

    // ================= DELETE =================

    // Method untuk menghapus data
    public void deleteData() {
        // Pastikan index valid
        if (selectedIndex >= 0 && selectedIndex < listMahasiswa.size()) {
            // Hapus data dari list
            listMahasiswa.remove(selectedIndex);

            // Refresh tabel
            form.mahasiswaTabel.setModel(setTable());

            // Bersihkan form
            clearForm();

            // Tampilkan pesan sukses
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        }
    }

    // ================= CLEAR FORM =================

    // Method untuk mengosongkan semua input dan mereset state
    public void clearForm() {
        // Kosongkan semua text field
        form.nimInput.setText("");
        form.namaInput.setText("");
        form.ttlInput.setText("");
        form.prodiInput.setText("");
        form.emailInput.setText("");

        // Reset combo box ke pilihan pertama
        form.statusComboBox.setSelectedIndex(0);

        // Sembunyikan tombol update dan delete
        form.updateButton.setVisible(false);
        form.deleteButton.setVisible(false);

        // Reset index baris terpilih
        selectedIndex = -1;
    }

    // ================= MAIN =================

    // Entry point program
    public static void main(String[] args) {
        // Buat objek window DataMahasiswa
        DataMahasiswa window = new DataMahasiswa();

        // Pasang panel utama dari form sebagai content pane
        window.setContentPane(window.form.getMainPanel());

        // Atur judul window
        window.setTitle("Data Mahasiswa");

        // Atur ukuran window
        window.setSize(900, 600);

        // Letakkan window di tengah layar
        window.setLocationRelativeTo(null);

        // Program berhenti saat window ditutup
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tampilkan window
        window.setVisible(true);
    }
}