package pbotubesmcd.views.admin;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pbotubesmcd.components.DefaultLabel;
import pbotubesmcd.components.DefaultTextField;
import pbotubesmcd.controllers.CategoriesController;
import pbotubesmcd.models.Categories;

public class ManagementCategoriesView extends AbstractManagementView {
    private final CategoriesController categoriesController;

    public ManagementCategoriesView() {
        super("Management Categories");
        this.categoriesController = new CategoriesController();
        loadData();
    }

    @Override
    protected String[] getColumnNames() {
        return new String[] { "ID", "Kategori" };
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        List<Categories> daftarCategories = categoriesController.getAll();

        if (daftarCategories != null) {
            for (Categories categories : daftarCategories) {
                Object[] baris = {
                        categories.getIdCategory(),
                        categories.getNamaKategori()
                };
                tableModel.addRow(baris);
            }
        }
    }

    @Override
    protected void showFormDialog(boolean isEdit) {
        int selectedRow = table.getSelectedRow();

        if (isEdit && selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih kategori yang ingin diedit pada tabel terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField txtKategori = new DefaultTextField(15);

        Integer idCategory = null;

        if (isEdit) {
            idCategory = (Integer) tableModel.getValueAt(selectedRow, 0);
            String oldKategori = (String) tableModel.getValueAt(selectedRow, 1);

            txtKategori.setText(oldKategori);
        }

        JPanel panelForm = new JPanel(new GridLayout(2, 2, 5, 5));

        panelForm.add(new DefaultLabel("Kategori:"));
        panelForm.add(txtKategori);

        String title = isEdit ? "Edit Kategori" : "Tambah Kategori";
        int confirm = JOptionPane.showConfirmDialog(this, panelForm, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (confirm == JOptionPane.OK_OPTION) {
            String kategori = txtKategori.getText();

            if (kategori.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Kategori tidak boleh kosong!", "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Categories category = new Categories(idCategory, kategori);

                if (isEdit) {
                    categoriesController.update(category);
                } else {
                    categoriesController.add(category);
                }

                loadData();
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan!", "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage(), "Error Sistem",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    protected void deleteSelectedItem() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih kategori yang ingin dihapus pada tabel terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer idCategory = (Integer) tableModel.getValueAt(selectedRow, 0);
        String kategori = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus kategori '" + kategori + "'?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                categoriesController.delete(idCategory);
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
