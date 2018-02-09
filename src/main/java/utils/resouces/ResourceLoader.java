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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.gui.menus.ProfileMenu;
import utils.AudioPlayer;
import utils.GLogger;

public final class ResourceLoader {
    private static final HashMap<String, Image> loadedTextures = new HashMap<>();
    private static final SoundManager           soundManager   = new SoundManager();

    public static InputStream load(@NotNull String fileName) {
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

    @Nullable
    private static FileWriter getFileWriter(@NotNull String fileName) {
        try {
            return new FileWriter(new File(ProfileMenu.class.getResource(fileName).toURI()));
        }
        catch (URISyntaxException | IOException e) {
            GLogger.error(GLogger.GError.CANNON_CREATE_FILE_WRITER, e, fileName);
        }
        return null;
    }

    @Nullable
    public static BufferedWriter getBufferedWriter(@NotNull String fileName) {
        final FileWriter writer = getFileWriter(fileName);

        if (writer == null) {
            return null;
        }

        return new BufferedWriter(writer);
    }
    @Nullable
    public static Image loadTexture(@NotNull String fileName) {
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

    @NotNull
    private static String getFileContent(@NotNull String name) {
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

    @Nullable
    public static AudioPlayer getSound(@NotNull String name) {
        return soundManager.getAudio(name);
    }

    @Nullable
    public static AudioPlayer loadSound(@NotNull String name) {
        return soundManager.loadAudio(name);
    }

    @Nullable
    public static AudioPlayer checkAndGetSound(@NotNull String name) {
        return soundManager.checkAndGetAudio(name);
    }

    @NotNull
    public static JSONObject getJSONThrowing(@NotNull String name) throws JSONException {
        return new JSONObject(getFileContent(name));
    }

    @Nullable
    public static JSONObject getJSON(String name) {
        try {
            return new JSONObject(getFileContent(name));
        }
        catch (JSONException e) {
            GLogger.error(GLogger.GError.CANNOT_PARSE_JSON_FILE, e, name);
        }
        return null;
    }

    @NotNull
    public static URL getURL(@NotNull String path) {
        return ProfileMenu.class.getResource(path);
    }
}
