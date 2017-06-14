package Bombercraft2.Bombercraft2;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.gui.menus.ProfileMenu;
import utils.resouces.JSONAble;
import utils.resouces.ResourceLoader;

/*
 * kolko bolo minut hrania kolko bolo novych hier kolko krat bol profil nacitany
 * kedy bol naposledy ulozeny
 */
public class Profil implements JSONAble{
	private OptionsManager options;
	
	private final static String NAME 			= "name";
	private final static String AVATAR 			= "avatar";
	private final static String NEW_GAMES 		= "newGames";
	private final static String LAST_LOGIN 		= "lastLogin";
	private final static String PLAYING_TIME 	= "playingTime";
	private final static String PROFIL_LOADED	= "profilLoaded";
	
	private String	name			= "playerName";
	private String	avatar			= "player1.png";
	private String  profilName		= null;
	private float	msOfPlaying		= 0;
	private int		newGames		= 0;
	private int		profilLoaded	= 0;
	private float	lastLogin		= System.currentTimeMillis();
	
	// CONTRUCTORS
	public Profil(String profilName) {
		fromJSON(ResourceLoader.getJSON(Config.FOLDER_PROFILE + profilName + Config.EXTENSION_PROFILE));
		this.profilName = profilName;
	}
	private void initDefault(){
		name 			= Config.PROFILE_DEFAULT_NAME;
		avatar 			= Config.PROFILE_DEFAULT_AVATAR;
		newGames 		= 0;
		msOfPlaying 	= 0;
		profilLoaded 	= 0;
	}

	public void fromJSON(JSONObject profileData){
		options = new OptionsManager();
		try{options.fromJSON(profileData.getJSONObject("gameOptions"));}
		catch (Exception e) {options.initDefault();}
		
		try{
			JSONObject data = profileData.getJSONObject("userInfo");
			name 			= data.getString(NAME);
			avatar			= data.getString(AVATAR);
			newGames 		= data.getInt(NEW_GAMES);
			profilLoaded 	= data.getInt(PROFIL_LOADED);
			msOfPlaying 	= data.getInt(PLAYING_TIME);
		}
		catch(Exception e){
			e.printStackTrace();
			initDefault();
		}
		lastLogin = System.currentTimeMillis();
		profilLoaded++;
	}

	public JSONObject toJSON(){
		JSONObject result = new JSONObject();
		try {
			msOfPlaying += (System.currentTimeMillis() - lastLogin);
			result.put(NAME, name);
			result.put(AVATAR, avatar);
			result.put(NEW_GAMES, newGames);
			result.put(PLAYING_TIME, msOfPlaying);
			result.put(PROFIL_LOADED, profilLoaded);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	// OVERRIDES

	@Override
	public String toString() {
		return "name: " + name + ", avatar: " + avatar;
	}

	// GETTERS

	public int getNewGames() {return newGames;}
	public int getProfilLoaded() {return profilLoaded;}
	public float getLastLogin() {return lastLogin;}
	public float getMsOfPlaying() {return msOfPlaying;}
	public String getName() {return name;}
	public String getAvatar() {return avatar;}
	public OptionsManager getOptions(){return options;}

	public static void saveProfil(Profil profil) {
		JSONObject result = new JSONObject();
		try {
			result.put("userInfo", profil.toJSON());
			result.put("gameOptions", profil.options.toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			String fileName = Config.FOLDER_PROFILE + profil.profilName + Config.EXTENSION_PROFILE;
			BufferedWriter out = ResourceLoader.getBufferredWritter(fileName);
		    out.write(result.toString());  
		    out.close();
		}
		catch (IOException e){
		    e.printStackTrace();
		}
	}


}
;