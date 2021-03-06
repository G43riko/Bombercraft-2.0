package org.bombercraft2.gui;

import org.bombercraft2.StaticConfig;
import org.json.JSONException;
import org.json.JSONObject;
import org.utils.resources.ResourceUtils;

public class GuiManager {
    private final String     lang = StaticConfig.DEFAULT_LANGUAGE;
    private final JSONObject data;

    public GuiManager() {
        data = ResourceUtils.getJSON(StaticConfig.FILE_GUI_TEXTS);
    }

    public String getLabelOf(String key, String... args) {
        try {
            if (args.length == 0) {
                return data.getJSONObject(key).getString(lang);
            }
            String text = data.getJSONObject(key).getString(lang);
            for (String arg : args) {
                text = text.replaceFirst(StaticConfig.TEXT_ARG_PLACEHOLDER, arg);
            }
            return text;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return "Undefined label";
    }

    public void onResize() {
        // TODO Auto-generated method stub

    }


}
