package Bombercraft2.Bombercraft2.multiplayer.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Bombercraft;

public class ClientPlayer {
	private Socket socket;
	private ObjectInputStream objectReader;
	private ObjectOutputStream objectWritter;
	private String name;
	private int id;
	
	public String getName() {
		return name;
	}

	public ClientPlayer(Socket socket, int id) {
		this.id = id;
		this.socket = socket;
		try {
			objectWritter = new ObjectOutputStream(socket.getOutputStream());
			objectWritter.flush();
			objectReader = new ObjectInputStream(socket.getInputStream());
//			GLog.write(GLog.SITE, "S: Profil hráèa " + name + " bol vytvorený");
		} catch (IOException e) {
			e.printStackTrace();
//			GLog.write(GLog.SITE, "S: Nepodarilo sa vytvori profil hráèovy " + name); 
		}
	}
	
	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object read(){
		try {
			Object o = objectReader.readObject();
			return o;
		} catch (ClassNotFoundException | IOException e) {
//			GLog.write(GLog.SITE, "S: Nepodarila sa preèíta správa pri hráèovy menom: " + name);
		}
		return null;
	}
	
	public void cleanUp() {
		try {
			socket.close();

			objectReader.close();
			objectWritter.close();
//			GLog.write(GLog.SITE, "S: Úspešne sa zmazal hráè: " + name);
		} catch (IOException e) {
			e.printStackTrace();
//			GLog.write(GLog.SITE, "S: Nepodarilo sa zmaza hráèa menom: " + name);
		}
	}

	public void write(Serializable o, String type){
		Bombercraft.sendMessages++;
		JSONObject object = new JSONObject();
		try {
			object.put("type", type);
			object.put("msg", o.toString());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		try {
			objectWritter.writeObject(object.toString());
			objectWritter.flush();
		} catch (IOException e) {
			e.printStackTrace();
//			GLog.write(GLog.SITE_MESSAGES, "S: Nepodailo sa odosla správu " + o + " hráèovy: " + name);
		}
	}

}
