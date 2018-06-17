package Bombercraft2.playGround.Misc;

import Bombercraft2.Bombercraft2.core.GameStateType;
import Bombercraft2.engine.CoreEngine;
import Bombercraft2.playGround.SimpleAbstractGame;
import org.jetbrains.annotations.NotNull;

public class  GenericGame <T extends CoreEngine> extends SimpleAbstractGame<T> {
    public GenericGame(@NotNull T parent, @NotNull GameStateType type) {
        super(parent, type);
    }
}
