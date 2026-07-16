package pbotubesmcd.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import pbotubesmcd.components.DefaultTitle;
import pbotubesmcd.controllers.AuthController;
import pbotubesmcd.utils.Router;
import pbotubesmcd.utils.UITheme;
import pbotubesmcd.views.admin.ManagementUserView;

public class HomeAdminView extends JPanel {
    private final AuthController authController;

    private final JPanel mainPanel;
    private JPanel contentArea;

    private final CardLayout cardLayout;
    private CardLayout contentCardLayout;

    public HomeAdminView(JPanel mainPanel, CardLayout cardLayout) {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                Window window = SwingUtilities.getWindowAncestor(HomeAdminView.this);

                if (window != null) {
                    window.setSize(1000, 700);

                    window.setLocationRelativeTo(null);
                }
            }
        });

        this.authController = new AuthController();

        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(UITheme.COLOR_BG_LIGHT);

        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(UITheme.COLOR_MCD_RED);
        sidebarPanel.setPreferredSize(new Dimension(250, 0));
        sidebarPanel.setBorder(new EmptyBorder(30, 20, 30, 20));

        JLabel lblTitle = new JLabel("Admin Panel");
        lblTitle.setFont(UITheme.FONT_TITLE);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnDashboard = createSidebarButton("Dashboard");
        JButton btnKelolaPesanan = createSidebarButton("Pesanan");
        JButton btnKelolaMenu = createSidebarButton("Menu");
        JButton btnKelolaUser = createSidebarButton("Pengguna");
        JButton btnLogout = createSidebarButton("Logout");

        sidebarPanel.add(lblTitle);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        sidebarPanel.add(btnDashboard);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebarPanel.add(btnKelolaPesanan);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebarPanel.add(btnKelolaMenu);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebarPanel.add(btnKelolaUser);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(btnLogout);

        contentCardLayout = new CardLayout();
        contentArea = new JPanel(contentCardLayout);

        JPanel dashboardPanel = new JPanel(new GridBagLayout());
        dashboardPanel.setBackground(Color.WHITE);
        JLabel lblWelcome = new DefaultTitle("Selamat Datang, Administrator!");
        dashboardPanel.add(lblWelcome);

        ManagementUserView meneUserView = new ManagementUserView();

        contentArea.add(dashboardPanel, Router.DASHBOARD);
        contentArea.add(meneUserView, Router.KELOLA_USER);

        add(sidebarPanel, BorderLayout.WEST);
        add(contentArea, BorderLayout.CENTER);

        btnDashboard.addActionListener(e -> {
            contentCardLayout.show(contentArea, Router.DASHBOARD);
        });

        btnKelolaUser.addActionListener(e -> {
            contentCardLayout.show(contentArea, Router.KELOLA_USER);
        });

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Apakah Anda yakin ingin keluar?", "Konfirmasi Logout",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    authController.logout();

                    Window window = SwingUtilities.getWindowAncestor(this);
                    if (window != null) {
                        window.setSize(600, 800);
                        window.setLocationRelativeTo(null);
                    }

                    cardLayout.show(mainPanel, Router.LOGIN);
                } catch (IllegalStateException exc) {
                    JOptionPane.showMessageDialog(this, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(UITheme.FONT_LABEL);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(180, 0, 5));

        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

}
