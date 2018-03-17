package Bombercraft2.playGround.Demos;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.ViewManager;
import Bombercraft2.playGround.Misc.map.SimpleChunkedMap;
import Bombercraft2.playGround.SimpleAbstractGame;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public class ChunkedMapDemo extends SimpleAbstractGame<CorePlayGround> {
    private static GVector2f NUMBER_OF_CHUNKS = new GVector2f(32, 32);
    private final SimpleChunkedMap map;

    public ChunkedMapDemo(@NotNull CorePlayGround parent) {
        super(parent, Type.ChunkedMapDemo);
        setViewManager(new ViewManager(NUMBER_OF_CHUNKS.mul(Config.BLOCK_SIZE).mul(Config.CHUNK_SIZE),
                                       parent.getCanvas().getWidth(),
                                       parent.getCanvas().getHeight(),
                                       3));
        map = new SimpleChunkedMap(this, NUMBER_OF_CHUNKS);
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
        map.render(g2);
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }

        if (Input.getMouseDown(Input.BUTTON_LEFT)) {
            System.out.println(map.getBlockOnAbsolutePos(Input.getMousePosition()));
        }
        doInput();
    }
}
