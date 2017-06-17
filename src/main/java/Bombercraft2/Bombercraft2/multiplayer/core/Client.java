package Bombercraft2.Bombercraft2.multiplayer.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Bombercraft;
import Bombercraft2.Bombercraft2.Config;

public abstract class Client {
	private ObjectInputStream objectReader;
	private ObjectOutputStream objectWritter;

	private Socket socket;

	private boolean readerIsRunning = true;
	
	public Client() {
		try {
			socket = new Socket("localhost", Config.SERVER_PORT);
			objectWritter = new ObjectOutputStream(socket.getOutputStream());
			objectWritter.flush();
			objectReader = new ObjectInputStream(socket.getInputStream());
//			GLog.write(GLog.SITE, "C: Client sa pripojil");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		listen();
	}
	
	public abstract void sendPlayerInfo();

	protected abstract void processMessage(String txt);
	
	private void listen(){
		Thread listenThread = new Thread(new Runnable(){
			public void run() {
				while(readerIsRunning){
					Object o = read();
					if(o != null){
						Bombercraft.recieveMessages++;
//						GLog.write(GLog.SITE_MESSAGES, "C: Client prijal správu: " + o);
						processMessage(String.valueOf(o));
					}
						
				}
			}
		});
		listenThread.start();
	}
	
	private Object read(){
		try {
			return objectReader.readObject();	
		} catch (ClassNotFoundException | IOException e) {
			return null;
		}
	}
	
	
	
	public void write(String o, String type){
		JSONObject object = new JSONObject();
		try {
			object.put("type", type);
			object.put("msg", o.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			objectWritter.writeObject(object.toString());
			Bombercraft.sendMessages++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void cleanUp() {
readerIsRunning = false;
		
		try {
			if(socket != null)
				socket.close();
			
			objectReader.close();
			objectWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}