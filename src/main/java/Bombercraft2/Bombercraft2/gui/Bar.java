package Bombercraft2.Bombercraft2.gui;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.GameAble;
import utils.math.GVector2f;

import java.awt.*;

abstract class Bar implements InteractAble, ClickAble, Visible{
	private Color backgroundColor 	= Config.BAR_BACKGROUND_COLOR;
	private Color borderColor 		= Config.BAR_BORDER_COLOR;
	private int borderWidth 		= Config.BAR_BORDER_WIDTH;

	private boolean visible 		= true;
	final GVector2f size;
	GVector2f totalSize;
	GVector2f totalPos;
	
	private final GameAble parent;
	
	//CONSTRUCTORS
	
	Bar(GameAble parent, GVector2f size) {
		this.parent = parent;
		this.size = size;
	}

	//ABSTRACT
	
	public abstract void calcPosition();
	
	//SETTERS

	public void setVisible(boolean visible) {this.visible = visible;}
	void setBorderWidth(int borderWidth) {this.borderWidth = borderWidth;}
	void setBorderColor(Color borderColor) {this.borderColor = borderColor;}
	void setBackgroundColor(Color backgroundColor) {this.backgroundColor = backgroundColor;}
	
	//GETTERS
	
	public boolean isVisible() {return visible;}
	GameAble getParent() {return parent;}
	int getBorderWidth() {return borderWidth;}
	Color getBorderColor() {return borderColor;}
	Color getBackgroundColor() {return backgroundColor;}

}
