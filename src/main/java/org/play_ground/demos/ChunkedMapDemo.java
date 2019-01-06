package org.play_ground.demos;

import org.bombercraft2.core.GameStateType;
import org.engine.Input;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.play_ground.CorePlayGround;
import org.play_ground.SimpleAbstractGame;
import org.play_ground.misc.ViewManager;
import org.play_ground.misc.map.MapManager;
import org.play_ground.misc.map.SimpleChunkedMap;
import org.utils.enums.Keys;

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
