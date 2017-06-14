package Bombercraft2.Bombercraft2.gui;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Config;
import utils.resouces.ResourceLoader;

public class GuiManager {
	private String lang = Config.DEFAULT_LANGUAGE;
	JSONObject data;
	
	public GuiManager(){
		data = ResourceLoader.getJSON(Config.FILE_GUI_TEXTS);
	}
	
	public String getLabelOf(String key){
		try {
			return data.getJSONObject(key).getString(lang);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "Undefined label";
	}

	public void onResize() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
