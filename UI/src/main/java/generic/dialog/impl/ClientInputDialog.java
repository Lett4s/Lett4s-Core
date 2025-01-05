package generic.dialog.impl;

import generic.component.LHintTextField;
import generic.component.SwiftLabel;
import generic.dialog.ClientDialog;
import generic.utility.ChildUIComponent;

import java.awt.*;

/**
 * Created: 27/09/2023 00:25
 * Author: Twitter @hawolt
 **/

public class ClientInputDialog extends ClientDialog {
    private final LHintTextField input;

    public ClientInputDialog(String message) {
        this(null, message);
    }

    public ClientInputDialog(Frame frame, String message) {
        this(frame, message, "OK", "CANCEL");
    }

    public ClientInputDialog(Frame frame, String message, String... options) {
        super(frame, options);
        String builder = "<html><div style='text-align: center;'>" + message + "</div></html>";
        container.add(new SwiftLabel(builder), BorderLayout.NORTH);
        this.input = new LHintTextField("");
        container.add(input, BorderLayout.CENTER);
    }

    @Override
    protected ChildUIComponent getContainer() {
        return new ChildUIComponent(new BorderLayout());
    }

    public String getInput() {
        return input.getText();
    }
}
