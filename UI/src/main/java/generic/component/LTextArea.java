package generic.component;

import generic.themes.ColorPalette;
import generic.themes.impl.LThemeChoice;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LTextArea extends JTextArea implements PropertyChangeListener {

    public LTextArea() {
        init();
    }

    private void init() {
        ColorPalette.addThemeListener(this);
        setFont(new Font("Dialog", Font.BOLD, 14));
        setBackground(ColorPalette.backgroundColor);
        setForeground(ColorPalette.textColor);
        setEditable(false);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LThemeChoice old = (LThemeChoice) evt.getOldValue();
        setBackground(ColorPalette.getNewColor(getBackground(), old));
        setForeground(ColorPalette.getNewColor(getForeground(), old));
    }
}
