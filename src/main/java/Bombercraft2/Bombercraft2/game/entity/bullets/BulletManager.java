package Bombercraft2.Bombercraft2.game.entity.bullets;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class BulletManager {
    public enum Types {
        BASIC("basic"),
        LASER("laser");

        private final String name;

        Types(String name) {this.name = name;}

        String getName() {return name;}
    }

    private static final HashMap<Types, BulletModel> bullets = new HashMap<>();

    public static void init(JSONObject data) {
        try {
            //TODO toto prerobit na tento loop hned ako bude BasicBullet pripraveny
//			Types[] types = Types.values();
//			for(int i=0 ; i<types.length ; i++){
//				bullets.put(types[i], new BulletModel(data.getJSONObject(types[i].getName())));
//			}
            bullets.put(Types.LASER, new BulletModel(data.getJSONObject(Types.LASER.getName())));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static BulletModel getBulletModel(Types type) {
        return bullets.get(type);
    }

    public static BulletModel getBulletModel(String type) {
        return bullets.get(Types.valueOf(type));
    }
}
