package pbotubesmcd.views;

import pbotubesmcd.database.Database;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pbotubesmcd.utils.Session;
import pbotubesmcd.models.User;

public class HomeCustomerView extends JFrame {
    private JPanel categoryPanel;
    private JPanel menuGridPanel;
    private JPanel bottomCartPanel;

    private JLabel totalItemsLabel;
    private JLabel totalPriceLabel;
    private JButton checkoutButton;

    private int totalItems = 0;
    private double totalPrice = 0.0;

    public HomeCustomerView() {
        setTitle("McDonald's Self-Help Kiosk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(221, 42, 36));
        // 1. Ambil data user yang sedang aktif dari Session
        User currentUser = Session.getCurrentUser();

        // 2. Lakukan pengecekan agar tidak null (safety check)
        String username = (currentUser != null) ? currentUser.getUsername() : "Guest";

        // 3. Gabungkan ke dalam JLabel
        JLabel titleLabel = new JLabel("Selamat Datang di McDonald's, " + username);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.YELLOW);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Sidebar Kategori
        categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setBorder(BorderFactory.createTitledBorder("Kategori"));
        categoryPanel.setBackground(Color.WHITE);
        add(categoryPanel, BorderLayout.WEST);

        // Grid Menu Utama
        menuGridPanel = new JPanel();
        menuGridPanel.setLayout(new GridLayout(0, 3, 15, 15));
        menuGridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(menuGridPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Bar Informasi Keranjang di Bagian Bawah
        bottomCartPanel = new JPanel(new BorderLayout(10, 10));
        bottomCartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));
        bottomCartPanel.setBackground(Color.DARK_GRAY);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);
        totalItemsLabel = new JLabel("Item di Keranjang: 0");
        totalItemsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalItemsLabel.setForeground(Color.WHITE);

        totalPriceLabel = new JLabel("Total Harga: Rp 0");
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalPriceLabel.setForeground(Color.YELLOW);

        infoPanel.add(totalItemsLabel);
        infoPanel.add(totalPriceLabel);
        bottomCartPanel.add(infoPanel, BorderLayout.WEST);

        checkoutButton = new JButton("Lihat Keranjang & Bayar");
        checkoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        checkoutButton.setBackground(Color.GREEN);
        checkoutButton.setForeground(Color.BLACK);
        checkoutButton.setFocusable(false);
        bottomCartPanel.add(checkoutButton, BorderLayout.EAST);
        add(bottomCartPanel, BorderLayout.SOUTH);

        // Ambil data dinamis dari database PostgreSQL
        loadCategoriesFromDatabase();
        loadMenusFromDatabase(null); // Awal mula tampilkan semua menu
    }

    // Mengambil data Kategori secara dinamis dari database
    private void loadCategoriesFromDatabase() {
        categoryPanel.removeAll();

        // Tombol untuk menampilkan semua menu terlebih dahulu
        JButton allButton = new JButton("Semua Menu");
        allButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        allButton.setMaximumSize(new Dimension(160, 40));
        allButton.addActionListener(e -> loadMenusFromDatabase(null));
        categoryPanel.add(allButton);
        categoryPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        String query = "SELECT * FROM public.categories ORDER BY id_category ASC";

        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int catId = rs.getInt("id_category");
                String catName = rs.getString("name");

                JButton catButton = new JButton(catName);
                catButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                catButton.setMaximumSize(new Dimension(160, 40));
                catButton.setFocusable(false);

                // Ketika kategori diklik, filter menu berdasarkan id_category
                catButton.addActionListener(e -> loadMenusFromDatabase(catId));

                categoryPanel.add(catButton);
                categoryPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal mengambil data kategori dari database.");
        }

        categoryPanel.revalidate();
        categoryPanel.repaint();
    }

    // Mengambil data Menu secara dinamis (Dapat difilter berdasarkan ID Kategori)
    private void loadMenusFromDatabase(Integer categoryId) {
        menuGridPanel.removeAll();

        String query = "SELECT * FROM public.menus";
        if (categoryId != null) {
            query += " WHERE id_category = ?";
        }
        query += " ORDER BY id_menu ASC";

        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            if (categoryId != null) {
                stmt.setInt(1, categoryId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");

                    // Membuat Kartu Item Menu Swing
                    JPanel itemCard = new JPanel(new BorderLayout(5, 5));
                    itemCard.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                    itemCard.setBackground(Color.WHITE);

                    JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
                    nameLabel.setFont(new Font("Arial", Font.BOLD, 14));

                    JLabel priceLabel = new JLabel("Rp " + String.format("%,.0f", price), SwingConstants.CENTER);
                    priceLabel.setForeground(Color.GRAY);

                    JButton addButton = new JButton("Tambah ke Cart");
                    addButton.setBackground(new Color(254, 194, 41));
                    addButton.setFocusable(false);

                    addButton.addActionListener(e -> {
                        totalItems++;
                        totalPrice += price;
                        updateCartBar();
                    });

                    JPanel infoMenuPanel = new JPanel(new GridLayout(2, 1));
                    infoMenuPanel.setOpaque(false);
                    infoMenuPanel.add(nameLabel);
                    infoMenuPanel.add(priceLabel);

                    itemCard.add(infoMenuPanel, BorderLayout.CENTER);
                    itemCard.add(addButton, BorderLayout.SOUTH);

                    menuGridPanel.add(itemCard);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat menu dari database.");
        }

        menuGridPanel.revalidate();
        menuGridPanel.repaint();
    }

    private void updateCartBar() {
        totalItemsLabel.setText("Item di Keranjang: " + totalItems);
        totalPriceLabel.setText("Total Harga: Rp " + String.format("%,.0f", totalPrice));
    }
}