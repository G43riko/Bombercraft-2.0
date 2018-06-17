package Bombercraft2.playGround.Demos;

import Bombercraft2.Bombercraft2.StaticConfig;
import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.GameStateType;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.ImagedBomb;
import Bombercraft2.playGround.Misc.PostFxManager;
import Bombercraft2.playGround.Misc.SimpleBomb;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import Bombercraft2.playGround.Misc.map.SimpleChunkedMap;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;

public class BombDemo extends GameState implements SimpleGameAble {
    private        ArrayList<SimpleBomb> bombs            = new ArrayList<>();
    private PostFxManager postFxManager;
    private final SimpleChunkedMap map;

    @NotNull
    CorePlayGround parent;

    public BombDemo(@NotNull CorePlayGround parent) {
        super(GameStateType.BombDemo);
        this.parent = parent;
        postFxManager = new PostFxManager(this, new GVector2f(parent.getCanvas().getWidth(), parent.getCanvas().getHeight()));
        map = new SimpleChunkedMap(this, new GVector2f(4, 4));
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
        map.render(g2);
        g2.drawImage(postFxManager.getImage(), 0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight(), null);

        bombs.forEach(a -> a.render(g2));
    }

    @Override
    public void update(float delta) {
        bombs.forEach(a -> a.update(delta));
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }
        if (Input.getMouseUp(Input.BUTTON_LEFT)) {
            ImagedBomb bomb = new ImagedBomb(Input.getMousePosition().sub(StaticConfig.BLOCK_SIZE_HALF), this);
            bomb.callback = (b) -> postFxManager.addImage(((ImagedBomb)b).getCrater(), b.getPosition(), b.getSize());
            bombs.add(bomb);
        }
    }



    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getCanvasSize() {
        return new GVector2f(parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
    }
}
