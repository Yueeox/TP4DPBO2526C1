import javax.swing.*;

public class MahasiswaForm {
    public JPanel mainPanel;
    public JLabel nimLabel;
    public JLabel namaLabel;
    public JLabel ttlLabel;
    public JLabel prodiLabel;
    public JLabel statusLabel;
    public JLabel emailLabel;
    public JTextField nimInput;
    public JTextField namaInput;
    public JTextField ttlInput;
    public JTextField prodiInput;
    public JTextField emailInput;
    public JButton addButton;
    public JButton updateButton;
    public JButton deleteButton;
    public JButton cancelButton;
    public JTable mahasiswaTabel;
    public JScrollPane mahasiswaScrollPane;
    public JComboBox<Mahasiswa.StatusMahasiswa> statusComboBox;

    public JPanel getMainPanel() {
        return mainPanel;
    }
}