package org.bombercraft2.game.lights;

import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.awt.*;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Light extends Entity {
    private static final float[]       borders   = new float[]{0, 1};
    private static final Color[]       colors    = new Color[]{Color.BLACK, new Color(0, 0, 0, 0)};
    private final        GVector2f     size;
    private final        Entity        target;
    private              BufferedImage image     = null;
    private              int           radius;
    private              int           luminance = 255;
    private              int           offset    = 0;
    private              double        angle     = 10;
    private              int           flash     = 0;


//	public Light(GameAble parent, GVector2f position, GVector2f size, int radius) {
//		this(parent, position, size, null);
//		calcImage();
//	}

    public Light(GameAble parent, GVector2f position, GVector2f size, Entity target) {
        super(position, parent);
        this.target = target;
        this.size = size;
        radius = (int) size.average();
//		
//		
        calcImage();
    }

    public Light(GameAble parent,
                 GVector2f position,
                 int radius,
                 double angle,
                 int offset,
                 int luminance,
                 Entity target
                ) {
        super(position, parent);

        this.luminance = luminance;
        this.target = target;
        this.offset = offset;
        this.radius = radius;
        this.angle = -(angle > Math.PI * 2 ? angle = Math.toRadians(angle) : angle);

        size = new GVector2f(radius, radius);

        calcImage();

    }

    private void calcImage() {
        image = new BufferedImage(size.getXi() * 2, size.getYi() * 2, BufferedImage.TYPE_INT_ARGB);

        Point2D center = new Point2D.Float(radius, radius);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        Point2D focus = center;

        if (radius != 0 && offset != 0) {
            focus = new Point2D.Double(radius + Math.cos(angle) * offset, radius + Math.sin(angle) * offset);
        }

        Color[] color = new Color[]{new Color(255, 255, 255, luminance), colors[1]};
        RadialGradientPaint rgp = new RadialGradientPaint(center,
                                                          radius,
                                                          focus,
                                                          borders,
                                                          color,
                                                          MultipleGradientPaint.CycleMethod.REFLECT);
        g2.setPaint(rgp);
        g2.fillOval(0, 0, size.getXi() * 2, size.getYi() * 2);
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getPosition() {
        return target == null ? super.getPosition() : target.getPosition();
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        GVector2f finalPos;
        if (target == null) {
            finalPos = position.sub(getParent().getOffset());
        }
        else {
            finalPos = target.getPosition().add(target.getSize().div(2)).sub(getParent().getOffset());
        }

        if (image != null) {
            g2.drawImage(image, finalPos.getXi() - size.getXi(), finalPos.getYi() - size.getYi(), null);
        }
        else {
            Point2D.Float point = new Point2D.Float(finalPos.getX(), finalPos.getY());
            g2.setPaint(new RadialGradientPaint(point, size.max(), borders, colors, CycleMethod.REFLECT));
            g2.fillArc(finalPos.getXi() - size.getXi(),
                       finalPos.getYi() - size.getYi(),
                       size.getXi() * 2,
                       size.getYi() * 2,
                       0, 360);
        }
//		setAngle(angle + 0.1); 
//		setOffset(offset - 1);
        if (flash > 0 && System.currentTimeMillis() % flash == 0) { setLuminance((int) (Math.random() * 255)); }
//		setRadius(radius + 1);
    }

    public void setRadius(int radius) {
        this.radius = radius;
        calcImage();
    }

    private void setLuminance(int luminance) {
        this.luminance = luminance;
        calcImage();
    }

    public void setOffset(int offset) {
        this.offset = offset;
        calcImage();
    }

    public void setAngle(double angle) {
        this.angle = angle;
        calcImage();
    }

    public void setFlash(int flash) {
        this.flash = flash;
        calcImage();
    }

    @Override
    public void update(float delta) {
        if (target != null) {
            setPosition(target.getPosition().add(target.getSize().div(2)));
            if (!target.isAlive()) {
                alive = false;
            }
        }

    }

    @Contract(pure = true)
    @NotNull
    @Override
    public JSONObject toJSON() {
        // TODO Auto-generated method stub
        return null;
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getSize() {
        return size;
    }

    public boolean isStatic() {
        return target == null;
    }

}
