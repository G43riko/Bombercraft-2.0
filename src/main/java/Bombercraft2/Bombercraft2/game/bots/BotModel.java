package Bombercraft2.Bombercraft2.game.bots;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.core.Texts;

public class BotModel {
	private int speed;
	private int maxHealt;
	private int demage;
	private BotManager.Types type;
	
	public BotModel(JSONObject data) {
		try{
			this.speed			= data.getInt(Texts.SPEED);
			this.demage			= data.getInt(Texts.DAMAGE);
			this.maxHealt		= data.getInt(Texts.HEALTH);
			this.type 			= BotManager.Types.valueOf(data.getString(Texts.TYPE));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public int getSpeed() {return speed;}
	public int getMaxHealt() {return maxHealt;}
	public int getDemage() {return demage;}
	public BotManager.Types getType() {return type;}
	

}
