package Bombercraft2.Bombercraft2.gui2.core;

public abstract class DrawableComponent extends PositionableComponent implements Drawable  {
    protected boolean visible = true;

    public boolean isVisible() {return visible;}

    public void setVisible(boolean visible) {this.visible = visible;}

}
