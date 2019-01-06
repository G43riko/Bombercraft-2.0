package org.play_ground;

import org.bombercraft2.core.GameStateType;
import org.engine.CoreEngine;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;

public abstract class AdvancedAbstractGame<T extends CoreEngine> extends SimpleAbstractGame<T> {
    protected AdvancedAbstractGame(@NotNull T parent, @NotNull GameStateType type) {
        super(parent, type);
    }

    @NotNull
    public GVector2f transform(@NotNull GVector2f position) {
        return position.getMul(getZoom()).getSub(getOffset());
    }

    @NotNull
    public GVector2f transformInvert(@NotNull GVector2f position) {
        return position.getAdd(getOffset()).getDiv(getZoom());
    }


}
