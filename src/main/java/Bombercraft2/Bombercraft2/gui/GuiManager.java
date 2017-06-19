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
	public String getLabelOf(String key, String ...args){
		try {
			if(args.length == 0){
				return data.getJSONObject(key).getString(lang);
			}
			String text = data.getJSONObject(key).getString(lang);
			for(int i=0 ; i<args.length ; i++){
				text = text.replaceFirst(Config.TEXT_ARG_PLACEHOLDER, args[i]);
			}
			return text;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "Undefined label";
	}
	public void onResize() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
