package Bombercraft2.Bombercraft2.core;

import Bombercraft2.Bombercraft2.gui.ClickAble;
import org.jetbrains.annotations.NotNull;

public abstract class GameState implements InteractAble, ClickAble {
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
