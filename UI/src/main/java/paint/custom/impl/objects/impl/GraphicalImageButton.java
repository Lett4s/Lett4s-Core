package paint.custom.impl.objects.impl;

import paint.custom.impl.objects.AbstractGraphicalButton;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created: 08/09/2023 14:02
 * Author: Twitter @hawolt
 **/

public class GraphicalImageButton extends AbstractGraphicalButton {
    private BufferedImage image;

    public GraphicalImageButton() {

    }

    public GraphicalImageButton(Rectangle area) {
        super(area);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        if (area == null || image == null || !visible) return;
        if (enabled) {
            if (hold) {
                graphics2D.setColor(Color.DARK_GRAY);
            } else {
                graphics2D.setColor(hover ? Color.LIGHT_GRAY : Color.GRAY);
            }
        }
        graphics2D.fillRect(x, y, width, height);
        graphics2D.drawImage(image, x, y, null);
    }

}
