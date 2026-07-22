package pbotubesmcd.views.admin;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pbotubesmcd.components.DefaultLabel;
import pbotubesmcd.components.DefaultTextField;
import pbotubesmcd.controllers.CategoriesController;
import pbotubesmcd.controllers.MenuController;
import pbotubesmcd.models.Categories;
import pbotubesmcd.models.Menu;
import pbotubesmcd.utils.ImageHelper;

public class ManagementMenuView extends AbstractManagementView {
    private final MenuController menuController;
    private final CategoriesController categoryController;

    public ManagementMenuView() {
        super("Management Menu");
        this.menuController = new MenuController();
        this.categoryController = new CategoriesController();
        table.setRowHeight(60);
        loadData();
    }

    @Override
    protected String[] getColumnNames() {
        return new String[] { "ID", "Kategori", "Nama Menu", "Harga", "Stok", "Gambar" };
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        List<Menu> daftarMenu = menuController.getAll();

        if (daftarMenu != null) {
            for (Menu menu : daftarMenu) {
                ImageIcon gambar = ImageHelper.getResizedIcon(menu.getGambar(), 50, 50);

                Object[] baris = {
                        menu.getIdMenu(),
                        menu.getCategory().getNamaKategori(),
                        menu.getNamaMenu(),
                        menu.getHarga(),
                        menu.getStok(),
                        gambar
                };
                tableModel.addRow(baris);
            }
        }
    }

    @Override
    protected void showFormDialog(boolean isEdit) {
        int selectedRow = table.getSelectedRow();

        if (isEdit && selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih menu yang ingin diedit pada tabel terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JComboBox<Categories> categoriesComboBox = new JComboBox<>(
                categoryController.getAll().toArray(new Categories[0]));

        JTextField txtNamaMenu = new DefaultTextField(15);
        JTextField txtHarga = new DefaultTextField(15);
        JTextField txtStok = new DefaultTextField(15);

        JTextField txtGambar = new DefaultTextField(10);
        txtGambar.setEditable(false);

        JButton btnBrowse = new JButton("Browse...");
        JPanel panelGambar = new JPanel(new BorderLayout(5, 0));
        panelGambar.add(txtGambar, BorderLayout.CENTER);
        panelGambar.add(btnBrowse, BorderLayout.EAST);

        btnBrowse.addActionListener(e -> {
            FileDialog fileDialog = new FileDialog((Frame) null, "Pilih Gambar Menu",
                    FileDialog.LOAD);

            fileDialog.setFile("*.png;*.jpg;*.jpeg");

            fileDialog.setVisible(true);

            String namaFile = fileDialog.getFile();
            String direktori = fileDialog.getDirectory();

            if (namaFile != null && direktori != null) {
                txtGambar.setText("/assets/images/" + namaFile);
            }
        });

        Integer idMenu = null;

        if (isEdit) {
            idMenu = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            String oldKategori = tableModel.getValueAt(selectedRow, 1).toString();
            String oldNamaMenu = tableModel.getValueAt(selectedRow, 2).toString();
            String oldHarga = tableModel.getValueAt(selectedRow, 3).toString();
            String oldStok = tableModel.getValueAt(selectedRow, 4).toString();

            txtNamaMenu.setText(oldNamaMenu);
            txtHarga.setText(oldHarga);
            txtStok.setText(oldStok);

            txtGambar.setText("");

            for (int i = 0; i < categoriesComboBox.getItemCount(); i++) {
                if (categoriesComboBox.getItemAt(i).getNamaKategori().equals(oldKategori)) {
                    categoriesComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        panelForm.add(new DefaultLabel("Kategori:"));
        panelForm.add(categoriesComboBox);
        panelForm.add(new DefaultLabel("Nama Menu:"));
        panelForm.add(txtNamaMenu);
        panelForm.add(new DefaultLabel("Harga:"));
        panelForm.add(txtHarga);
        panelForm.add(new DefaultLabel("Stok:"));
        panelForm.add(txtStok);
        panelForm.add(new DefaultLabel("Path Gambar (/assets/images/..):"));

        panelForm.add(panelGambar);

        String title = isEdit ? "Edit Menu" : "Tambah Menu";
        int confirm = JOptionPane.showConfirmDialog(this, panelForm, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (confirm == JOptionPane.OK_OPTION) {
            try {
                String namaMenu = txtNamaMenu.getText().trim();
                String hargaStr = txtHarga.getText().trim();
                String stokStr = txtStok.getText().trim();
                String pathGambar = txtGambar.getText().trim();

                Categories selectedCategory = (Categories) categoriesComboBox.getSelectedItem();

                if (namaMenu.isEmpty() || hargaStr.isEmpty() || stokStr.isEmpty() || selectedCategory == null) {
                    JOptionPane.showMessageDialog(this,
                            "Semua data wajib diisi (kecuali gambar jika tidak ingin diubah)!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                BigDecimal harga = new BigDecimal(hargaStr);
                int stok = Integer.parseInt(stokStr);

                String finalPathGambar = pathGambar.isEmpty() ? null : pathGambar;

                Menu menu = new Menu(idMenu, selectedCategory, namaMenu, harga, stok, finalPathGambar);

                if (isEdit) {
                    menuController.update(menu);
                } else {
                    menuController.add(menu);
                }

                loadData();

                JOptionPane.showMessageDialog(this, "Data menu berhasil disimpan!",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Harga dan Stok harus berupa angka yang valid!",
                        "Input Tidak Valid", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage(),
                        "Error Sistem", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    protected void deleteSelectedItem() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih menu yang ingin dihapus pada tabel terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer idMenu = (Integer) tableModel.getValueAt(selectedRow, 0);
        String namaMenu = (String) tableModel.getValueAt(selectedRow, 2);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus menu '" + namaMenu + "'?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                menuController.delete(idMenu);
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
        if (columnIndex == 5) {
            return ImageIcon.class;
        }
        return Object.class;
    }
}
