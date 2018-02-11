package Bombercraft2.playGround.Demos;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import Bombercraft2.playGround.Misc.SimpleMissile;
import Bombercraft2.playGround.Misc.ViewManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MissileDemo extends GameState implements SimpleGameAble {
    private final static GVector2f NUMBERS_OF_BLOCKS = new GVector2f(100, 100);
    private final CorePlayGround parent;
    private final List<SimpleMissile> missiles = new ArrayList<>();
    private final ViewManager    viewManager;

    public MissileDemo(CorePlayGround parent) {
        super(Type.MissileDemo);
        this.parent = parent;
        viewManager = new ViewManager(NUMBERS_OF_BLOCKS.mul(Config.BLOCK_SIZE),
                                      parent.getCanvas().getWidth(),
                                      parent.getCanvas().getHeight(),
                                      3);
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
        missiles.forEach(e -> e.render(g2));
    }

    @Override
    public void update(float delta) {
        missiles.forEach(e -> e.update(delta));
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }
        if (Input.getMouseUp(Input.BUTTON_LEFT)) {
            missiles.add(new SimpleMissile(Input.getMousePosition(), this));
        }
    }

    @Override
    public void doAct(GVector2f click) {

    }

    @Override
    public void onResize() {
        viewManager.setCanvasSize(parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
    }

    @Contract(pure = true)
    @Override
    public float getZoom() {
        return viewManager.getZoom();
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getCanvasSize() {
        return viewManager.getCanvasSize();
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getOffset() {
        return viewManager.getOffset();
    }
}
