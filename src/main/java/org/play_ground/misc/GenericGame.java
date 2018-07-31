package org.play_ground.misc;

import org.bombercraft2.core.GameStateType;
import org.engine.CoreEngine;
import org.jetbrains.annotations.NotNull;
import org.play_ground.SimpleAbstractGame;

public class GenericGame<T extends CoreEngine> extends SimpleAbstractGame<T> {
    public GenericGame(@NotNull T parent, @NotNull GameStateType type) {
        super(parent, type);
    }
}
