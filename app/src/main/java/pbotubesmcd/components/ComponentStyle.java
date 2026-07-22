package pbotubesmcd.components;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import pbotubesmcd.utils.UITheme;

public class ComponentStyle {
    public static void applyInputStyle(JTextField field) {
        field.setFont(UITheme.FONT_INPUT);

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.COLOR_MCD_YELLOW, 1, true),
                new EmptyBorder(8, 10, 8, 10)));
    }
}
