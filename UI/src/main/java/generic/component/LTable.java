package generic.component;

import com.hawolt.ui.generic.themes.ColorPalette;
import com.hawolt.ui.generic.themes.impl.LThemeChoice;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LTable extends JTable implements PropertyChangeListener {

    public LTable(TableModel model) {
        super(model);
        init();
    }

    private void init() {
        ColorPalette.addThemeListener(this);
        setBackground(ColorPalette.accentColor);
        setForeground(ColorPalette.textColor);
        getTableHeader().setOpaque(false);
        getTableHeader().setBackground(ColorPalette.accentColor);
        getTableHeader().setForeground(ColorPalette.textColor);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LThemeChoice old = (LThemeChoice) evt.getOldValue();
        setBackground(ColorPalette.getNewColor(getBackground(), old));
        setForeground(ColorPalette.getNewColor(getForeground(), old));
        getTableHeader().setBackground(ColorPalette.getNewColor(getTableHeader().getBackground(), old));
        getTableHeader().setForeground(ColorPalette.getNewColor(getTableHeader().getForeground(), old));
    }
}
