package Bombercraft2.Bombercraft2.game.entity.explosion;

import Bombercraft2.Bombercraft2.core.Interactable;
import Bombercraft2.Bombercraft2.game.GameAble;
import utils.math.GVector2f;

import java.awt.*;

public class ShockWave implements Interactable {
    private GameAble  parent    = null;
    private int       maxRadius = 0;
    private int       stroke    = 0;
    private int       speed     = 0;
    private int       radius    = 0;
    private int       alphaDiff = 0;
    private boolean   alive     = true;
    private Color     color     = null;
    private GVector2f position  = null;
    //CONTRUCTORS

    public ShockWave(GameAble parent,
                     GVector2f position,
                     int maxRadius,
                     int stroke,
                     int speed,
                     Color color,
                     boolean fadeOut
                    ) {
        super();
        this.position = position;
        this.maxRadius = maxRadius;
        this.stroke = stroke;
        this.parent = parent;
        this.speed = speed;
        this.color = color;

        if (fadeOut) {
            alphaDiff = 255 / (maxRadius / speed);
        }
    }

    //OVERRIDES

    @Override
    public void render(Graphics2D g2) {
        GVector2f pos = position.sub(radius).mul(parent.getZoom()).sub(parent.getOffset());
        g2.setColor(color);
        int size = (int) (radius * 2 * parent.getZoom());
        g2.setStroke(new BasicStroke(stroke * parent.getZoom()));
        g2.drawOval(pos.getXi(), pos.getYi(), size, size);

        color = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() - alphaDiff);
    }

    @Override
    public void update(float delta) {
        radius += speed;
        if (radius >= maxRadius) {
            alive = false;
        }
    }

    //GETTERS

    public boolean isAlive() {
        return alive;
    }

}