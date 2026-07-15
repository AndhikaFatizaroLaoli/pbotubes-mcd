package pbotubesmcd.components;

import javax.swing.JPasswordField;

public class DefaultPasswordField extends JPasswordField {
    public DefaultPasswordField(int columns) {
        super(columns);
        ComponentStyle.applyInputStyle(this);
    }
}
