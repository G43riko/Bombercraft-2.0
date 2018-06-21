package org.playGround;

import org.bombercraft2.core.GameStateType;
import org.engine.CoreEngine;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

public abstract class AdvancedAbstractGame<T extends CoreEngine> extends SimpleAbstractGame<T> {
    protected AdvancedAbstractGame(@NotNull T parent, @NotNull GameStateType type) {
        super(parent, type);
    }

    @NotNull
    public GVector2f transform(@NotNull GVector2f position) {
        return position.mul(getZoom()).sub(getOffset());
    }

    @NotNull
    public GVector2f transformInvert(@NotNull GVector2f position) {
        return position.add(getOffset()).div(getZoom());
    }


}