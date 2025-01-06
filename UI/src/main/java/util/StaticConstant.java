package util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticConstant {
    public static final String NAME = "CLIENT";
    public static final String PROJECT = "Client-Project";

    // PREDEFINED CONSTANTS
    public static final String PROJECT_DATA = "project.json";
    public static final Path APPLICATION_SETTINGS = Paths.get(System.getProperty("user.home")).resolve(PROJECT);
    public static final String CLIENT_SETTING_fILE = ".client-settings";
    public static final String PLAYER_SETTING_fILE = ".user-settings";
    public static final String USER_AGENT = "Client";
    // DYNAMIC CONSTANTS
    public static String VERSION;

}
