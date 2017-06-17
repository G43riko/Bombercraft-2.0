package Bombercraft2.Bombercraft2.game.entity;

import java.awt.Image;

import org.json.JSONException;
import org.json.JSONObject;

import Bombercraft2.Bombercraft2.core.Interactable;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.GameAble;
import utils.IDGenerator;
import utils.math.GVector2f;

public abstract class Entity implements Visible, Interactable{
	private 	int 		id 		= IDGenerator.getId();
	private 	GameAble 	parent;
	protected 	GVector2f 	position;
	protected 	boolean 	alive 	= true;
	//CONSTRUCTORS
	
	public Entity(JSONObject json, GameAble parent){
		this.parent = parent;
		try {
			id 			= json.getInt("id");
			alive		= json.getBoolean("alive");
			position 	= new GVector2f(json.getString("position"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Entity(GVector2f position, GameAble parent){
		this.position = position;
		this.parent = parent;
	}
	
	//ABSTRACT
	
	public abstract JSONObject toJSON();
	public abstract GVector2f getSur();
	public abstract GVector2f getSize();
	
	//GETTERS
	
	public int 			getID(){return id;}
	public GameAble 	getParent() {return parent;}
	public GVector2f 	getPosition() {return position;}
	protected GVector2f getTotalPosition(){return position.mul(parent.getZoom()).sub(getParent().getOffset());}
	protected GVector2f getTotalSize(){return getSize().mul(parent.getZoom()); }

	public boolean 		isAlive() {return alive;}
	
	//SETTERS

	public void setPosition(GVector2f position) {this.position = position;}
}
