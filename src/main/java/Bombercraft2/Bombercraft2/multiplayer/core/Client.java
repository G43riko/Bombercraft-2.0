package Bombercraft2.Bombercraft2.multiplayer.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Bombercraft;
import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Texts;

public abstract class Client implements Writable{
	private ObjectInputStream objectReader;
	private ObjectOutputStream objectWritter;

	private Socket socket;

	private boolean readerIsRunning = true;
	
	public Client(String ip) {
		
		try {
			socket = new Socket(ip, Config.SERVER_PORT);
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
	
	/**
	 * Tato funkcia je zavolana ak sa bezdovodne prerusi spojenie zo serverom
	 */
	protected abstract void onConnectionBroken();
	
	public void write(String o, String type){
		if(!socket.isConnected()){
			onConnectionBroken();
		}
		JSONObject object = new JSONObject();
		try {
			object.put(Texts.TYPE, type);
			object.put(Texts.MESSAGE, o.toString());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		try {
			objectWritter.writeObject(object.toString());
			Bombercraft.sendMessages++;
		} catch (IOException e) {
			onConnectionBroken();
		}
	}
	

	public void cleanUp() {
		readerIsRunning = false;
		
		try {
			if(socket != null){
				socket.close();
			}
			
			objectReader.close();
			objectWritter.close();
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}
}