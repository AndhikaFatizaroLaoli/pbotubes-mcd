package pbotubesmcd.components;

import javax.swing.JLabel;

import pbotubesmcd.utils.UITheme;

public class DefaultLabel extends JLabel {
    public DefaultLabel(String text) {
        super(text);
        setFont(UITheme.FONT_LABEL);
    }
}
