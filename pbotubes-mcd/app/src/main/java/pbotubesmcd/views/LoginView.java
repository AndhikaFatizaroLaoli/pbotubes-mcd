package pbotubesmcd.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pbotubesmcd.controllers.AuthController;
import pbotubesmcd.database.Database;
import pbotubesmcd.enums.UserRole;

public class LoginView {
    private JFrame frame;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginView() {
        initializeUI();
    }

    private void initializeUI() {
        // Create the main window frame
        frame = new JFrame("McDonald's App - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 220);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setResizable(false);

        // Layout setup
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title/Header
        JLabel lblTitle = new JLabel("Welcome back!", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        // Username Label and Text Field
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        panel.add(txtUsername, gbc);

        // Password Label and Field
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        panel.add(txtPassword, gbc);

        // Login Button
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        btnLogin = new JButton("Login");
        panel.add(btnLogin, gbc);

        frame.add(panel);

        // Pastikan Anda sudah mengimpor AuthController di bagian atas file:
// import pbotubesmcd.controllers.AuthController;

btnLogin.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        // 1. Panggil AuthController
        AuthController authController = new AuthController();

        // 2. Lakukan validasi login melalui Controller (Opsi 1)
        if (authController.login(username, password)) {
            // Jika true, Controller sudah memunculkan HomeCustomerView / HomeAdminView.
            // Maka di sini kita cukup menutup halaman Login saja.
            frame.dispose(); 
        } else {
            // Jika false, tampilkan pesan error
            JOptionPane.showMessageDialog(frame, 
                "Username atau password salah!", 
                "Login Gagal", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
});
    }

    public void run() {
        // Display the window
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}