package generic.login;

import generic.component.LFlatButton;
import generic.component.LTextAlign;
import generic.themes.ColorPalette;
import generic.utility.ChildUIComponent;
import generic.utility.HighlightType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created: 08/08/2023 17:45
 * Author: Twitter @hawolt
 **/

public class SwiftFrameDecoration extends JPanel implements ActionListener {

    private final ChildUIComponent buttons;
    private LFlatButton minimize, close;
    private final Window window;
    private Point initialClick;

    public SwiftFrameDecoration(Window source) {
        this.buttons = new ChildUIComponent(new GridLayout(0, source instanceof JFrame ? 2 : 1, 3, 0));
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(320, 30));
        this.setBackground(ColorPalette.accentColor);
        this.add(buttons, BorderLayout.EAST);
        this.window = source;
        this.setup();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
                SwiftFrameDecoration.this.initialClick = e.getPoint();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(final MouseEvent e) {
                final int thisX = source.getLocation().x;
                final int thisY = source.getLocation().y;
                final int xMoved = e.getX() - SwiftFrameDecoration.this.initialClick.x;
                final int yMoved = e.getY() - SwiftFrameDecoration.this.initialClick.y;
                final int X = thisX + xMoved;
                final int Y = thisY + yMoved;
                source.setLocation(X, Y);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "—" -> {
                if (window instanceof Frame frame) {
                    frame.setState(JFrame.ICONIFIED);
                }
            }
            case "✖" -> {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }
        }
    }

    private void setup() {
        buttons.setBackground(ColorPalette.accentColor);
        if (window instanceof JFrame) {
            minimize = new LFlatButton("—", LTextAlign.CENTER, HighlightType.COMPONENT);
            minimize.addActionListener(this);
            buttons.add(minimize);
        }
        close = new LFlatButton("✖", LTextAlign.CENTER, HighlightType.COMPONENT);
        close.addActionListener(this);
        close.setHighlightColor(Color.RED);
        buttons.add(close);
    }

    public ChildUIComponent getButtonPanel() {
        return buttons;
    }

    public LFlatButton getMinimize() {
        return minimize;
    }

    public LFlatButton getClose() {
        return close;
    }
}
