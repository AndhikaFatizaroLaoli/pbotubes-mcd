package pbotubesmcd.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import pbotubesmcd.utils.UITheme;

public class DefaultButtonWhite extends JButton {
    public DefaultButtonWhite(String text) {
        super(text);

        setFont(UITheme.FONT_BUTTON);
        setBackground(UITheme.COLOR_MCD_YELLOW);
        setForeground(Color.black);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.COLOR_MCD_YELLOW, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }
}
