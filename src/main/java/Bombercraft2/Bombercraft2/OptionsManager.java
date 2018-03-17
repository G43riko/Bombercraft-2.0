package Bombercraft2.Bombercraft2;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import utils.resouces.JSONAble;
import utils.resouces.ResourceLoader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class OptionsManager implements JSONAble {
    private final HashMap<String, Boolean> viewOptions = new HashMap<>();

    public void fromJSON(@NotNull JSONObject data) {
        try {
            Iterator<?> keys = data.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                viewOptions.put(key, data.getBoolean(key));
            }
        }
        catch (JSONException e) {
            initDefault();
        }
    }

    @NotNull
    public JSONObject toJSON() {
        JSONObject result = new JSONObject();

        try {
            for (Entry<String, Boolean> pair : viewOptions.entrySet()) {
                result.put(pair.getKey(), pair.getValue());
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void initDefault() {
        final JSONObject result = ResourceLoader.getJSON(Config.FILE_VISIBLE_OPTIONS);
        if (result != null) {
            fromJSON(result);
        }
    }

    public boolean getVisibleOption(String key) {
        return viewOptions.get(key);
    }

    public void switchVisibleOption(String key) {
        viewOptions.put(key, !viewOptions.get(key));
    }
}
