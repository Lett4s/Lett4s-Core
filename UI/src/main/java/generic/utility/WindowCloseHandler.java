package generic.utility;

import generic.ClientWindow;
import generic.dialog.ClientDialog;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created: 22/08/2023 09:32
 * Author: Twitter @hawolt
 **/

public class WindowCloseHandler extends WindowAdapter {

    private final ClientWindow clientWindow;

    public WindowCloseHandler(ClientWindow clientWindow) {
        this.clientWindow = clientWindow;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        int option = ClientWindow.showOptionDialog(
                "Do you want to exit?",
                "EXIT", "CANCEL"
        );
        if (option != 1) clientWindow.getRoot().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
