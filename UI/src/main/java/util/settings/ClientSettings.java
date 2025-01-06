package util.settings;

import org.json.JSONObject;

/**
 * Created: 28/08/2023 19:48
 * Author: Twitter @hawolt
 **/

public class ClientSettings extends DynamicSettings {
    public ClientSettings(JSONObject o, SettingService service) {
        super(o, service);
    }

    public boolean isRememberMe() {
        return getByKeyOrDefault("remember", false);
    }

    public String getRememberMeUsername() {
        return getByKeyNonNullOrThrow("username", () -> new RuntimeException("NO_USERNAME_PRESENT"));
    }

    public String getFriendHandling() {
        return getByKeyOrDefault("autoFriends", "User choice");
    }

    public int getClientVolumeGain() {
        return getByKeyOrDefault("Volume", 100);
    }

    public int getClientVolumeMixer() {
        return getByKeyOrDefault("MixerVolume", 100);
    }

    public int getClientTheme() {
        return getByKeyOrDefault("Theme", 0);
    }

    public String getWinePrefixDirectory() {
        return getByKeyOrDefault("WinePrefixDir", "");
    }

    public String getWineBinaryDirectory() {
        return getByKeyOrDefault("WineBinaryDir", "");
    }

    public String getDXVKVersion() {
        return getByKeyOrDefault("dxvkVersion", "2.3");
    }

    public boolean isMangoHudEnabled() {
        return getByKeyOrDefault("mangoHud", false);
    }

    public boolean isGameModeEnabled() {
        return getByKeyOrDefault("gameModeRun", false);
    }
}
