package pbotubesmcd.views.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import pbotubesmcd.components.DefaultButton;
import pbotubesmcd.components.DefaultTable;
import pbotubesmcd.components.DefaultTitle;
import pbotubesmcd.utils.UITheme;

public abstract class AbstractManagementView extends JPanel {
    protected DefaultTableModel tableModel;
    protected JTable table;

    protected JButton btnTambah;
    protected JButton btnEdit;
    protected JButton btnHapus;

    public AbstractManagementView(String title) {
        initializeBaseUI(title);
        setupBaseActionListeners();
    }

    private void initializeBaseUI(String title) {
        setLayout(new BorderLayout(10, 10));
        setBackground(UITheme.COLOR_BG_LIGHT);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new DefaultTitle(title);
        add(lblTitle, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(null, getColumnNames()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return getTableColumnClass(columnIndex);
            }
        };

        table = new DefaultTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelAksi = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelAksi.setBackground(Color.WHITE);

        btnTambah = new DefaultButton("Tambah");
        btnEdit = new DefaultButton("Edit");
        btnHapus = new DefaultButton("Hapus");

        btnHapus.setBackground(Color.DARK_GRAY);

        panelAksi.add(btnTambah);
        panelAksi.add(btnEdit);
        panelAksi.add(btnHapus);

        addExtraButtons(panelAksi);

        add(panelAksi, BorderLayout.SOUTH);
    }

    private void setupBaseActionListeners() {
        btnTambah.addActionListener((ActionEvent e) -> showFormDialog(false));
        btnEdit.addActionListener((ActionEvent e) -> showFormDialog(true));
        btnHapus.addActionListener((ActionEvent e) -> deleteSelectedItem());
    }

    protected abstract String[] getColumnNames();

    protected abstract void loadData();

    protected abstract void showFormDialog(boolean isEdit);

    protected abstract void deleteSelectedItem();

    protected abstract Class<?> getTableColumnClass(int columnIndex);

    protected void addExtraButtons(JPanel panelAksi) {
    }
}
