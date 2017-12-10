package utils.resouces;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.gui.menus.ProfileMenu;
import utils.AudioPlayer;
import utils.GLogger;

public final class ResourceLoader {
    private static final HashMap<String, Image> loadedTextures = new HashMap<>();
    private static final SoundManager           soundManager   = new SoundManager();

    public static InputStream load(String fileName) {
        InputStream input = ResourceLoader.class.getResourceAsStream(fileName);
        if (input == null) {
            input = ResourceLoader.class.getResourceAsStream("/" + fileName);
        }
        if (input == null) {
            input = ResourceLoader.class.getResourceAsStream("./" + fileName);
        }
        if (input == null) {
            input = ResourceLoader.class.getResourceAsStream("res" + fileName);
        }
        if (input == null) {
            input = ResourceLoader.class.getResourceAsStream("/res" + fileName);
        }
        if (input == null) {
            input = ResourceLoader.class.getResourceAsStream("./res" + fileName);
        }
        return input;
    }

    private static FileWriter getFileWriter(String fileName) {
        try {
            return new FileWriter(new File(ProfileMenu.class.getResource(fileName).toURI()));
        }
        catch (URISyntaxException | IOException e) {
            GLogger.error(GLogger.GError.CANNON_CREATE_FILEWRITTER, e, fileName);
        }
        return null;
    }

    public static BufferedWriter getBufferedWriter(String fileName) {
        return new BufferedWriter(getFileWriter(fileName));
    }

    public static Image loadTexture(String fileName) {
        if (loadedTextures.containsKey(fileName)) { return loadedTextures.get(fileName); }
        else {
            try {
                loadedTextures.put(fileName, ImageIO.read(load("/images/materials/" + fileName)));
                GLogger.log(GLogger.GLog.TEXTURE_LOAD, fileName);
            }
            catch (IOException e) {
                GLogger.error(GLogger.GError.CANNON_READ_TEXTURE, e, fileName);
            }
            return loadedTextures.get(fileName);
        }
    }

    private static String getFileContent(String name) {
        BufferedReader buf = new BufferedReader(new InputStreamReader(load(name)));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            line = buf.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            GLogger.log(GLogger.GLog.FILE_READ, name);
        }
        catch (IOException e) {
            GLogger.error(GLogger.GError.CANNON_READ_FILE, e, name);
        }

        return sb.toString();
    }

    public static AudioPlayer getSound(String name) {
        return soundManager.getAudio(name);
    }

    public static AudioPlayer loadSound(String name) {
        return soundManager.loadAudio(name);
    }

    public static AudioPlayer checkAndGetSound(String name) {
        return soundManager.checkAndGetAudio(name);
    }

    public static JSONObject getJSONThrowing(String name) throws JSONException {
        return new JSONObject(getFileContent(name));
    }

    public static JSONObject getJSON(String name) {
        try {
            return new JSONObject(getFileContent(name));
        }
        catch (JSONException e) {
            GLogger.error(GLogger.GError.CANNOT_PARSE_JSON_FILE, e, name);
        }
        return null;
    }

    public static URL getURL(String path) {
        return ProfileMenu.class.getResource(path);
    }
}
