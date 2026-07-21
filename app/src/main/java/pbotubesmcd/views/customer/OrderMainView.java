package pbotubesmcd.views.customer;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import pbotubesmcd.components.DefaultButtonRed;
import pbotubesmcd.components.DefaultButtonWhite;
import pbotubesmcd.components.DefaultButtonYellow;
import pbotubesmcd.components.DefaultLabel;
import pbotubesmcd.controllers.AuthController;
import pbotubesmcd.controllers.CartController;
import pbotubesmcd.controllers.CategoriesController;
import pbotubesmcd.controllers.MenuController;
import pbotubesmcd.models.Categories;
import pbotubesmcd.models.Menu;
import pbotubesmcd.utils.Images;
import pbotubesmcd.utils.Router;
import pbotubesmcd.utils.UITheme;

public class OrderMainView extends JPanel {
    private final CategoriesController categoriesController;
    private final AuthController authController;
    private final MenuController menuController;
    private final CartController cartController;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private JPanel cartItemsContainer;
    private JPanel contentPanel;
    private JPanel sidebarPanel;
    private JLabel lblTotalHarga;

    public OrderMainView(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.categoriesController = new CategoriesController();
        this.authController = new AuthController();
        this.menuController = new MenuController();

        this.cartController = CartController.getInstance();

        initializeUI();

        List<Categories> cat = categoriesController.getAll();
        if (cat != null && !cat.isEmpty()) {
            loadMenuByCategory(cat.get(0));
        }
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(UITheme.COLOR_BG_LIGHT);

        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(UITheme.COLOR_MCD_YELLOW);
        sidebarPanel.setPreferredSize(new Dimension(150, 0));
        sidebarPanel.setBorder(new EmptyBorder(30, 20, 30, 20));

        ImageIcon icon = new ImageIcon(getClass().getResource(Images.LOGO_RED));

        Image image = icon.getImage().getScaledInstance(
                50,
                50,
                Image.SCALE_SMOOTH);

        JLabel lblLogo = new JLabel(new ImageIcon(image));

        sidebarPanel.add(lblLogo);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        setSidebarButton();
        sidebarPanel.add(Box.createVerticalGlue());

        JButton btnLogout = new DefaultButtonRed("Logout");
        sidebarPanel.add(btnLogout);

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Apakah Anda yakin ingin keluar?", "Konfirmasi Logout",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    authController.logout();
                    cardLayout.show(mainPanel, Router.LOGIN);
                } catch (IllegalStateException exc) {
                    JOptionPane.showMessageDialog(this, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(sidebarPanel, BorderLayout.WEST);

        GridLayout gridLayout = new GridLayout(0, 3, 15, 15);

        contentPanel = new JPanel();
        contentPanel.setLayout(gridLayout);
        contentPanel.setBackground(UITheme.COLOR_BG_LIGHT);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(UITheme.COLOR_BG_LIGHT);
        wrapperPanel.add(contentPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int viewportWidth = scrollPane.getViewport().getWidth();

                int availableWidth = viewportWidth - 40;

                int maxColumns = Math.max(1, (availableWidth + 15) / 265);

                if (gridLayout.getColumns() != maxColumns) {
                    gridLayout.setColumns(maxColumns);
                    contentPanel.revalidate();
                }
            }
        });

        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomBar = new JPanel(new BorderLayout());
        bottomBar.setBackground(Color.WHITE);
        bottomBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                new EmptyBorder(15, 30, 15, 30)));

        lblTotalHarga = new JLabel("Keranjang: 0 Item | Total: Rp 0");
        lblTotalHarga.setFont(new Font("SansSerif", Font.BOLD, 18));

        JButton btnLihatKeranjang = new DefaultButtonRed("Lihat Pesanan");
        btnLihatKeranjang.setPreferredSize(new Dimension(200, 50));

        btnLihatKeranjang.addActionListener(e -> {
            if (cartController.getCartItems().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Silakan pilih menu terlebih dahulu!");
            } else {
                cardLayout.show(mainPanel, Router.CART);
            }
        });

        bottomBar.add(lblTotalHarga, BorderLayout.WEST);
        bottomBar.add(btnLihatKeranjang, BorderLayout.EAST);

        add(bottomBar, BorderLayout.SOUTH);
    }

    private void setSidebarButton() {
        List<Categories> daftarCategories = categoriesController.getAll();

        if (daftarCategories != null) {
            for (Categories categories : daftarCategories) {
                JButton btnCat = new DefaultButtonWhite(categories.getNamaKategori());

                btnCat.addActionListener(e -> loadMenuByCategory(categories));

                sidebarPanel.add(btnCat);
                sidebarPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }
    }

    private void loadMenuByCategory(Categories category) {
        contentPanel.removeAll();

        List<Menu> daftarMenu = menuController.getAllByCatId(category.getIdCategory());

        if (daftarMenu != null) {
            for (Menu menu : daftarMenu) {
                JPanel cardPanel = createMenuItemCard(menu);
                contentPanel.add(cardPanel);
            }
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createMenuItemCard(Menu menu) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        cardPanel.setPreferredSize(new Dimension(250, 300));

        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(menu.getGambar()));

            Image image = originalIcon.getImage().getScaledInstance(
                    150,
                    150,
                    Image.SCALE_SMOOTH);

            JLabel lblGambar = new JLabel(new ImageIcon(image));
            lblGambar.setHorizontalAlignment(SwingConstants.CENTER);

            JPanel imgWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
            imgWrapper.setOpaque(false);
            imgWrapper.add(lblGambar);
            cardPanel.add(imgWrapper, BorderLayout.NORTH);
        } catch (Exception e) {
            System.err.println("Gagal memuat gambar untuk menu: " + menu.getNamaMenu());
        }

        JLabel lblNama = new DefaultLabel(menu.getNamaMenu());
        lblNama.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel nameWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        nameWrapper.setOpaque(false);
        nameWrapper.add(lblNama);
        cardPanel.add(nameWrapper, BorderLayout.CENTER);

        JButton btnAddToCart = new DefaultButtonYellow("Tambah");
        btnAddToCart.addActionListener(e -> {
            if (menu.getStok() > 0) {
                cartController.addMenu(menu);
                lblTotalHarga.setText("Keranjang: " + cartController.getCartItems().size() +
                        " Item | Total: Rp " + cartController.getTotalCartPrice());
            } else {
                JOptionPane.showMessageDialog(this, "Stok habis!");
            }
        });

        cardPanel.add(btnAddToCart, BorderLayout.SOUTH);
        return cardPanel;
    }
}
