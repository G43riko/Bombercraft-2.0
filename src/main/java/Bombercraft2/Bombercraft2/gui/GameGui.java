package Bombercraft2.Bombercraft2.gui;

import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.core.Interactable;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.engine.Input;
import utils.math.GVector2f;

public class GameGui  implements Interactable, Clicable{
	GameLogs 	gameLogs;
	NavBar		navBar;
	SideBar		sideBar;
	GameAble parent;
	public GameGui(GameAble parent) {
		this.parent = parent;
		gameLogs 	= new GameLogs(parent);
		navBar 		= new NavBar(parent);
		sideBar 	= new SideBar(parent);
	}
	public void calcPosition(){
		sideBar.calcPosition();
		navBar.calcPosition();
	}
	@Override
	public void render(Graphics2D g2) {
		if(navBar.isVisible()){
			navBar.render(g2);
		}

		if(sideBar.isVisible()){
			sideBar.render(g2);
		}
		
//		if(Bombercraft.getViewOption("renderMiniMap"))
//			minimap.render(g2);
		
		if(parent.getVisibleOption("renderLogs")){
			gameLogs.render(g2);
		}
	}
	@Override
	public void input() {
		if(Input.isKeyDown(Input.KEY_Q)){
			sideBar.setVisible(sideBar.isVisible() == false);
		}
		
		if(navBar.isVisible()){
			navBar.input();
		}
	}
	
	@Override
	public void update(float delta) {
		if(sideBar.isVisible()){
			sideBar.update(delta);
		}
	}

	@Override
	public void doAct(GVector2f click) {
		if(navBar.isVisible()){
			navBar.doAct(click);
		}
		
		if(sideBar.isVisible()){
			sideBar.doAct(click);
		}
	}

}
