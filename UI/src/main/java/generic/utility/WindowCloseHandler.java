package generic.utility;

import generic.Client;
import generic.login.InternalLoginState;
import util.settings.ClientSettings;
import util.settings.SettingManager;
import util.settings.SettingService;
import util.settings.SettingType;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created: 22/08/2023 09:32
 * Author: Twitter @hawolt
 **/

public class WindowCloseHandler extends WindowAdapter {

    private boolean logoutButtonEnabled = false;
    private final Client client;

    public WindowCloseHandler(Client client) {
        this.client = client;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        if (logoutButtonEnabled) {
            int option = Client.showOptionDialog(
                    "Do you want to exit?",
                    "LOGOUT", "EXIT", "CANCEL"
            );
            if (option == 0) {
                logOut();
            }
            if (option == 1) client.getRoot().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        } else {
            int option = Client.showOptionDialog(
                    "Do you want to exit?",
                    "EXIT", "CANCEL"
            );
            if (option != 1) client.getRoot().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }

    public boolean isLogoutButtonEnabled() {
        return logoutButtonEnabled;
    }

    public void toggleLogoutButton() {
        this.logoutButtonEnabled = !logoutButtonEnabled;
    }

    private void logOut() {
        SettingService service = client.getSettingService();
        service.write(SettingType.CLIENT, "remember", false);
        service.write(SettingType.CLIENT, "username", "");
        client.getRoot().dispose();
        toggleLogoutButton();
        client.createLoginUI();
        client.getLoginUI().getRememberMe().setSelected(service.getClientSettings().isRememberMe());
        client.getLoginUI().toggle(InternalLoginState.LOADING);
        if (service.getClientSettings().isRememberMe()) {
            service.set(service.getClientSettings().getRememberMeUsername());
        }
        client.getLoginUI().toggle(InternalLoginState.LOGIN);
        client.getRoot().setVisible(true);
    }

}
