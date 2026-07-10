package pbotubesmcd.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

        // Add action listener to the login button
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    public void run() {
        // Display the window
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter both username and password.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Database verification
        try (Connection conn = Database.connect()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(frame, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Adjust table and column names to match your schema if necessary
            String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String roleStr = rs.getString("role");
                        UserRole role = UserRole.valueOf(roleStr.toUpperCase());

                        JOptionPane.showMessageDialog(frame, "Login Successful! Role: " + role, "Success", JOptionPane.INFORMATION_MESSAGE);
                        
                        // Close login screen
                        frame.dispose();

                        // Redirect based on role
                        if (role == UserRole.ADMIN) {
                            System.out.println("Opening Admin Panel...");
                            // new AdminView().run(); (Instantiate your admin dashboard here)
                        } else if (role == UserRole.CUSTOMER) {
                            System.out.println("Opening Customer Dashboard...");
                            // new CustomerView().run(); (Instantiate your customer dashboard here)
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "SQL Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid role found in database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}