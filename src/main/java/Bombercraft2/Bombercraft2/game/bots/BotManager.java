package Bombercraft2.Bombercraft2.game.bots;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class BotManager {
    public enum Types {
        A("a");
        private final String name;

        Types(String name) {this.name = name;}

        public String getName() {return name;}
    }

    private static final HashMap<Types, BotModel> bots = new HashMap<>();

    public static void init(JSONObject data) {
        try {
            Types[] types = Types.values();
            for (Types type : types) {
                bots.put(Types.A, new BotModel(data.getJSONObject(type.name)));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static BotModel getBotModel(Types type) {
        return bots.get(type);
    }

    public static BotModel getBotModel(String type) {
        return bots.get(Types.valueOf(type));
    }
}
