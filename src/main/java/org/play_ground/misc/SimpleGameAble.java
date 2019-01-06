package org.play_ground.misc;

import org.bombercraft2.MainManager;
import org.bombercraft2.core.Visible;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.play_ground.misc.map.BasicChunk;

import java.util.ArrayList;
import java.util.List;

/**
 * Components
 * - ViewManager
 * - getOffset
 * - getZoom
 * - ParticleManager
 */
public interface SimpleGameAble {
    @Contract(pure = true)
    default float getZoom() {
        return 1;
    }

    @NotNull
    @Contract(pure = true)
    default GVector2f getOffset() {
        return new GVector2f();
    }

    default boolean isVisible(@NotNull Visible b) {
        return !(b.getPosition().getX() * getZoom() + b.getSize().getX() * getZoom() < getOffset().getX() ||
                b.getPosition().getY() * getZoom() + b.getSize().getY() * getZoom() < getOffset().getY() ||
                getOffset().getX() + getCanvasSize().getX() < b.getPosition().getX() * getZoom() ||
                getOffset().getY() + getCanvasSize().getY() < b.getPosition().getY() * getZoom());
    }

    @NotNull
    @Contract(pure = true)
    default GVector2f getCanvasSize() {
        return new GVector2f();
    }

    default List<BasicChunk> getActChunk(BasicEntity entity) {
        return new ArrayList<>();
    }

    @NotNull
    default List<String> getLogInfo() {
        return getManager().getLogInfo();
    }

    //TODO: toto treba prerobiť na @NotNull
    default MainManager getManager() { return null; }
}