package Bombercraft2.Bombercraft2.gui.menus;

import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.MenuAble;
import Bombercraft2.Bombercraft2.core.Texts;
import utils.math.GVector2f;

public class OptionsMenu extends Menu{

	public OptionsMenu(MenuAble parent) {
		super(parent, GameState.Type.OptionsMenu);
		this.parent = parent;
		position.setY(100);
		init();
	}
	
	protected void init() {
		setItem(Texts.BACK);
	}
	@Override
	public void render(Graphics2D g2) {
		g2.clearRect(0, 0, size.getXi(), size.getYi());
		super.render(g2);
	}

	@Override
	public void doAct(GVector2f click) {
		if(components.get(Texts.BACK).isClickIn(click)){
			parent.showMainMenu();
		}
	}

	@Override
	public void calcPosition() {
		// TODO Auto-generated method stub
		
	}

}
