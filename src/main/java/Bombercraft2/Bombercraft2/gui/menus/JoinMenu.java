package Bombercraft2.Bombercraft2.gui.menus;

import java.awt.Graphics2D;
import java.awt.List;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;

import org.json.JSONObject;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.MenuAble;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.gui.components.Button;
import Bombercraft2.Bombercraft2.gui.components.GuiComponent;
import Bombercraft2.Bombercraft2.gui.components.RemoteGamePanel;
import Bombercraft2.Bombercraft2.multiplayer.RemoteGameData;
import utils.GLogger;
import utils.Utils;
import utils.math.GVector2f;



public class JoinMenu extends Menu{
	private java.util.List<RemoteGameData> games 	= new ArrayList<RemoteGameData>();
	private java.util.List<RemoteGameData> gamesNew = new ArrayList<RemoteGameData>();
	private long 	lastPing		= 0;
	private int 	scanned 		= 0;
	
	private Thread t = new Thread(new Runnable() {
		public void run() {
			scanned = 0;
			try{
				InetAddress localhost = InetAddress.getByName(Utils.getLocalIP());
				byte[] ip = localhost.getAddress(); 
				for (int i = 0; i < Config.SCANNING_RANGE; i++) { 
					ip[3] = (byte)i; 
					InetAddress address = InetAddress.getByAddress(ip); 
					
					if (address.isReachable(Config.SCANNING_TIMEOUT)) { 
						Socket socket = null;
						ObjectInputStream objectReader = null;
						ObjectOutputStream objectWritter = null;
						try{
							socket = new Socket(address, Config.SERVER_PORT);

							objectReader = new ObjectInputStream(socket.getInputStream());
							objectWritter = new ObjectOutputStream(socket.getOutputStream());;
							objectWritter.flush();
							
							String result = String.valueOf(objectReader.readObject());
							JSONObject data = new JSONObject(result);;
							String message = String.valueOf(data.get(Texts.MESSAGE));
							
							JSONObject msg = new JSONObject(message);
							addRemoteData(new RemoteGameData(msg.getString(Texts.PLAYER_NAME), 
															 address.toString().replace("/", ""), 
															 msg.getString(Texts.LEVEL_NAME), 
															 msg.getInt(Texts.MAX_PLAYERS), 
															 msg.getInt(Texts.PLAYERS_NUMBER)));
						}
					    catch (Exception ee) {
//					    	ee.printStackTrace();
						}
						if(socket != null){
							socket.close();
						}
						if(objectReader != null){
							objectReader.close();
						}
						if(objectWritter != null){
							objectWritter.close();
						}
					} 
					else if (!address.getHostAddress().equals(address.getHostName())){} 
					else {} 
					scanned++;
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
	
	private void addRemoteData(RemoteGameData data){
		gamesNew.add(data);
	}
	public JoinMenu(MenuAble parent) {
		super(parent, GameState.Type.JoinMenu);
		this.parent = parent;
		position.setY(100);
		init();
	}
	protected void init() {
		setItem(Texts.BACK);

		t.start();
		lastPing = System.currentTimeMillis();
	}
	@Override
	public void render(Graphics2D g2) {
		g2.clearRect(0, 0, size.getXi(), size.getYi());
		super.render(g2);
	}
	@Override
	public void update(float delta) {
		gamesNew.forEach(a -> {
			addComponent(Texts.GAME + (games.size() + 1), new RemoteGamePanel(parent, this, a, games.size()));
			games.add(a);
		});
		gamesNew.clear();
		
		super.update(delta);
		if(lastPing + Config.PING_REFRESH_TIME < System.currentTimeMillis()){
			lastPing = System.currentTimeMillis();
			games.forEach(a -> a.reping());
		}
	}
	@Override
	public void doAct(GVector2f click) {
		
		if(components.get(Texts.BACK).isClickIn(click)){
			parent.showMainMenu();
		}
		int counter = 1;
		GuiComponent panel = components.get(Texts.GAME + counter++);
		while(panel != null){
			if(panel.isClickIn(click)){
				((RemoteGamePanel)panel).doAct(click);
			}
			panel = components.get(Texts.GAME + counter++);
		}
	}

	@Override
	public void calcPosition() {
		// TODO Auto-generated method stub
		
	}
	public void stopSearch() {
		t.interrupt();
	}

}
