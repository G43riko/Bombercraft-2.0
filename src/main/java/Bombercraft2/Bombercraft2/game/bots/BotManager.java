package Bombercraft2.Bombercraft2.game.bots;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class BotManager {
	public enum Types{
		A("a");
		private String name;
		Types(String name){this.name = name;}
		public String getName(){return name;}
	}
	private static HashMap<Types, BotModel> bots = new HashMap<Types, BotModel>();

	public static void init(JSONObject data){
		try{
			Types[] types = Types.values();
			for(int i=0 ; i<types.length ; i++){
				bots.put(Types.A, new BotModel(data.getJSONObject(types[i].name)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public static BotModel getBotModel(Types type){
		return bots.get(type);
	}
	public static BotModel getBotModel(String type){
		return bots.get(Types.valueOf(type));
	}
}
