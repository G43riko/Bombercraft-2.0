package org.bombercraft2.core;

import org.glib2.interfaces.ClickAble;
import org.glib2.interfaces.InteractAbleG2;
import org.jetbrains.annotations.NotNull;

public abstract class GameState implements InteractAbleG2, ClickAble {
    private final GameStateType type;

    protected GameState(GameStateType type) {
        this.type = type;
    }

    @NotNull
    public GameStateType getType() {
        return type;
    }

    public void onResize() {}
}
