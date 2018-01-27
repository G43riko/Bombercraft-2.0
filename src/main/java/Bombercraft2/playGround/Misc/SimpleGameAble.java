package Bombercraft2.playGround.Misc;

import org.jetbrains.annotations.NotNull;

import Bombercraft2.Bombercraft2.core.Visible;
import utils.math.GVector2f;

public interface SimpleGameAble {
    default float getZoom() {
    	return 1;
    };

    @NotNull
    default GVector2f getOffset() {
    	return new GVector2f(); 
    };
    default boolean isVisible(@NotNull Visible b) {
        return !(b.getPosition().getX() * getZoom() + b.getSize().getX() * getZoom() < getOffset().getX() ||
                b.getPosition().getY() * getZoom() + b.getSize().getY() * getZoom() < getOffset().getY() ||
                getOffset().getX() + getCanvasSize().getX() < b.getPosition().getX() * getZoom() ||
                getOffset().getY() + getCanvasSize().getY() < b.getPosition().getY() * getZoom());
    };
    @NotNull
    default GVector2f getCanvasSize() {
    	return null;
    };
}
