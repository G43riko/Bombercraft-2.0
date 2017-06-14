package Bombercraft2.Bombercraft2.gui;

import java.awt.Color;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Interactable;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.GameAble;
import utils.math.GVector2f;

public abstract class Bar implements Interactable, Clicable, Visible{
	private Color backgroundColor 	= Config.BAR_BACKGROUND_COLOR;
	private Color borderColor 		= Config.BAR_BORDER_COLOR;
	
	protected GVector2f size;
	protected GVector2f totalSize;
	protected GVector2f totalPos;
	
	private boolean visible = true;
	private GameAble parent;
	private int borderWidth = Config.BAR_BORDER_WIDTH;
	
	//CONTRUCTORS
	
	public Bar(GameAble parent, GVector2f size) {
		this.parent = parent;
		this.size = size;
	}

	//ABSTRACT
	
	public abstract void calcPosition();
	
	//SETTERS

	public void setVisible(boolean visible) {this.visible = visible;}
	public void setBorderWidth(int borderWidth) {this.borderWidth = borderWidth;}
	public void setBorderColor(Color borderColor) {this.borderColor = borderColor;}
	public void setBackgroundColor(Color backgroundColor) {this.backgroundColor = backgroundColor;}
	
	//GETTERS
	
	public boolean isVisible() {return visible;}
	public GameAble getParent() {return parent;}
	public int getBorderWidth() {return borderWidth;}
	public Color getBorderColor() {return borderColor;}
	public Color getBackgroundColor() {return backgroundColor;}

}
