package Bombercraft2.Bombercraft2.gui;

import Bombercraft2.Bombercraft2.Config;
import org.json.JSONException;
import org.json.JSONObject;
import utils.resouces.ResourceLoader;

public class GuiManager {
    private String lang = Config.DEFAULT_LANGUAGE;
    JSONObject data;

    public GuiManager() {
        data = ResourceLoader.getJSON(Config.FILE_GUI_TEXTS);
    }

    public String getLabelOf(String key, String... args) {
        try {
            if (args.length == 0) {
                return data.getJSONObject(key).getString(lang);
            }
            String text = data.getJSONObject(key).getString(lang);
            for (String arg : args) {
                text = text.replaceFirst(Config.TEXT_ARG_PLACEHOLDER, arg);
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
