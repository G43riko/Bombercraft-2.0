package org.play_ground.misc;

import org.bombercraft2.core.Visible;
import org.bombercraft2.game.misc.GCanvas;
import org.glib2.interfaces.InteractAbleG2;
import org.glib2.math.physics.Collisions;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.play_ground.demos.ParticlesDemo;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleGridCollision implements InteractAbleG2 {
    private final static int                   MIN_SIZE = 10;
    private              GVector2f             position;
    private              GVector2f             size;
    private              SimpleGridCollision   parentGrid;
    private              SimpleGameAble        parent;
    private              boolean               visible  = false;
    /*
    3 0
    2 1
     */
    private              SimpleGridCollision[] children = new SimpleGridCollision[4];

    /**
     * Constructor for children's grid
     *
     * @param parentGrid - parent grid
     * @param position   - scale of grid
     * @param size       - size of grid
     */
    private SimpleGridCollision(SimpleGridCollision parentGrid, GVector2f position, GVector2f size) {
        this.parentGrid = parentGrid;
        this.position = position;
        this.size = size;
        create();
    }

    /**
     * Constructor for parent grid
     *
     * @param parent - parent object
     */
    public SimpleGridCollision(SimpleGameAble parent) {
        position = new GVector2f();
        this.parent = parent;
        size = parent.getCanvasSize();
        create();
    }

    private int nthChild() {
        int counter = 0;
        SimpleGridCollision tmpParent = parentGrid;
        while (tmpParent != null) {
            tmpParent = tmpParent.parentGrid;
            counter++;
        }
        return counter;
    }

    private void create() {
        if (size.getY() < MIN_SIZE || size.getX() < MIN_SIZE) {
            return;
        }
        final GVector2f halfSize = size.getDiv(2);
        children[0] = new SimpleGridCollision(this, position.getAdd(new GVector2f(halfSize.getXi(), 0)), halfSize);
        children[1] = new SimpleGridCollision(this,
                                              position.getAdd(new GVector2f(halfSize.getXi(), halfSize.getYi())),
                                              halfSize);
        children[2] = new SimpleGridCollision(this, position.getAdd(new GVector2f(0, halfSize.getYi())), halfSize);
        children[3] = new SimpleGridCollision(this, position.getAdd(new GVector2f(0, 0)), halfSize);
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        if (parentGrid == null) {
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.RED);
        }
        if (!visible) {
            return;
        }
        GCanvas.drawRect(g2, position, size);
        // g2.drawRect(scale.getXi(), scale.getYi(), size.getXi(), size.getYi());

        for (int i = 0; i < 4; i++) {
            if (children[i] != null) {
                children[i].render(g2);
            }
        }
    }


    @Override
    public void update(float delta) {
        deactivateVisibility();
        List<Visible> particles = new ArrayList<>(((ParticlesDemo) parent).particles);
        particles.forEach(this::checkObject);
    }

    private void deactivateVisibility() {
        visible = false;
        for (int i = 0; i < 4; i++) {
            if (children[i] != null) {
                children[i].deactivateVisibility();
            }
        }
    }

    private void checkObject(Visible object) {
        if (Collisions._2D.rectRect(object.getPosition().getXi(),
                                    object.getPosition().getYi(),
                                    object.getSize().getXi(),
                                    object.getSize().getYi(),
                                    position.getXi(),
                                    position.getYi(),
                                    size.getXi(),
                                    size.getYi())) {
            visible = true;

            for (int i = 0; i < 4; i++) {
                if (children[i] != null) {
                    children[i].checkObject(object);
                }
            }
        }
    }
}
