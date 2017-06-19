package Bombercraft2.Bombercraft2.gui;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Bombercraft2.Bombercraft2.core.Interactable;
import Bombercraft2.Bombercraft2.core.Render;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.Iconable;
import Bombercraft2.Bombercraft2.game.ToolManager;
import Bombercraft2.Bombercraft2.gui.submenu.SubMenuCreator;
import Bombercraft2.Bombercraft2.gui.submenu.Submenu;
import Bombercraft2.engine.Input;
import utils.math.GVector2f;

public class GameGui  implements Interactable, Clicable{
	GameLogs 	gameLogs;
	NavBar		navBar;
	SideBar		sideBar;
	Submenu		subMenu;
	PlayerPanel	playerPanel;
	GameAble 	parent;
	public GameGui(GameAble parent) {
		this.parent = parent;
		gameLogs 	= new GameLogs(parent);
		navBar 		= new NavBar(parent);
		sideBar 	= new SideBar(parent);
		playerPanel = new PlayerPanel(parent);
		subMenu		= new Submenu(parent, SubMenuCreator.generateSubmenu(parent));
		
	}
	
	public void calcPosition(){
		sideBar.calcPosition();
		navBar.calcPosition();
	}
	
	public NavBar getNavBar(){
		return navBar;
	}
	
	
	@Override
	public void render(Graphics2D g2) {
		if(navBar.isVisible()){
			navBar.render(g2);
		}

		if(sideBar.isVisible()){
			sideBar.render(g2);
		}
		if(subMenu.isVisible()){
			subMenu.render(g2);
		}
		
		if(playerPanel.isVisible()){
			playerPanel.render(g2);
		}
//		if(Bombercraft.getViewOption("renderMiniMap"))
//			minimap.render(g2);
		
		if(parent.getVisibleOption(Render.LOGS)){
			gameLogs.render(g2);
		}
	}
	@Override
	public void input() {
		if(Input.getKeyDown(Input.KEY_E)){
			sideBar.setVisible(sideBar.isVisible() == false);
		}
		if(Input.getKeyDown(Input.KEY_Q)){
			subMenu.setVisible(subMenu.isVisible() == false);
		}
		
		if(navBar.isVisible()){
			navBar.input();
		}
		if(subMenu.isVisible()){
			subMenu.input();
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
