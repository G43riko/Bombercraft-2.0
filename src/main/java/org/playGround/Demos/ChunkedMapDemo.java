package org.playGround.Demos;

import org.bombercraft2.core.GameStateType;
import org.engine.Input;
import org.jetbrains.annotations.NotNull;
import org.playGround.CorePlayGround;
import org.playGround.Misc.ViewManager;
import org.playGround.Misc.map.MapManager;
import org.playGround.Misc.map.SimpleChunkedMap;
import org.playGround.SimpleAbstractGame;
import org.utils.enums.Keys;
import utils.math.GVector2f;

public class ChunkedMapDemo extends SimpleAbstractGame<CorePlayGround> {

    public ChunkedMapDemo(@NotNull CorePlayGround parent) {
        super(parent, GameStateType.ChunkedMapDemo);
        getManager().setManagers(new MapManager(new SimpleChunkedMap(this, new GVector2f(32, 32))));
        getManager().setManagers(new ViewManager(getManager().getMapManager().getMapSize(),
                                                 parent.getCanvas().getWidth(),
                                                 parent.getCanvas().getHeight(),
                                                 3));
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Keys.ESCAPE)) {
            parent.stopDemo();
        }

        if (Input.getMouseDown(Input.BUTTON_LEFT)) {
            System.out.println(getManager().getMapManager().getBlockOnAbsolutePos(Input.getMousePosition()));
        }
        doInput();
    }
}
