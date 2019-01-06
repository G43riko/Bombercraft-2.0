package org.play_ground;

import org.bombercraft2.core.GameStateType;
import org.engine.CoreEngine;
import org.jetbrains.annotations.NotNull;
import utils.math.BVector2f;

public abstract class AdvancedAbstractGame<T extends CoreEngine> extends SimpleAbstractGame<T> {
    protected AdvancedAbstractGame(@NotNull T parent, @NotNull GameStateType type) {
        super(parent, type);
    }

    @NotNull
    public BVector2f transform(@NotNull BVector2f position) {
        return position.getMul(getZoom()).getSub(getOffset());
    }

    @NotNull
    public BVector2f transformInvert(@NotNull BVector2f position) {
        return position.getAdd(getOffset()).getDiv(getZoom());
    }


}
