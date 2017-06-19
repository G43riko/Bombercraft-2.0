package Bombercraft2.Bombercraft2.gui.menus;

import java.awt.Graphics2D;
import java.util.HashMap;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.MenuAble;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.gui.Clicable;
import Bombercraft2.Bombercraft2.gui.GuiManager;
import Bombercraft2.Bombercraft2.gui.components.Button;
import Bombercraft2.Bombercraft2.gui.components.GuiComponent;
import Bombercraft2.engine.Input;
import utils.math.GVector2f;
//public abstract class Menu extends GuiComponent implements Clicable{
public abstract class Menu extends GameState implements Clicable, Visible{
	protected HashMap<String, GuiComponent> components = new HashMap<String, GuiComponent>();
	protected GVector2f 					position;
	protected GVector2f 					size;
	protected MenuAble 						parent;
	
	public Menu(MenuAble parent, GameState.Type type){
		super(type);
		this.parent = parent;
		position = parent.getPosition();
		size = parent.getSize();
		//position = new GVector2f(0, 100);
		
		calcPosition();
	}
	
//	@Override
//	public void onResize() {
//		for(GuiComponent component : components.values()){
//			component.calcPosition();
//		}
//	}

	public void setDisabled(String key, boolean value){
		components.get(key).setDisable(value);
	}
	protected void setItem(String key){
		addComponent(key, new Button(this, parent.getGuiManager().getLabelOf(key)));
	}
	public void render(Graphics2D g2) {
		components.entrySet()
			      .stream()
			      .forEach(a -> a.getValue().render(g2));
	}
	
	@Override
	public void update(float delta) {
		components.entrySet()
				  .stream()
				  .forEach(a -> a.getValue().update(delta));
	}
	
	public abstract void calcPosition();

	protected void addComponent(String name, GuiComponent component){
		components.put(name, component);
	}
	
	public GVector2f getPosition() {return position;}
	public MenuAble getParent(){return parent;}
	public GVector2f getSize() {return size;}
}
