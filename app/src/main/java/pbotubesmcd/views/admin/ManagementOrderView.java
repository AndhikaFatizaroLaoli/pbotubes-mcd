package pbotubesmcd.views.admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import pbotubesmcd.components.DefaultButtonRed;
import pbotubesmcd.controllers.OrderController;
import pbotubesmcd.controllers.OrderDetailController;
import pbotubesmcd.enums.OrderStatus;
import pbotubesmcd.models.Order;
import pbotubesmcd.models.OrderDetail;

public class ManagementOrderView extends AbstractManagementView {
    private final OrderController orderController;
    private final OrderDetailController orderDetailController;

    public ManagementOrderView() {
        super("Management Order");
        this.orderController = new OrderController();
        this.orderDetailController = new OrderDetailController();
        loadData();
    }

    @Override
    protected String[] getColumnNames() {
        return new String[] { "ID", "Pemesan", "Tanggal Pemesanan", "Nomer Antrian", "Total Harga", "Status",
                "Tipe Pemesanan" };
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        List<Order> daftarOrder = orderController.getAll();

        if (daftarOrder != null) {
            for (Order order : daftarOrder) {
                Object[] baris = {
                        order.getIdOrder(),
                        order.getUser().getUsername(),
                        order.getTanggalOrder(),
                        order.getNomorAntrean(),
                        order.getTotalHarga(),
                        order.getStatus().name(),
                        order.getOrderType().name()
                };
                tableModel.addRow(baris);
            }
        }
    }

    @Override
    protected void showFormDialog(boolean isEdit) {
        if (!isEdit) {
            return;
        }

        int selectedRow = table.getSelectedRow();

        if (isEdit && selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih order yang ingin diedit pada tabel terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JComboBox<OrderStatus> cbStatus = new JComboBox<>(OrderStatus.values());

        Integer idOrder;

        idOrder = (Integer) tableModel.getValueAt(selectedRow, 0);

        String oldStatus = (String) tableModel.getValueAt(selectedRow, 5);
        cbStatus.setSelectedItem(OrderStatus.valueOf(oldStatus));

        JPanel panelForm = new JPanel(new GridLayout(2, 1, 5, 5));
        panelForm.add(new JLabel("Ubah status untuk ID Pesanan: " + idOrder));
        panelForm.add(cbStatus);

        int confirm = JOptionPane.showConfirmDialog(this, panelForm, "Update Status Pesanan",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (confirm == JOptionPane.OK_OPTION) {
            try {
                OrderStatus statusBaru = (OrderStatus) cbStatus.getSelectedItem();

                orderController.updateStatus(idOrder, statusBaru);

                loadData();

                JOptionPane.showMessageDialog(this, "Status pesanan berhasil diperbarui!",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (HeadlessException ex) {
                JOptionPane.showMessageDialog(this, "Gagal mengubah status: " + ex.getMessage(),
                        "Error Sistem", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    protected void deleteSelectedItem() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pesanan yang ingin dihapus pada tabel terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer idOrder = (Integer) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus pesanan dengan ID: " + idOrder + "?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                orderController.delete(idOrder);
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

    @Override
    protected void addExtraButtons(JPanel panelAksi) {
        JButton btnDetail = new DefaultButtonRed("Detail");
        panelAksi.add(btnDetail);

        panelAksi.add(btnDetail, 0);
        btnDetail.addActionListener(e -> showDetailPesanan());

        if (btnTambah != null)
            btnTambah.setVisible(false);

        if (btnEdit != null)
            btnEdit.setText("Update Status");
        if (btnHapus != null)
            btnHapus.setText("Batalkan Pesanan");
    }

    private void showDetailPesanan() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pesanan di tabel terlebih dahulu untuk melihat detail!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer idOrder = Integer.valueOf(tableModel.getValueAt(selectedRow, 0).toString());
        Order totalHargaOrder = orderController.getById(idOrder);

        String[] kolomDetail = { "Nama Menu", "Jumlah", "Subtotal" };
        DefaultTableModel detailModel = new DefaultTableModel(null, kolomDetail) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<OrderDetail> listDetail = orderDetailController.getAllById(idOrder);

        if (listDetail != null) {
            for (OrderDetail detail : listDetail) {
                Object[] baris = {
                        detail.getMenu().getNamaMenu(),
                        detail.getJumlah(),
                        detail.getSubtotal()
                };
                detailModel.addRow(baris);
            }
        }

        JTable detailTable = new JTable(detailModel);
        detailTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(detailTable);
        scrollPane.setPreferredSize(new Dimension(450, 200));

        JPanel panelUtama = new JPanel(new BorderLayout(0, 10));
        panelUtama.add(new JLabel("<html><b>Rincian Menu untuk ID Pesanan: " + idOrder + "</b></html>"),
                BorderLayout.NORTH);
        panelUtama.add(scrollPane, BorderLayout.CENTER);

        JLabel lblTotal = new JLabel("<html><b>Grand Total: Rp " + totalHargaOrder.getTotalHarga() + "</b></html>");
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        panelUtama.add(lblTotal, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(this,
                panelUtama,
                "Detail Pesanan",
                JOptionPane.PLAIN_MESSAGE);
    }
}
