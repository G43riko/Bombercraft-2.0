package Bombercraft2.Bombercraft2;



import java.io.BufferedWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import utils.Utils;
import utils.resouces.JSONAble;
import utils.resouces.ResourceLoader;

/*
 * kolko bolo minut hrania 
 * kolko bolo novych hier 
 * kolko krat bol profil nacitany
 * kedy bol naposledy ulozeny
 */
public class Profil implements JSONAble{
	
	private final static String NAME 			= "name";
	private final static String AVATAR 			= "avatar";
	private final static String NEW_GAMES 		= "newGames";
	private final static String LAST_LOGIN 		= "lastLogin";
	private final static String PLAYING_TIME 	= "playingTime";
	private final static String PROFIL_LOADED	= "profilLoaded";
	public static final String 	GUEST 			= "GUEST_PROFILE";

	private OptionsManager 	options;
	private String			name			= "playerName";
	private String			avatar			= "player1.png";
	private String  		profilName		= null;
	private float			msOfPlaying		= 0;
	private int				newGames		= 0;
	private int				profilLoaded	= 0;
	private float			lastLogin		= System.currentTimeMillis();
	
	// CONTRUCTORS
	public Profil(String profilName) {
		this.profilName = profilName;
		if(profilName.equals(GUEST)){
			initDefaultProfile();
			return;
		}
		try {
			fromJSON(ResourceLoader.getJSONThrowing(Config.FOLDER_PROFILE + profilName + Config.EXTENSION_PROFILE));
		} catch (JSONException e) {
			try {
				JSONObject data = new JSONObject();
				data.put("userInfo", new JSONObject());
//				data.put("gameOptions", new JSONObject());
				fromJSON(data);
			} catch (JSONException ee) {
				ee.printStackTrace();
			}
		}
	}
	private void initDefault(){
		name 			= Config.PROFILE_DEFAULT_NAME;
		avatar 			= Config.PROFILE_DEFAULT_AVATAR;
		newGames 		= 0;
		msOfPlaying 	= 0;
		profilLoaded 	= 0;
	}
	
	private void initDefaultProfile(){
		options = new OptionsManager();
		options.initDefault();
		initDefault();
		name = Utils.getHostName();
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
			result.put(LAST_LOGIN, lastLogin);
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

	public String 			getName() {return name;}
	public String 			getAvatar() {return avatar;}
	public OptionsManager 	getOptions() {return options;}
	public int 				getNewGames() {return newGames;}
	public float 			getLastLogin() {return lastLogin;}
	public int 				getProfilLoaded() {return profilLoaded;}
	public float 			getMsOfPlaying() {return msOfPlaying;}

	public static void saveProfil(Profil profil) {
		if(profil.profilName.equals(GUEST)){
			return;
		}
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