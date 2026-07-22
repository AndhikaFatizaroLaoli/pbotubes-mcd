package pbotubesmcd.components;

import javax.swing.JTextField;

public class DefaultTextField extends JTextField {
    public DefaultTextField(int columns) {
        super(columns);
        ComponentStyle.applyInputStyle(this);
    }
}
