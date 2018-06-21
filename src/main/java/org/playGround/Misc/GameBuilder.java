package org.playGround.Misc;

import org.bombercraft2.core.GameStateType;
import org.engine.CoreEngine;
import org.jetbrains.annotations.NotNull;
import org.playGround.Misc.map.MapManager;
import org.playGround.Misc.map.SimpleChunkedMap;
import utils.math.GVector2f;

public class GameBuilder {

    public <T> T getName(T name) {
        return name;
    }

    public <T extends CoreEngine> GenericGame build(@NotNull T parent) {
        GenericGame result = new GenericGame<T>(parent, GameStateType.GenericGame);

        final SimpleChunkedMap map = new SimpleChunkedMap(result, new GVector2f(2, 2));

        result.getManager().setManagers(new MapManager(map));
        return result;
    }
}