package org.bombercraft2.game.misc;

import org.bombercraft2.StaticConfig;
import org.jetbrains.annotations.NotNull;
import utils.math.BVector2f;

/**
 * Táto trieda by mala byť prístupná vždy ak niekto bude potrebovať zoom alebo offset
 */
public class GameTransform {
    private final BVector2f maxOffset = new BVector2f();
    @NotNull
    private final BVector2f offset    = new BVector2f();
    private       float     minZoom;
    private       float     zoom      = StaticConfig.DEFAULT_ZOOM;

    @NotNull
    public BVector2f transform(@NotNull BVector2f position) {
        return position.getMul(zoom).getSub(offset);
    }

    @NotNull
    public BVector2f transformInvert(@NotNull BVector2f position) {
        return position.getAdd(offset).getDiv(zoom);
    }

    public void addZoom(float value) {
        zoom = Math.max(minZoom, zoom + value);
    }

    public float getZoom() {
        return zoom;
    }
}
