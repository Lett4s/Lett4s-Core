package generic.utility;

import generic.Client;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created: 22/08/2023 09:32
 * Author: Twitter @hawolt
 **/

public class WindowCloseHandler extends WindowAdapter {

    private final Client client;

    public WindowCloseHandler(Client client) {
        this.client = client;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        int option = Client.showOptionDialog(
                "Do you want to exit?",
                "EXIT", "CANCEL"
        );
        if (option != 1) client.getRoot().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
