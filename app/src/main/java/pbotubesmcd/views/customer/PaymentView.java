package pbotubesmcd.views.customer;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import pbotubesmcd.components.DefaultButtonRed;
import pbotubesmcd.components.DefaultButtonWhite;
import pbotubesmcd.components.DefaultButtonYellow;
import pbotubesmcd.controllers.AuthController;
import pbotubesmcd.controllers.CartController;
import pbotubesmcd.controllers.OrderController;
import pbotubesmcd.controllers.OrderDetailController;
import pbotubesmcd.enums.OrderStatus;
import pbotubesmcd.enums.OrderType;
import pbotubesmcd.models.CartItem;
import pbotubesmcd.models.Order;
import pbotubesmcd.models.OrderDetail;
import pbotubesmcd.models.User;
import pbotubesmcd.utils.OrderSession;
import pbotubesmcd.utils.Router;
import pbotubesmcd.utils.UITheme;

public class PaymentView extends JPanel {
    private final CartController cartController;
    private final AuthController authController;
    private final OrderController orderController;
    private final OrderDetailController orderDetailController;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    private JLabel lblTotalHarga;
    private String metodePilihan = "";

    public PaymentView(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.cartController = CartController.getInstance();
        this.authController = new AuthController();
        this.orderController = new OrderController();
        this.orderDetailController = new OrderDetailController();

        initializeUI();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                refreshPaymentUI();
            }
        });
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(UITheme.COLOR_BG_LIGHT);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.COLOR_MCD_YELLOW);
        headerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel lblTitle = new JLabel("Metode Pembayaran");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        headerPanel.add(lblTitle, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(UITheme.COLOR_BG_LIGHT);

        JPanel metodePanel = new JPanel(new GridLayout(3, 1, 0, 20));
        metodePanel.setOpaque(false);
        metodePanel.setPreferredSize(new Dimension(400, 300));
        JPanel paymentPanel = new JPanel(new BorderLayout());
        paymentPanel.setBackground(UITheme.COLOR_BG_LIGHT);
        paymentPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        JButton btnQris = new DefaultButtonWhite("QRIS / E-Wallet");
        JButton btnKartu = new DefaultButtonWhite("Kartu Debit / Kredit");
        JButton btnTunai = new DefaultButtonWhite("Tunai di Kasir");

        Font fontMetode = new Font("SansSerif", Font.BOLD, 20);
        btnQris.setFont(fontMetode);
        btnKartu.setFont(fontMetode);
        btnTunai.setFont(fontMetode);

        ActionListener metodeAction = e -> {
            btnQris.setBackground(Color.WHITE);
            btnKartu.setBackground(Color.WHITE);
            btnTunai.setBackground(Color.WHITE);

            JButton diklik = (JButton) e.getSource();
            diklik.setBackground(UITheme.COLOR_MCD_YELLOW);
            metodePilihan = diklik.getText();
        };

        btnQris.addActionListener(metodeAction);
        btnKartu.addActionListener(metodeAction);
        btnTunai.addActionListener(metodeAction);

        metodePanel.add(btnQris);
        metodePanel.add(btnKartu);
        metodePanel.add(btnTunai);

        centerPanel.add(metodePanel);
        add(centerPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new BorderLayout(0, 15));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        lblTotalHarga = new JLabel("Grand Total: Rp 0");
        lblTotalHarga.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTotalHarga.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnPanel.setOpaque(false);

        JButton btnKembali = new DefaultButtonYellow("Kembali");
        btnKembali.setPreferredSize(new Dimension(200, 50));
        btnKembali.addActionListener(e -> cardLayout.show(mainPanel, Router.CART));

        JButton btnBayar = new DefaultButtonRed("Proses Pembayaran");
        btnBayar.setPreferredSize(new Dimension(250, 50));

        btnBayar.addActionListener(e -> processPayment());

        btnPanel.add(btnKembali);
        btnPanel.add(btnBayar);

        footerPanel.add(lblTotalHarga, BorderLayout.NORTH);
        footerPanel.add(btnPanel, BorderLayout.CENTER);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private void refreshPaymentUI() {
        lblTotalHarga.setText("Grand Total: Rp " + cartController.getTotalCartPrice());
        metodePilihan = "";
    }

    private void processPayment() {
        if (metodePilihan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Silakan pilih metode pembayaran terlebih dahulu!", "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            User customer = authController.getCurrentUser();
            OrderType tipePesanan = OrderSession.getCurrentOrderType();
            BigDecimal totalHarga = cartController.getTotalCartPrice();

            OrderStatus status = metodePilihan.equals("Tunai di Kasir") ? OrderStatus.PENDING : OrderStatus.PAID;

            int nomorAntrean = new Random().nextInt(900) + 100;

            Order orderBaru = new Order(null, customer, LocalDateTime.now(), nomorAntrean, totalHarga, status,
                    tipePesanan);

            orderController.add(orderBaru);

            List<CartItem> cartItems = cartController.getCartItems();
            for (CartItem item : cartItems) {
                OrderDetail detail = new OrderDetail(
                        null,
                        orderBaru,
                        item.getMenu(),
                        item.getJumlah(),
                        item.getSubtotal());
                orderDetailController.add(detail);
            }

            cartController.clearCart();
            OrderSession.clearOrderType();

            JOptionPane.showMessageDialog(this,
                    "Pembayaran Berhasil via " + metodePilihan + "!\nNomor Antrean Anda: " + nomorAntrean,
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

            cardLayout.show(mainPanel, Router.HOME_CUSTOMER);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal memproses pembayaran: " + ex.getMessage(), "Error Database",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
