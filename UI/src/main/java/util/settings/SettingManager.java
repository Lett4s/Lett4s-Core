package util.settings;

import generic.ClientWindow;
import logger.Logger;
import org.json.JSONObject;
import util.DynamicObject;
import util.StaticConstant;
import util.Unsafe;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created: 28/08/2023 18:55
 * Author: Twitter @hawolt
 **/

public class SettingManager implements SettingService {
    private final Map<String, List<SettingListener<?>>> map = new HashMap<>();
    private final ClientSettings client;
    private UserSettings player;
    private String username;

    public SettingManager() {
        this.client = new ClientSettings(load(SettingType.CLIENT), this);
    }

    @Override
    public UserSettings set(String username) {
        this.username = username;
        this.player = new UserSettings(load(SettingType.PLAYER), this);
        return player;
    }

    @Override
    public ClientSettings getClientSettings() {
        return client;
    }

    @Override
    public void write(SettingType type, String name, Object o) {
        Logger.debug("[settings] write '{}' for '{}' as '{}'", name, type, o == null ? "null" : o);
        DynamicObject object = switch (type) {
            case CLIENT -> client;
            case PLAYER -> player;
        };
        object.put(name, o);
        switch (type) {
            case CLIENT -> write(SettingType.CLIENT);
            case PLAYER -> write(SettingType.PLAYER);
        }
        this.dispatch(name, o);
    }

    @Override
    public void addSettingListener(String name, SettingListener<?> listener) {
        Logger.debug("[settings] add '{}' listener with source '{}'", name, listener.getClass().getCanonicalName());
        if (!map.containsKey(name)) map.put(name, new ArrayList<>());
        this.map.get(name).add(listener);
    }

    private void dispatch(String name, Object value) {
        Logger.debug("[settings] dispatch '{}' as '{}'", name, value == null ? "null" : value);
        List<SettingListener<?>> list = map.get(name);
        if (list == null) return;
        for (SettingListener<?> listener : list) {
            ClientWindow.service.execute(() -> listener.onSettingWrite(name, Unsafe.cast(value)));
        }
    }

    @Override
    public UserSettings getUserSettings() {
        return player;
    }

    private JSONObject load(SettingType type) {
        Logger.debug("[settings] load local setting for:{}", type);
        Path path = switch (type) {
            case CLIENT -> StaticConstant.APPLICATION_SETTINGS.resolve(StaticConstant.CLIENT_SETTING_fILE);
            case PLAYER ->
                    StaticConstant.APPLICATION_SETTINGS.resolve(username).resolve(StaticConstant.PLAYER_SETTING_fILE);
        };
        try {
            return new JSONObject(new String(Files.readAllBytes(path)));
        } catch (IOException e) {
            Logger.info("Unable to locate {}-setting file", type.name().toLowerCase(Locale.ENGLISH), path.toFile());
        }
        return new JSONObject();
    }

    private void write(SettingType type) {
        Logger.debug("[settings] write local setting for:{}", type);
        Path path = switch (type) {
            case CLIENT -> StaticConstant.APPLICATION_SETTINGS.resolve(StaticConstant.CLIENT_SETTING_fILE);
            case PLAYER ->
                    StaticConstant.APPLICATION_SETTINGS.resolve(username).resolve(StaticConstant.PLAYER_SETTING_fILE);
        };
        try {
            Files.createDirectories(path.getParent());
            byte[] content = switch (type) {
                case CLIENT -> client.toString().getBytes(StandardCharsets.UTF_8);
                case PLAYER -> player.toString().getBytes(StandardCharsets.UTF_8);
            };
            Files.write(
                    path,
                    content,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            Logger.info("Unable to create directory {}", path.toFile());
        }
    }
}
