package Bombercraft2.playGround.Demos.chunkedMapDemo;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import Bombercraft2.playGround.Misc.ViewManager;
import Bombercraft2.playGround.Misc.map.SimpleChunkedMap;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public class ChunkedMapDemo extends GameState implements SimpleGameAble {
    private static GVector2f NUMBER_OF_CHUNKS = new GVector2f(32, 32);
    private final ViewManager      viewManager;
    private final SimpleChunkedMap map;
    private final CorePlayGround   parent;

    public ChunkedMapDemo(@NotNull CorePlayGround parent) {
        super(Type.ChunkedMapDemo);
        viewManager = new ViewManager(NUMBER_OF_CHUNKS.mul(Block.SIZE).mul(Config.CHUNK_SIZE),
                                      parent.getCanvas().getWidth(),
                                      parent.getCanvas().getHeight(),
                                      3);
        this.parent = parent;
        map = new SimpleChunkedMap(this, NUMBER_OF_CHUNKS);
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
        map.render(g2);
    }

    @Override
    public void doAct(GVector2f click) {

    }

    @Contract(pure = true)
    @Override
    public float getZoom() {
        return viewManager.getZoom();
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getOffset() {
        return viewManager.getOffset();
    }


    @Override
    public void input() {
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }

        viewManager.input();
    }

    @Override
    public boolean isVisible(@NotNull Visible b) {
        return !(b.getPosition().getX() * getZoom() + b.getSize().getX() * getZoom() < getOffset().getX() ||
                b.getPosition().getY() * getZoom() + b.getSize().getY() * getZoom() < getOffset().getY() ||
                getOffset().getX() + parent.getCanvas().getWidth() < b.getPosition().getX() * getZoom() ||
                getOffset().getY() + parent.getCanvas().getHeight() < b.getPosition().getY() * getZoom());
    }

	@Contract(pure = true)
    @NotNull
    @Override
	public GVector2f getCanvasSize() {
		return new GVector2f(parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
	}
}
