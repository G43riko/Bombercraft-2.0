package org.bombercraft2;


import org.glib2.interfaces.JSONAble;
import org.glib2.network.InternetUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import utils.GLogger;
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
    public static final  String         GUEST          = "GUEST_PROFILE";
    private final static String         DEFAULT_NAME   = "name";
    private final static String         DEFAULT_AVATAR = "avatar";
    private final static String         NEW_GAMES      = "newGames";
    private final static String         LAST_LOGIN     = "lastLogin";
    private final static String         PLAYING_TIME   = "playingTime";
    private final static String         PROFILE_LOADED = "profileLoaded";
    private final        String         profileName;
    private              OptionsManager options;
    private              String         name           = "playerName";
    private              String         avatar         = "player1.png";
    private              float          msOfPlaying    = 0;
    private              int            newGames       = 0;
    private              int            profileLoaded  = 0;
    private              float          lastLogin      = System.currentTimeMillis();

    // CONSTRUCTORS
    public Profile(@NotNull String profileName) {
        this.profileName = profileName;
        if (profileName.equals(GUEST)) {
            initDefaultProfile();
            return;
        }
        try {
            fromJSON(ResourceLoader.getJSONThrowing(StaticConfig.FOLDER_PROFILE + profileName + StaticConfig.EXTENSION_PROFILE));
            GLogger.log(GLogger.GLog.PROFILE_SUCCESSFULLY_LOADED);
        }
        catch (JSONException e) {
            GLogger.error(GLogger.GError.PROFILE_PARSING_FAILED, profileName);
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
            String fileName = StaticConfig.FOLDER_PROFILE + profile.profileName + StaticConfig.EXTENSION_PROFILE;
            BufferedWriter out = ResourceLoader.getBufferedWriter(fileName);
            if (out != null) {
                out.write(result.toString());
                out.close();
                GLogger.log(GLogger.GLog.PROFILE_SUCCESSFULLY_SAVED);
            }
        }
        catch (IOException e) {
            GLogger.error(GLogger.GError.PROFILE_SAVING_FAILED, profile.profileName);
        }
    }

    private void initDefault() {
        name = StaticConfig.PROFILE_DEFAULT_NAME;
        avatar = StaticConfig.PROFILE_DEFAULT_AVATAR;
        newGames = 0;
        msOfPlaying = 0;
        profileLoaded = 0;
    }

    private void initDefaultProfile() {
        options = new OptionsManager();
        options.initDefault();
        initDefault();
        name = InternetUtils.getHostName();
    }

    public void fromJSON(@NotNull JSONObject profileData) {
        options = new OptionsManager();
        try {options.fromJSON(profileData.getJSONObject("gameOptions"));}
        catch (Exception e) {options.initDefault();}

        try {
            JSONObject data = profileData.getJSONObject("userInfo");
            name = data.getString(DEFAULT_NAME);
            avatar = data.getString(DEFAULT_AVATAR);
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
    // OVERRIDES

    @NotNull
    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        try {
            msOfPlaying += (System.currentTimeMillis() - lastLogin);
            result.put(DEFAULT_NAME, name);
            result.put(DEFAULT_AVATAR, avatar);
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

    // GETTERS

    @Override
    public String toString() {
        return "name: " + name + ", avatar: " + avatar;
    }

    public String getName() {return name;}

    public String getAvatar() {return avatar;}

    public OptionsManager getOptions() {return options;}

    public int getNewGames() {return newGames;}

    public float getLastLogin() {return lastLogin;}

    public int getProfileLoaded() {return profileLoaded;}

    public float getMsOfPlaying() {return msOfPlaying;}
}
