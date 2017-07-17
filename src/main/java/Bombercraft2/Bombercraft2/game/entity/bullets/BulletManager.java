package Bombercraft2.Bombercraft2.game.entity.bullets;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.core.Texts;

public class BulletManager {
	public enum Types{
		BASIC("basic"),
		LASER("laser");
		
		private String name;
		Types(String name){this.name = name;}
		public String getName(){return name;}
	}
	private static HashMap<Types, BulletModel> bullets = new HashMap<Types, BulletModel>();
	public static void init(JSONObject data){
		try {
			//TODO toto prerobit na tento loop hned ako bude BasicBullet pripraveny
//			Types[] types = Types.values();
//			for(int i=0 ; i<types.length ; i++){
//				bullets.put(types[i], new BulletModel(data.getJSONObject(types[i].getName())));
//			}
			bullets.put(Types.LASER, new BulletModel(data.getJSONObject(Types.LASER.getName())));
		} catch (JSONException e) {
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
