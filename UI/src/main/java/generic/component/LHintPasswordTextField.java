package generic.component;

import generic.themes.ColorPalette;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created: 01/09/2023 01:37
 * Author: Twitter @hawolt
 **/

public class LHintPasswordTextField extends JPasswordField implements PropertyChangeListener {
    private final String hint;

    public LHintPasswordTextField(String hint) {
        ColorPalette.addThemeListener(this);
        this.hint = hint;
        this.setForeground(Color.WHITE);
        this.setCaretColor(Color.WHITE);
        this.setBackground(ColorPalette.backgroundColor);
        this.setFont(new Font("Dialog", Font.PLAIN, 22));
        setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, ColorPalette.inputUnderline),
                new EmptyBorder(5, 5, 5, 5)
        ));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (!getText().isEmpty()) return;
        int height = getHeight();
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Insets insets = getInsets();
        FontMetrics fontMetrics = g.getFontMetrics();
        int background = getBackground().getRGB();
        int foreground = getForeground().getRGB();
        int mask = 0xFEFEFEFE;
        int blend = ((background & mask) >>> 1) + ((foreground & mask) >>> 1);
        g.setColor(new Color(blend, true));
        g.drawString(hint, insets.left, height / 2 + fontMetrics.getAscent() / 2 - 2);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        setBackground(ColorPalette.backgroundColor);
        setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, ColorPalette.inputUnderline),
                new EmptyBorder(5, 5, 5, 5)
        ));
    }
}
