package Bombercraft2.Bombercraft2;


import Bombercraft2.Bombercraft2.game.entity.particles.Emitter;
import utils.Utils;

public class App{
	
    public static void main(String[] args){
    	Bombercraft game = new Bombercraft();
		game.run();
    }
    
    private static void testMultiplayer(){
    	Thread t1 = new Thread(new Runnable() {
			public void run() {
				Bombercraft server = new Bombercraft();
		    	server.initDefaultProfil();
		    	server.startNewGame();
		    	server.run();
			}
		});
    	Thread t2 = new Thread(new Runnable() {
			public void run() {
				Bombercraft client = new Bombercraft();
		    	client.initDefaultProfil();
		    	client.showJoinMenu();
		    	client.run();
			}
		});
    	t1.start();
    	Utils.sleep(1000);
    	t2.start();
    }
}
