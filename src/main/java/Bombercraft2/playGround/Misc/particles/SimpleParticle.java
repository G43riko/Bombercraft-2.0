package Bombercraft2.playGround.Misc.particles;

import java.awt.Color;
import java.awt.Graphics2D;

import org.json.JSONObject;

import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import utils.math.GVector2f;

public class SimpleParticle implements Visible, InteractAble{
	private final SimpleGameAble parent;
	private final GVector2f direction;
	private GVector2f position;
    private final GVector2f size;
    private boolean alive;
    private final Color     color;
    private       int       health;

    //CONSTRUCTORS

    public SimpleParticle(GVector2f position, 
    					  SimpleGameAble parent, 
    					  Color color, 
    					  GVector2f direction, 
    					  GVector2f size, 
    					  int health) {
        this.position = position;
        this.parent = parent;
        this.direction = direction;
        this.color = color;
        this.health = health;
        this.size = size;
    }

    //OVERRIDES

    @Override
    public void update(float delta) {
        position = position.add(direction.mul(parent.getZoom()));
        if (--health <= 0) { 
        	alive = false;
        }
    }
    
    public boolean isALive() {
    	return alive;
    }
    
    public void render(Graphics2D g2) {
        GVector2f pos = position.sub(size.div(2)).mul(parent.getZoom()).sub(parent.getOffset());
        GVector2f totalSize = size.mul(parent.getZoom());
        g2.setColor(color);
        g2.fillArc(pos.getXi(), pos.getYi(), totalSize.getXi(), totalSize.getYi(), 0, 360);
    }
    
    //GETTERS

    public GVector2f getSize() {return size;}

	@Override
	public GVector2f getPosition() {
		return position;
	}

}
