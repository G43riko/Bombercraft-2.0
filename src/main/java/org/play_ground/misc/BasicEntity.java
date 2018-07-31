package org.play_ground.misc;

import org.bombercraft2.game.misc.GCanvas;
import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.map.BasicChunk;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BasicEntity {
    public final  GVector2f        size;
    public final  SimpleGameAble   parent;
    public final  List<BasicChunk> actChunks = new ArrayList<>(4);
    private final boolean          isMoving  = true;
    public        GVector2f        position;

    public BasicEntity(SimpleGameAble parent, GVector2f position, GVector2f size) {
        this.position = position;
        this.parent = parent;
        this.size = size;
    }

    public void update(float delta) {
        if (isMoving && isNearByBorders()) {
            changeChunks(getOldActChunks(), parent.getActChunk(this));
        }
    }

    private void changeChunks(List<BasicChunk> oldChunks, List<BasicChunk> actChunks) {
        oldChunks.forEach(chunk -> chunk.remove(this));
        actChunks.forEach(chunk -> chunk.addIfNotExists(this));
        this.actChunks.clear();
        this.actChunks.addAll(actChunks);
    }

    public void render(@NotNull Graphics2D g2) {
        GCanvas.drawRect(g2, position, size, Color.RED, 1);
        // g2.setColor(Color.red);
        // g2.setStroke(new BasicStroke(1));
        // g2.drawRect(scale.getXi(), scale.getYi(), size.getXi(), size.getYi());
    }

    private boolean isNearByBorders() {
        return true;
    }

    public List<BasicChunk> getOldActChunks() {
        return actChunks.stream().filter(actChunk -> !actChunk.isIn(this)).collect(Collectors.toList());
    }


}
