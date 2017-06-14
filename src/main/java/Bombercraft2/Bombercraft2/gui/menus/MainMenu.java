package Bombercraft2.Bombercraft2.gui.menus;

import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.MenuAble;
import Bombercraft2.Bombercraft2.gui.GuiManager;
import Bombercraft2.Bombercraft2.gui.components.Button;
import Bombercraft2.Bombercraft2.gui.components.GuiComponent;
import utils.math.GVector2f;

public class MainMenu extends Menu{
	private MenuAble parent;
	public MainMenu(MenuAble parent, GuiManager manager){
		super(parent, GameState.Type.MainMenu);
		this.manager = manager;
		this.parent = parent;
		position.setY(100);
		init();
	}
	protected void init() {
		setItem("newGame");
		setItem("continue");
		setItem("stopGame");
		setItem("joinGame");
		setItem("changeProfil");
		setItem("options");
		setItem("exit");
	}
	@Override
	public void doAct(GVector2f click) {
		if(components.get("exit").isClickIn(click)){
			parent.exitGame();
		}
		else if(components.get("changeProfil").isClickIn(click)){
			parent.showProfileMenu();
		}
		else if(components.get("newGame").isClickIn(click)){
			parent.startNewGame();
		}
		else if(components.get("continue").isClickIn(click)){
			parent.continueGame();
		}
	}
	@Override
	public void render(Graphics2D g2) {
		g2.clearRect(0, 0, size.getXi(), size.getYi());
		super.render(g2);
	}
	@Override
	public void calcPosition() {
		
	}
	@Override
	public void onResize() {
		for(GuiComponent component : components.values()){
			component.calcPosAndSize();
		}
	}
}
