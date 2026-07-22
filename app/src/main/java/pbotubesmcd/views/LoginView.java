package pbotubesmcd.views;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import pbotubesmcd.components.DefaultButtonRed;
import pbotubesmcd.components.DefaultLabel;
import pbotubesmcd.components.DefaultPasswordField;
import pbotubesmcd.components.DefaultTextField;
import pbotubesmcd.components.DefaultTitle;
import pbotubesmcd.controllers.AuthController;
import pbotubesmcd.enums.UserRole;
import pbotubesmcd.exceptions.InvalidCredentialsException;
import pbotubesmcd.utils.Router;
import pbotubesmcd.utils.Session;
import pbotubesmcd.utils.UITheme;

public class LoginView extends JPanel {
    private final AuthController authController;

    JTextField txtUsername;
    JPasswordField txtPassword;

    public LoginView(JPanel mainPanel, CardLayout cardLayout) {
        this.authController = new AuthController();

        initializeUI(mainPanel, cardLayout);
    }

    private void initializeUI(JPanel mainPanel, CardLayout cardLayout) {
        setBackground(UITheme.COLOR_BG_LIGHT);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new DefaultTitle("McDonald's App - Login");

        JLabel lblUsername = new DefaultLabel("Username:");
        txtUsername = new DefaultTextField(20);

        JLabel lblPassword = new DefaultLabel("Password:");
        txtPassword = new DefaultPasswordField(20);

        JButton btnLogin = new DefaultButtonRed("Login");

        gbc.insets = new Insets(10, 10, 40, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(lblUsername, gbc);

        gbc.gridx = 1;
        add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblPassword, gbc);

        gbc.gridx = 1;
        add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnLogin, gbc);

        handleLoginButton(btnLogin, mainPanel, cardLayout);
    }

    private void handleLoginButton(JButton btnLogin, JPanel mainPanel, CardLayout cardLayout) {
        btnLogin.addActionListener((ActionEvent e) -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            try {
                authController.login(username, password);

                txtUsername.setText("");
                txtPassword.setText("");

                UserRole role = Session.getCurrentUser().getRole();

                switch (role) {
                    case ADMIN -> cardLayout.show(mainPanel, Router.HOME_ADMIN);
                    case CUSTOMER -> cardLayout.show(mainPanel, Router.HOME_CUSTOMER);
                    default -> JOptionPane.showMessageDialog(LoginView.this, "Role tidak dikenal");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(LoginView.this, ex.getMessage(), "Input Tidak Valid",
                        JOptionPane.WARNING_MESSAGE);
            } catch (InvalidCredentialsException ex) {
                JOptionPane.showMessageDialog(LoginView.this, ex.getMessage(), "Login Gagal",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(LoginView.this, "Terjadi kesalahan: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}