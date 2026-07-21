package pbotubesmcd.components;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import pbotubesmcd.utils.UITheme;

public class DefaultButtonYellow extends JButton {
    public DefaultButtonYellow(String text) {
        super(text);

        setFont(UITheme.FONT_BUTTON);
        setBackground(UITheme.COLOR_MCD_YELLOW);
        setForeground(Color.white);
        setBorder(new EmptyBorder(10, 20, 10, 20));
    }
}
