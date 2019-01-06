package org.play_ground.demos;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.core.GameState;
import org.bombercraft2.core.GameStateType;
import org.engine.Input;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.play_ground.CorePlayGround;
import org.play_ground.misc.ImagedBomb;
import org.play_ground.misc.PostFxManager;
import org.play_ground.misc.SimpleBomb;
import org.play_ground.misc.SimpleGameAble;
import org.play_ground.misc.map.SimpleChunkedMap;
import org.utils.enums.Keys;

import java.awt.*;
import java.util.ArrayList;

public class BombDemo extends GameState implements SimpleGameAble {
    private final SimpleChunkedMap map;
    @NotNull
    CorePlayGround parent;
    private ArrayList<SimpleBomb> bombs = new ArrayList<>();
    private PostFxManager         postFxManager;

    public BombDemo(@NotNull CorePlayGround parent) {
        super(GameStateType.BombDemo);
        this.parent = parent;
        postFxManager = new PostFxManager(this,
                                          new GVector2f(parent.getCanvas().getWidth(), parent.getCanvas().getHeight()));
        map = new SimpleChunkedMap(this, new GVector2f(4, 4));
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
        map.render(g2);
        g2.drawImage(postFxManager.getImage(),
                     0,
                     0,
                     parent.getCanvas().getWidth(),
                     parent.getCanvas().getHeight(),
                     null);

        bombs.forEach(a -> a.render(g2));
    }

    @Override
    public void update(float delta) {
        bombs.forEach(a -> a.update(delta));
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Keys.ESCAPE)) {
            parent.stopDemo();
        }
        if (Input.getMouseUp(Input.BUTTON_LEFT)) {
            ImagedBomb bomb = new ImagedBomb(Input.getMousePosition().getSub(StaticConfig.BLOCK_SIZE_HALF), this);
            bomb.callback = (b) -> postFxManager.addImage(((ImagedBomb) b).getCrater(), b.getPosition(), b.getSize());
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
