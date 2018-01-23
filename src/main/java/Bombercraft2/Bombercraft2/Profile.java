package Bombercraft2.Bombercraft2;


import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import utils.GLogger;
import utils.Utils;
import utils.resouces.JSONAble;
import utils.resouces.ResourceLoader;

import java.io.BufferedWriter;
import java.io.IOException;

/*
 * kolko bolo minut hrania 
 * kolko bolo novych hier 
 * kolko krat bol profil nacitany
 * kedy bol naposledy ulozeny
 */
public class Profile implements JSONAble {
    private final static String NAME           = "name";
    private final static String AVATAR         = "avatar";
    private final static String NEW_GAMES      = "newGames";
    private final static String LAST_LOGIN     = "lastLogin";
    private final static String PLAYING_TIME   = "playingTime";
    private final static String PROFILE_LOADED = "profileLoaded";
    public static final  String GUEST          = "GUEST_PROFILE";

    private OptionsManager options;
    private String name          = "playerName";
    private String avatar        = "player1.png";
    private final String profileName;
    private float  msOfPlaying   = 0;
    private int    newGames      = 0;
    private int    profileLoaded = 0;
    private float  lastLogin     = System.currentTimeMillis();

    // CONSTRUCTORS
    public Profile(@NotNull String profileName) {
        this.profileName = profileName;
        if (profileName.equals(GUEST)) {
            initDefaultProfile();
            return;
        }
        try {
            fromJSON(ResourceLoader.getJSONThrowing(Config.FOLDER_PROFILE + profileName + Config.EXTENSION_PROFILE));
            GLogger.log(GLogger.GLog.PROFILE_LOADED);
        }
        catch (JSONException e) {
            GLogger.error(GLogger.GError.CANNON_PARSE_PROFILE, profileName);
            try {
                JSONObject data = new JSONObject();
                data.put("userInfo", new JSONObject());
//				data.put("gameOptions", new JSONObject());
                fromJSON(data);
            }
            catch (JSONException ee) {
                ee.printStackTrace();
            }
        }
    }

    private void initDefault() {
        name = Config.PROFILE_DEFAULT_NAME;
        avatar = Config.PROFILE_DEFAULT_AVATAR;
        newGames = 0;
        msOfPlaying = 0;
        profileLoaded = 0;
    }

    private void initDefaultProfile() {
        options = new OptionsManager();
        options.initDefault();
        initDefault();
        name = Utils.getHostName();
    }

    public void fromJSON(JSONObject profileData) {
        options = new OptionsManager();
        try {options.fromJSON(profileData.getJSONObject("gameOptions"));}
        catch (Exception e) {options.initDefault();}

        try {
            JSONObject data = profileData.getJSONObject("userInfo");
            name = data.getString(NAME);
            avatar = data.getString(AVATAR);
            newGames = data.getInt(NEW_GAMES);
            profileLoaded = data.getInt(PROFILE_LOADED);
            msOfPlaying = data.getInt(PLAYING_TIME);
        }
        catch (Exception e) {
            initDefault();
        }
        lastLogin = System.currentTimeMillis();
        profileLoaded++;
    }

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        try {
            msOfPlaying += (System.currentTimeMillis() - lastLogin);
            result.put(NAME, name);
            result.put(AVATAR, avatar);
            result.put(NEW_GAMES, newGames);
            result.put(LAST_LOGIN, lastLogin);
            result.put(PLAYING_TIME, msOfPlaying);
            result.put(PROFILE_LOADED, profileLoaded);
        }
        catch (JSONException e) {
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

    public String getName() {return name;}

    public String getAvatar() {return avatar;}

    public OptionsManager getOptions() {return options;}

    public int getNewGames() {return newGames;}

    public float getLastLogin() {return lastLogin;}

    public int getProfileLoaded() {return profileLoaded;}

    public float getMsOfPlaying() {return msOfPlaying;}

    public static void saveProfile(@NotNull Profile profile) {
        if (profile.profileName.equals(GUEST)) {
            return;
        }
        JSONObject result = new JSONObject();
        try {
            result.put("userInfo", profile.toJSON());
            result.put("gameOptions", profile.options.toJSON());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String fileName = Config.FOLDER_PROFILE + profile.profileName + Config.EXTENSION_PROFILE;
            BufferedWriter out = ResourceLoader.getBufferedWriter(fileName);
            out.write(result.toString());
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
