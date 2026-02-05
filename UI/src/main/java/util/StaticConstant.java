package util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticConstant {

    // DYNAMIC CONSTANTS
    public static String PROJECT = "Client-Project";
    public static String USER_AGENT = "Client";
    public static String NAME = "CLIENT";
    public static String VERSION;

    // PREDEFINED CONSTANTS
    public static final String PLAYER_SETTING_fILE = ".user-settings";
    public static final String PROJECT_DATA = "project.json";
    public static final Path APPLICATION_SETTINGS = Paths.get(System.getProperty("user.home")).resolve(PROJECT);
    public static final String CLIENT_SETTING_fILE = ".client-settings";


}
