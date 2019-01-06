package org.play_ground.misc.map;

import org.bombercraft2.game.misc.GCanvas;
import org.play_ground.misc.BasicEntity;
import utils.math.BVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BasicChunk {
    public final BVector2f         position;
    public final BVector2f         size;
    public final Color             color;
    private      List<BasicEntity> entities = new ArrayList<>();

    public BasicChunk(BVector2f position, BVector2f size, Color color) {
        this.position = position;
        this.size = size;
        this.color = color;
    }

    public void add(BasicEntity entity) {
        entities.add(entity);
    }

    public void addIfNotExists(BasicEntity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        }
    }

    public void remove(BasicEntity entity) {
        entities.remove(entity);
    }

    public void render(Graphics2D g2) {
        GCanvas.fillRect(g2, position, size, entities.isEmpty() ? color : Color.GREEN);
        // g2.setColor(entities.isEmpty() ? color : Color.GREEN);
        // g2.fillRect(scale.getXi(), scale.getYi(), size.getXi(), size.getYi());
    }

    public boolean isIn(BasicEntity entity) {
        return entity.position.getX() > position.getX() &&
                entity.position.getY() > position.getY() &&
                entity.position.getX() + entity.size.getX() < position.getX() + size.getX() &&
                entity.position.getY() + entity.size.getY() < position.getY() + size.getY();
    }
}
