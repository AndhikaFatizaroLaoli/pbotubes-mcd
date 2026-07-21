package pbotubesmcd.components;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import pbotubesmcd.utils.UITheme;

public class DefaultTable extends JTable {
    public DefaultTable(TableModel model) {
        super(model);
        setFont(UITheme.FONT_INPUT);
        setRowHeight(25);
        getTableHeader().setFont(UITheme.FONT_LABEL);
        getTableHeader().setBackground(UITheme.COLOR_MCD_YELLOW);
    }
}
