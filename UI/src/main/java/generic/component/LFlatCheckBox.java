package generic.component;

import javax.swing.*;
import java.awt.*;

public class LFlatCheckBox extends JCheckBox {

    private static final int BORDER = 2;

    public LFlatCheckBox(String text) {
        super(text);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setOpaque(false);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int height = getHeight();
        int ly = (height - 16) / 2;

        if (this.isSelected()) {
            g2d.setColor(this.isEnabled() ? this.getBackground() : Color.GRAY);
            g2d.fillRoundRect(1, ly, 16, 16, BORDER, BORDER);

            int[] px = {4, 8, 14, 12, 8, 6};
            int[] py = {ly + 8, ly + 14, ly + 5, ly + 3, ly + 10, ly + 6};
            g2d.setColor(Color.WHITE);
            g2d.fillPolygon(px, py, px.length);
        } else {
            g2d.setColor(Color.WHITE);
            g2d.fillRoundRect(2, ly + 1, 14, 14, BORDER, BORDER);
        }

        g2d.dispose();
    }
}