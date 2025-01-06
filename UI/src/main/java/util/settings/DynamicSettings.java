package util.settings;

import org.json.JSONObject;
import util.DynamicObject;

/**
 * Created: 27/09/2023 16:45
 * Author: Twitter @hawolt
 **/

public class DynamicSettings extends DynamicObject {
    protected final SettingService service;

    public DynamicSettings(JSONObject o, SettingService service) {
        super(o);
        this.service = service;
    }
}
