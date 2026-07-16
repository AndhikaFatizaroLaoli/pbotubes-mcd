package pbotubesmcd.views.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import pbotubesmcd.components.DefaultButton;
import pbotubesmcd.components.DefaultTitle;
import pbotubesmcd.controllers.UserController;
import pbotubesmcd.enums.UserRole;
import pbotubesmcd.models.User;
import pbotubesmcd.utils.UITheme;

public class ManagementUserView extends JPanel {
    private final UserController userController;

    private DefaultTableModel tableModel;
    private JTable tableUser;

    private JButton btnTambah;
    private JButton btnEdit;
    private JButton btnHapus;

    public ManagementUserView() {
        this.userController = new UserController();

        initializeUI();
        setupActionListeners();
        loadDataUser();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UITheme.COLOR_BG_LIGHT);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new DefaultTitle("Manajemen User");
        add(lblTitle, BorderLayout.NORTH);

        String[] columnNames = { "ID", "Username", "Password", "Role" };

        tableModel = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableUser = new JTable(tableModel);
        tableUser.setFont(UITheme.FONT_INPUT);
        tableUser.setRowHeight(25);
        tableUser.getTableHeader().setFont(UITheme.FONT_LABEL);
        tableUser.getTableHeader().setBackground(UITheme.COLOR_MCD_YELLOW);

        JScrollPane scrollPane = new JScrollPane(tableUser);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelAksi = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelAksi.setBackground(Color.WHITE);

        btnTambah = new DefaultButton("Tambah User");
        btnEdit = new DefaultButton("Edit User");
        btnHapus = new DefaultButton("Hapus User");

        btnHapus.setBackground(Color.DARK_GRAY);

        panelAksi.add(btnTambah);
        panelAksi.add(btnEdit);
        panelAksi.add(btnHapus);

        add(panelAksi, BorderLayout.SOUTH);
    }

    private void setupActionListeners() {
        btnTambah.addActionListener((ActionEvent e) -> {
            showUserForm(false);
        });

        btnEdit.addActionListener((ActionEvent e) -> {
            showUserForm(true);
        });

        btnHapus.addActionListener((ActionEvent e) -> {
            hapusUser();
        });
    }

    private void showUserForm(boolean isEdit) {
        int selectedRow = tableUser.getSelectedRow();

        if (isEdit && selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih user yang ingin diedit pada tabel terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField txtUsername = new JTextField(15);
        JPasswordField txtPassword = new JPasswordField(15);
        JComboBox<UserRole> cbRole = new JComboBox<>(UserRole.values());

        Integer idUser = null;

        if (isEdit) {
            idUser = (Integer) tableModel.getValueAt(selectedRow, 0);
            String oldUsername = (String) tableModel.getValueAt(selectedRow, 1);
            String oldRole = (String) tableModel.getValueAt(selectedRow, 3);

            txtUsername.setText(oldUsername);
            cbRole.setSelectedItem(UserRole.valueOf(oldRole));
        }

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        panelForm.add(new JLabel("Username:"));
        panelForm.add(txtUsername);
        panelForm.add(new JLabel(isEdit ? "Password Baru:" : "Password:"));
        panelForm.add(txtPassword);
        panelForm.add(new JLabel("Role:"));
        panelForm.add(cbRole);

        String dialogTitle = isEdit ? "Edit Data User" : "Tambah User Baru";
        int result = JOptionPane.showConfirmDialog(this, panelForm, dialogTitle, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            UserRole role = (UserRole) cbRole.getSelectedItem();

            if (username.trim().isEmpty() || (!isEdit && password.trim().isEmpty())) {
                JOptionPane.showMessageDialog(this, "Username dan Password tidak boleh kosong!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                User user = new User(idUser, username, password, role);

                if (isEdit) {
                    userController.updateUser(user);
                } else {
                    userController.addUser(user);
                }

                loadDataUser();
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan!", "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage(), "Error Sistem",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void hapusUser() {
        int selectedRow = tableUser.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih user yang ingin dihapus pada tabel terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer idUser = (Integer) tableModel.getValueAt(selectedRow, 0);
        String username = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus user '" + username + "'?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                userController.deleteUser(idUser);
                loadDataUser();
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!", "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void loadDataUser() {
        tableModel.setRowCount(0);

        List<User> daftarUser = userController.getAllUser();

        if (daftarUser != null) {
            for (User user : daftarUser) {
                Object[] baris = {
                        user.getIdUser(),
                        user.getUsername(),
                        "********",
                        user.getRole().name()
                };
                tableModel.addRow(baris);
            }
        }
    }
}
