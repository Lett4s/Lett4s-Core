package generic.component;

import generic.themes.ColorPalette;
import generic.themes.impl.LThemeChoice;
import generic.themes.impl.LThemeChoice;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LTextField extends JTextField implements PropertyChangeListener {

    public LTextField(String text) {
        setText(text);
        init();
    }

    private void init() {
        ColorPalette.addThemeListener(this);
        setBackground(ColorPalette.accentColor);
        setForeground(ColorPalette.textColor);
        setEditable(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LThemeChoice old = (LThemeChoice) evt.getOldValue();
        setBackground(ColorPalette.getNewColor(getBackground(), old));
        setForeground(ColorPalette.getNewColor(getForeground(), old));
    }
}
