package pbotubesmcd.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pbotubesmcd.components.DefaultButtonYellow;
import pbotubesmcd.enums.OrderType;
import pbotubesmcd.utils.Images;
import pbotubesmcd.utils.OrderSession;
import pbotubesmcd.utils.Router;
import pbotubesmcd.utils.UITheme;

public class HomeCustomerView extends JPanel {
    public HomeCustomerView(JPanel mainPanel, CardLayout cardLayout) {
        setBackground(UITheme.COLOR_MCD_RED);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        ImageIcon icon = new ImageIcon(getClass().getResource(Images.LOGO));

        Image image = icon.getImage().getScaledInstance(
                300,
                300,
                Image.SCALE_SMOOTH);

        JLabel lblLogo = new JLabel(new ImageIcon(image));
        centerPanel.add(lblLogo);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(0, 0, 80, 0));

        JButton btnDineIn = new DefaultButtonYellow("Makan di Tempat");
        JButton btnTakeAway = new DefaultButtonYellow("Bawa Pulang");

        bottomPanel.add(btnDineIn);
        bottomPanel.add(btnTakeAway);

        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        btnDineIn.addActionListener(e -> {
            OrderSession.setCurrentOrderType(OrderType.DINE_IN);
            cardLayout.show(mainPanel, Router.MAIN_ORDER);
        });

        btnTakeAway.addActionListener(e -> {
            OrderSession.setCurrentOrderType(OrderType.TAKE_AWAY);
            cardLayout.show(mainPanel, Router.MAIN_ORDER);
        });
    }
}
