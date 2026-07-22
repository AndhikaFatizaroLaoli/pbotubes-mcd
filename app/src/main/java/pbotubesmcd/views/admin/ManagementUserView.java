package pbotubesmcd.views.admin;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import pbotubesmcd.controllers.UserController;
import pbotubesmcd.enums.UserRole;
import pbotubesmcd.models.User;

public class ManagementUserView extends AbstractManagementView {
    private final UserController userController;

    public ManagementUserView() {
        super("Management User");
        this.userController = new UserController();
        loadData();
    }

    @Override
    protected String[] getColumnNames() {
        return new String[] { "ID", "Username", "Password", "Role" };
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        List<User> daftarUser = userController.getAll();

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

    @Override
    protected void showFormDialog(boolean isEdit) {
        int selectedRow = table.getSelectedRow();

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
                    userController.update(user);
                } else {
                    userController.add(user);
                }

                loadData();
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan!", "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (HeadlessException ex) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage(), "Error Sistem",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    protected void deleteSelectedItem() {
        int selectedRow = table.getSelectedRow();

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
                userController.delete(idUser);
                loadData();
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!", "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (HeadlessException ex) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    protected Class<?> getTableColumnClass(int columnIndex) {
        return Object.class;
    }
}
