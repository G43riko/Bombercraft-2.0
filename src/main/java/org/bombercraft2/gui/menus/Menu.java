package org.bombercraft2.gui.menus;

import org.bombercraft2.core.GameState;
import org.bombercraft2.core.GameStateType;
import org.bombercraft2.core.MenuAble;
import org.bombercraft2.gui.components.Button;
import org.bombercraft2.gui.components.GuiComponent;
import org.glib2.interfaces.ClickAble;
import org.glib2.interfaces.Visible;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;

//public abstract class Menu extends GuiComponent implements ClickAble{
public abstract class Menu extends GameState implements ClickAble, Visible {
    final HashMap<String, GuiComponent> components = new HashMap<>();
    final GVector2f                     position;
    final GVector2f                     size;
    MenuAble parent;

    Menu(MenuAble parent, GameStateType type) {
        super(type);
        this.parent = parent;
        position = parent.getPosition();
        size = parent.getSize();
        //scale = new GVector2f(0, 100);

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

    void setItem(String key) {
        addComponent(key, new Button(this, parent.getGuiManager().getLabelOf(key)));
    }

    public void render(@NotNull Graphics2D g2) {
        components.forEach((key, value) -> value.render(g2));
    }

    @Override
    public void update(float delta) {
        components.forEach((key, value) -> value.update(delta));
    }

    protected abstract void calcPosition();

    void addComponent(String name, GuiComponent component) {
        components.put(name, component);
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getPosition() {return position;}

    public MenuAble getParent() {return parent;}

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getSize() {return size;}
}
