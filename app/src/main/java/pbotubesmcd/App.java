package pbotubesmcd;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pbotubesmcd.utils.Router;
import pbotubesmcd.views.HomeAdminView;
import pbotubesmcd.views.HomeCustomerView;
import pbotubesmcd.views.LoginView;

public class App extends JFrame {

    public App() {
        setTitle("McDonald's Self Order");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.green);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        LoginView loginView = new LoginView(mainPanel, cardLayout);
        HomeAdminView homeAdminView = new HomeAdminView(mainPanel, cardLayout);
        HomeCustomerView homeCustomerView = new HomeCustomerView(mainPanel, cardLayout);

        mainPanel.add(loginView, Router.LOGIN);
        mainPanel.add(homeAdminView, Router.HOME_ADMIN);
        mainPanel.add(homeCustomerView, Router.HOME_CUSTOMER);

        add(mainPanel);

        cardLayout.show(mainPanel, Router.LOGIN);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new App().setVisible(true);
        });
    }
}