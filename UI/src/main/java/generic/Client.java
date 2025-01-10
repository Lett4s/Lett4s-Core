package generic;

import generic.dialog.ClientDialog;
import generic.utility.WindowCloseHandler;
import util.ExecutorManager;
import util.settings.SettingService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client implements WindowStateListener {

    public static final ExecutorService service = ExecutorManager.registerService("pool", Executors.newCachedThreadPool());
    private final WindowCloseHandler windowCloseHandler;
    private SettingService settingService;
    private final JFrame root;

    public Client(String title) {
        this.root = new JFrame();
        this.root.setTitle(title);
        this.root.addWindowStateListener(this);
        this.root.addWindowListener(windowCloseHandler = new WindowCloseHandler(this));
        this.root.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    @Override
    public void windowStateChanged(WindowEvent e) {
        if (e.getNewState() == Frame.MAXIMIZED_BOTH) {

        }
    }

    public static Frame getMainParent() {
        Frame[] frames = Frame.getFrames();
        return frames.length > 0 ? frames[0] : null;
    }

    public static int showMessageDialog(String... lines) {
        return ClientDialog.showMessageDialog(getMainParent(), lines);
    }

    public static int showOptionDialog(String message, String... options) {
        return ClientDialog.showOptionDialog(getMainParent(), message, options);
    }

    public static int showOptionDialog(String[] messages, String... options) {
        return ClientDialog.showOptionDialog(getMainParent(), messages, options);
    }

    public static String showComboOptionDialog(String message, String... options) {
        return ClientDialog.showComboOptionDialog(getMainParent(), message, options);
    }

    public static String showComboOptionDialog(String[] messages, String... options) {
        return ClientDialog.showComboOptionDialog(getMainParent(), messages, options);
    }

    public static String showInputDialog(String message) {
        return ClientDialog.showInputDialog(getMainParent(), message);
    }

    public JFrame getRoot() {
        return root;
    }

    public WindowCloseHandler getWindowCloseHandler() {
        return windowCloseHandler;
    }

    public SettingService getSettingService() {
        return settingService;
    }

    public void setSettingService(SettingService settingService) {
        this.settingService = settingService;
    }

}
