package pbotubesmcd.components;

import javax.swing.JLabel;

import pbotubesmcd.utils.UITheme;

public class DefaultTitle extends JLabel {
    public DefaultTitle(String text) {
        super(text);
        setFont(UITheme.FONT_TITLE);
    }
}
