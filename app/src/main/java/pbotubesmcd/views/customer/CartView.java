package pbotubesmcd.views.customer;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import pbotubesmcd.components.DefaultButtonRed;
import pbotubesmcd.components.DefaultButtonYellow;
import pbotubesmcd.controllers.CartController;
import pbotubesmcd.models.CartItem;
import pbotubesmcd.utils.Router;
import pbotubesmcd.utils.UITheme;

public class CartView extends JPanel {

    private final CartController cartController;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    private JPanel cartItemsContainer;
    private JLabel lblGrandTotal;

    public CartView(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.cartController = CartController.getInstance();

        initializeUI();

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                refreshCartUI();
            }
        });
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(UITheme.COLOR_BG_LIGHT);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.COLOR_MCD_YELLOW);
        headerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel lblTitle = new JLabel("Keranjang Anda");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        headerPanel.add(lblTitle, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        cartItemsContainer = new JPanel();
        cartItemsContainer.setLayout(new BoxLayout(cartItemsContainer, BoxLayout.Y_AXIS));
        cartItemsContainer.setBackground(UITheme.COLOR_BG_LIGHT);

        JScrollPane scrollPane = new JScrollPane(cartItemsContainer);
        scrollPane.setBorder(new EmptyBorder(20, 50, 20, 50));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new BorderLayout(20, 0));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        lblGrandTotal = new JLabel("Grand Total: Rp 0");
        lblGrandTotal.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblGrandTotal.setHorizontalAlignment(SwingConstants.RIGHT);

        footerPanel.add(lblGrandTotal, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnPanel.setOpaque(false);

        JButton btnKembali = new DefaultButtonYellow("Tambah Menu Lain");
        btnKembali.setPreferredSize(new Dimension(200, 50));
        btnKembali.addActionListener(e -> cardLayout.show(mainPanel, Router.MAIN_ORDER));

        JButton btnCheckout = new DefaultButtonRed("Lanjut Pembayaran");
        btnCheckout.setPreferredSize(new Dimension(200, 50));
        btnCheckout.addActionListener(e -> {
            if (!cartController.getCartItems().isEmpty()) {
                cardLayout.show(mainPanel, Router.PAYMENT);
            }
        });

        btnPanel.add(btnKembali);
        btnPanel.add(btnCheckout);

        footerPanel.add(btnPanel, BorderLayout.EAST);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private void refreshCartUI() {
        cartItemsContainer.removeAll();
        List<CartItem> items = cartController.getCartItems();

        if (items.isEmpty()) {
            JLabel lblKosong = new JLabel("Keranjang Kosong", SwingConstants.CENTER);
            lblKosong.setFont(new Font("SansSerif", Font.ITALIC, 18));
            cartItemsContainer.add(lblKosong);
        } else {
            for (CartItem item : items) {
                cartItemsContainer.add(createCartRow(item));
                cartItemsContainer.add(Box.createRigidArea(new Dimension(0, 10)));
            }

            lblGrandTotal.setText("Grand Total: Rp " + cartController.getTotalCartPrice());
            cartItemsContainer.revalidate();
            cartItemsContainer.repaint();
        }
    }

    private JPanel createCartRow(CartItem item) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                new EmptyBorder(15, 20, 15, 20)));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);
        JLabel lblNama = new JLabel(item.getMenu().getNamaMenu());
        lblNama.setFont(new Font("SansSerif", Font.BOLD, 18));
        JLabel lblHarga = new JLabel("Rp " + item.getMenu().getHarga());
        lblHarga.setForeground(Color.GRAY);
        infoPanel.add(lblNama);
        infoPanel.add(lblHarga);
        row.add(infoPanel, BorderLayout.WEST);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        actionPanel.setOpaque(false);

        JLabel lblSub = new JLabel("Rp " + item.getSubtotal());
        lblSub.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblSub.setForeground(new Color(218, 41, 28));

        JButton btnMin = new DefaultButtonRed("-");
        JLabel lblQty = new JLabel(String.valueOf(item.getJumlah()));
        lblQty.setFont(new Font("SansSerif", Font.BOLD, 18));
        JButton btnPlus = new DefaultButtonYellow("+");

        btnMin.addActionListener(e -> {
            cartController.decreaseMenu(item.getMenu());
            refreshCartUI();
        });

        btnPlus.addActionListener(e -> {
            if (item.getMenu().getStok() > item.getJumlah()) {
                cartController.addMenu(item.getMenu());
                refreshCartUI();
            }
        });

        actionPanel.add(lblSub);
        actionPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        actionPanel.add(btnMin);
        actionPanel.add(lblQty);
        actionPanel.add(btnPlus);

        row.add(actionPanel, BorderLayout.EAST);
        return row;
    }
}
