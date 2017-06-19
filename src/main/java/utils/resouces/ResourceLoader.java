package utils.resouces;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.gui.menus.ProfileMenu;
import utils.AudioPlayer;

public final class ResourceLoader {
	private static HashMap<String, Image> loadedTextures = new HashMap<String, Image>();
	private static SoundManager soundManager = new SoundManager();
	
	public static InputStream load(String fileName){
		InputStream input = ResourceLoader.class.getResourceAsStream(fileName);
		if(input == null){
			input = ResourceLoader.class.getResourceAsStream("/" + fileName);
		}
		return input;
	}

	public static FileWriter getFileWriter(String fileName){
		try {
			return new FileWriter(new File(ProfileMenu.class.getResource(fileName).toURI()));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static BufferedWriter getBufferredWritter(String fileName){
		return new BufferedWriter(getFileWriter(fileName));
	}
	
	public static Image loadTexture(String fileName){
		if(loadedTextures.containsKey(fileName))
			return loadedTextures.get(fileName);
		else{
			try {
				loadedTextures.put(fileName, ImageIO.read(load("/images/materials/" + fileName)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return loadedTextures.get(fileName);
		}
	}
	
	public static String getFileCOntent(String name){
		BufferedReader buf = new BufferedReader(new InputStreamReader(load(name))); 
		StringBuilder sb = new StringBuilder(); 
		String line;
		try {
			line = buf.readLine();
			while(line != null){ 
				sb.append(line).append("\n"); 
				line = buf.readLine(); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return sb.toString();
	}
	
	public static AudioPlayer getSound(String name){
		return soundManager.getAudio(name);
	}
	public static AudioPlayer loadSound(String name){
		return soundManager.loadAudio(name);
	}
	public static AudioPlayer checkAndGetSound(String name){
		return soundManager.checkAndGetAudio(name);
	}
	public static JSONObject getJSONThrowing(String name) throws JSONException{
		return new JSONObject(getFileCOntent(name));
	}
	public static JSONObject getJSON(String name){
		JSONObject result = null;
		try {
			result = new JSONObject(getFileCOntent(name));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static URL getURL(String path) {
		return ProfileMenu.class.getResource(path);
	}
}
