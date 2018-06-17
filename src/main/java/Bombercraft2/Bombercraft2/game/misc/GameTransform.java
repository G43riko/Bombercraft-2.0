package Bombercraft2.Bombercraft2.game.misc;

import Bombercraft2.Bombercraft2.StaticConfig;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

/**
 * Táto trieda by mala byť prístupná vždy ak niekto bude potrebovať zoom alebo offset
 */
public class GameTransform {
    private       float     minZoom;
    private final GVector2f maxOffset  = new GVector2f();

    private       float     zoom   = StaticConfig.DEFAULT_ZOOM;
    @NotNull
    private final GVector2f offset = new GVector2f();


    @NotNull
    public GVector2f transform(@NotNull GVector2f position) {
        return position.mul(zoom).sub(offset);
    }
    @NotNull
    public GVector2f transformInvert(@NotNull GVector2f position) {
        return position.add(offset).div(zoom);
    }

    public void addZoom(float value) {
        zoom = Math.max(minZoom, zoom + value);
    }

    public float getZoom() {
        return zoom;
    }
}
