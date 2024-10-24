package generic.dialog.impl;

import com.hawolt.ui.generic.component.LComboBox;
import com.hawolt.ui.generic.component.LFlatButton;
import com.hawolt.ui.generic.component.LTextAlign;
import com.hawolt.ui.generic.component.SwiftLabel;
import com.hawolt.ui.generic.dialog.SwiftDialog;
import com.hawolt.ui.generic.utility.ChildUIComponent;
import com.hawolt.ui.generic.utility.HighlightType;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created: 27/09/2023 00:25
 * Author: Twitter @hawolt
 **/

public class SwiftComboOptionDialog extends SwiftDialog {
    private final LComboBox<String> box;

    public SwiftComboOptionDialog(String message, String... options) {
        this(null, message, options);
    }

    public SwiftComboOptionDialog(Frame frame, String message, String... options) {
        this(frame, new String[]{message}, options);
    }

    public SwiftComboOptionDialog(Frame frame, String[] lines, String... options) {
        super(frame, new String[0]);
        StringBuilder builder = new StringBuilder();
        builder.append("<html><div style='text-align: center;'>");
        for (int i = 0; i < lines.length; i++) {
            if (i != 0) builder.append("<br>");
            String message = lines[i];
            builder.append(message);
        }
        builder.append("</div></html>");
        container.add(new SwiftLabel(builder.toString()), BorderLayout.CENTER);
        ChildUIComponent selection = new ChildUIComponent(new GridLayout(0, 1, 0, 5));
        selection.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, Color.DARK_GRAY),
                new EmptyBorder(5, 5, 5, 5)
        ));
        LFlatButton button = new LFlatButton("Select", LTextAlign.CENTER, HighlightType.COMPONENT);
        box = new LComboBox<>(options);
        box.addItemListener(listener -> button.setActionCommand(String.valueOf(box.getSelectedIndex())));
        button.addActionListener(this);
        selection.add(box);
        selection.add(button);
        this.main.add(selection, BorderLayout.SOUTH);
    }

    @Override
    protected ChildUIComponent getContainer() {
        return new ChildUIComponent(new BorderLayout());
    }

    public String getSelectedItem() {
        return box.getItemAt(box.getSelectedIndex());
    }
}
