package Bombercraft2.Bombercraft2.game.entity.explosion;

import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.Bombercraft2.game.GameAble;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public class ShockWave implements InteractAble {
    private final GameAble parent;
    private final int      maxRadius;
    private final int      stroke;
    private final int      speed;
    private int       radius    = 0;
    private int       alphaDiff = 0;
    private boolean   alive     = true;
    private       Color     color;
    private final GVector2f position;

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


    @Override
    public void render(@NotNull Graphics2D g2) {
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


    public boolean isAlive() {
        return alive;
    }

}
