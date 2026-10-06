import java.text.DateFormat;


public class Mahasiswa {
    public enum StatusMahasiswa {AKTIF, CUTI, TIDAK_ADA_KETERANGAN, LULUS}
    private String nim;
    private String nama;
    private String ttl;
    private String prodi;
    private StatusMahasiswa status;
    private String email;

    public Mahasiswa(String nim, String nama, String ttl, String prodi, StatusMahasiswa status, String email) {
        this.nim = nim;
        this.nama = nama;
        this.ttl = ttl;
        this.prodi = prodi;
        this.status = status;
        this.email = email;
    }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getTtl() { return ttl; }
    public void setTtl(String ttl) { this.ttl = ttl; }

    public String getProdi() { return prodi; }
    public void setProdi(String prodi) { this.prodi = prodi; }

    public StatusMahasiswa getStatus() { return status; }
    public void setStatus(StatusMahasiswa status) { this.status = status; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}