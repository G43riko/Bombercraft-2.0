package org.bombercraft2.game.bots;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class BotFactory {
    private static final HashMap<Types, BotPrototype> bots = new HashMap<>();

    public static void init(JSONObject data) {
        try {
            Types[] types = Types.values();
            for (Types type : types) {
                bots.put(type, new BotPrototype(data.getJSONObject(type.name)));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static BotPrototype getBotPrototype(Types type) {
        return bots.get(type);
    }

    public static BotPrototype getBotPrototype(String type) {
        return bots.get(Types.valueOf(type));
    }

    public enum Types {
        A("a"),
        WORKER("worker");
        private final String name;

        Types(String name) {this.name = name;}

        public String getName() {return name;}
    }
}
