package Bombercraft2.playGround.Demos;

import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.ViewManager;
import Bombercraft2.playGround.Misc.map.MapManager;
import Bombercraft2.playGround.Misc.map.SimpleChunkedMap;
import Bombercraft2.playGround.SimpleAbstractGame;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public class ChunkedMapDemo extends SimpleAbstractGame<CorePlayGround> {

    public ChunkedMapDemo(@NotNull CorePlayGround parent) {
        super(parent, Type.ChunkedMapDemo);
        getManager().setManagers(new MapManager(new SimpleChunkedMap(this, new GVector2f(32, 32))));
        getManager().setManagers(new ViewManager(getManager().getMapManager().getMapSize(),
                                                 parent.getCanvas().getWidth(),
                                                 parent.getCanvas().getHeight(),
                                                 3));
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }

        if (Input.getMouseDown(Input.BUTTON_LEFT)) {
            System.out.println(getManager().getMapManager().getBlockOnAbsolutePos(Input.getMousePosition()));
        }
        doInput();
    }
}
