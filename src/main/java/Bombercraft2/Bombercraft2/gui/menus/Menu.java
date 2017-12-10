package Bombercraft2.Bombercraft2.gui.menus;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.MenuAble;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.gui.Clicable;
import Bombercraft2.Bombercraft2.gui.components.Button;
import Bombercraft2.Bombercraft2.gui.components.GuiComponent;
import utils.math.GVector2f;

import java.awt.*;
import java.util.HashMap;

//public abstract class Menu extends GuiComponent implements Clicable{
public abstract class Menu extends GameState implements Clicable, Visible {
    protected final HashMap<String, GuiComponent> components = new HashMap<>();
    protected       GVector2f                     position   = null;
    protected       GVector2f                     size       = null;
    protected       MenuAble                      parent     = null;

    public Menu(MenuAble parent, GameState.Type type) {
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

    public void setDisabled(String key, boolean value) {
        components.get(key).setDisable(value);
    }

    protected void setItem(String key) {
        addComponent(key, new Button(this, parent.getGuiManager().getLabelOf(key)));
    }

    public void render(Graphics2D g2) {
        components.forEach((key, value) -> value.render(g2));
    }

    @Override
    public void update(float delta) {
        components.forEach((key, value) -> value.update(delta));
    }

    public abstract void calcPosition();

    protected void addComponent(String name, GuiComponent component) {
        components.put(name, component);
    }

    @Override
    public GVector2f getPosition() {return position;}

    public MenuAble getParent() {return parent;}

    @Override
    public GVector2f getSize() {return size;}
}
