package generic.dialog.impl;

import generic.component.SwiftLabel;
import generic.dialog.DialogOption;
import generic.dialog.ClientDialog;
import generic.utility.ChildUIComponent;

import java.awt.*;

/**
 * Created: 27/09/2023 00:25
 * Author: Twitter @hawolt
 **/

public class ClientNotificationDialog extends ClientDialog {
    public ClientNotificationDialog(String... lines) {
        this(null, lines);
    }

    public ClientNotificationDialog(Frame frame, String... lines) {
        super(frame, DialogOption.OK);
        StringBuilder builder = new StringBuilder();
        builder.append("<html><div style='text-align: center;'>");
        for (int i = 0; i < lines.length; i++) {
            if (i != 0) builder.append("<br>");
            String message = lines[i];
            builder.append(message);
        }
        builder.append("</div></html>");
        container.add(new SwiftLabel(builder.toString()), BorderLayout.CENTER);
    }

    @Override
    protected ChildUIComponent getContainer() {
        return new ChildUIComponent(new BorderLayout());
    }
}
