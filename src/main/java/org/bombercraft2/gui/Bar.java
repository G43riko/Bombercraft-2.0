package org.bombercraft2.gui;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.core.Visible;
import org.bombercraft2.game.GameAble;
import org.glib2.interfaces.InteractAbleG2;
import utils.math.GVector2f;

import java.awt.*;

abstract class Bar implements InteractAbleG2, ClickAble, Visible {
    final         GVector2f size;
    private final GameAble  parent;
    GVector2f totalSize;
    GVector2f totalPos;
    private Color   backgroundColor = StaticConfig.BAR_BACKGROUND_COLOR;
    private Color   borderColor     = StaticConfig.BAR_BORDER_COLOR;
    private int     borderWidth     = StaticConfig.BAR_BORDER_WIDTH;
    private boolean visible         = true;

    //CONSTRUCTORS

    Bar(GameAble parent, GVector2f size) {
        this.parent = parent;
        this.size = size;
    }

    //ABSTRACT

    public abstract void calcPosition();

    //SETTERS

    public boolean isVisible() {return visible;}

    public void setVisible(boolean visible) {this.visible = visible;}

    GameAble getParent() {return parent;}

    int getBorderWidth() {return borderWidth;}

    //GETTERS

    void setBorderWidth(int borderWidth) {this.borderWidth = borderWidth;}

    Color getBorderColor() {return borderColor;}

    void setBorderColor(Color borderColor) {this.borderColor = borderColor;}

    Color getBackgroundColor() {return backgroundColor;}

    void setBackgroundColor(Color backgroundColor) {this.backgroundColor = backgroundColor;}

}
