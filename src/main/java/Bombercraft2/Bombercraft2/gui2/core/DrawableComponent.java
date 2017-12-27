package Bombercraft2.Bombercraft2.gui2.core;

import Bombercraft2.Bombercraft2.gui2.GuiManager;

public abstract class DrawableComponent extends PositionableComponent implements Drawable  {
    protected boolean visible = true;
    private GuiManager manager;

    public DrawableComponent() {
        GuiConnector.register(this);
    }
    public boolean isVisible() {return visible;}

    public void setVisible(boolean visible) {this.visible = visible;}

    public void setManager(GuiManager manager) {
        this.manager = manager;
    }

    public GuiManager getManager() {
        return manager;
    }

}
