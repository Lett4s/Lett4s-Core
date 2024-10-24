package generic.utility;

import com.hawolt.SwiftRift;
import com.hawolt.util.settings.SettingManager;
import com.hawolt.util.settings.SettingService;
import com.hawolt.util.settings.SettingType;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created: 22/08/2023 09:32
 * Author: Twitter @hawolt
 **/

public class WindowCloseHandler extends WindowAdapter {

    private final SwiftRift swiftRift;

    public WindowCloseHandler(SwiftRift swiftRift) {
        this.swiftRift = swiftRift;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        if (swiftRift.getLeagueClient() == null) {
            swiftRift.getRoot().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        } else {
            int option = SwiftRift.showOptionDialog(
                    "Do you want to exit or logout?",
                    "LOGOUT", "EXIT", "CANCEL"
            );
            if (option == 0) {
                SettingService service = new SettingManager();
                service.write(SettingType.CLIENT, "remember", false);
            }
            if (option != 2) swiftRift.getRoot().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }
}
