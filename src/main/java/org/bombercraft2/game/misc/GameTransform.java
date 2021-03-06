package org.bombercraft2.game.misc;

import org.bombercraft2.StaticConfig;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;

/**
 * Táto trieda by mala byť prístupná vždy ak niekto bude potrebovať zoom alebo offset
 */
public class GameTransform {
    private final GVector2f maxOffset = new GVector2f();
    @NotNull
    private final GVector2f offset    = new GVector2f();
    private       float     minZoom;
    private       float     zoom      = StaticConfig.DEFAULT_ZOOM;

    @NotNull
    public GVector2f transform(@NotNull GVector2f position) {
        return position.getMul(zoom).getSub(offset);
    }

    @NotNull
    public GVector2f transformInvert(@NotNull GVector2f position) {
        return position.getAdd(offset).getDiv(zoom);
    }

    public void addZoom(float value) {
        zoom = Math.max(minZoom, zoom + value);
    }

    public float getZoom() {
        return zoom;
    }
}
