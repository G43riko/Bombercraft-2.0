package Bombercraft2.Bombercraft2.game.entity.particles;

import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.awt.*;

public class Particle extends Entity {
    private final GVector2f direction;
    private final GVector2f size;
    private final Color     color;
    private       int       health;

    //CONSTRUCTORS

    public Particle(GVector2f position, GameAble parent, Color color, GVector2f direction, GVector2f size, int health) {
        super(position, parent);
        this.direction = direction;
        this.color = color;
        this.health = health;
        this.size = size;
    }

    //OVERRIDES

    @Override
    public void update(float delta) {
        position = position.add(direction.mul(getParent().getZoom()));
        if (--health <= 0) { 
        	alive = false;
        }

    }

    @Override
    public void render(Graphics2D g2) {
        GVector2f pos = position.sub(size.div(2)).mul(getParent().getZoom()).sub(getParent().getOffset());
        GVector2f totalSize = size.mul(getParent().getZoom());
        g2.setColor(color);
        g2.fillArc(pos.getXi(), pos.getYi(), totalSize.getXi(), totalSize.getYi(), 0, 360);
    }

    @Override
    public JSONObject toJSON() {
        return null;
    }

    @Override
    public GVector2f getPosition() {
        return super.getPosition().div(getParent().getZoom());
    }

    //GETTERS

    public GVector2f getSize() {return size;}

}
