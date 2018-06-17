package Bombercraft2.playGround.Misc;

import Bombercraft2.Bombercraft2.core.GameStateType;
import Bombercraft2.engine.CoreEngine;
import Bombercraft2.playGround.Misc.map.MapManager;
import Bombercraft2.playGround.Misc.map.SimpleChunkedMap;
import org.jetbrains.annotations.NotNull;
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
