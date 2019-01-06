package org.play_ground.demos;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.core.GameStateType;
import org.engine.Input;
import org.jetbrains.annotations.NotNull;
import org.play_ground.CorePlayGround;
import org.play_ground.SimpleAbstractGame;
import org.play_ground.misc.SimpleMissile;
import org.play_ground.misc.ViewManager;
import org.utils.enums.Keys;
import utils.math.BVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MissileDemo extends SimpleAbstractGame<CorePlayGround> {
    private final static BVector2f           NUMBERS_OF_BLOCKS = new BVector2f(100, 100);
    private final        List<SimpleMissile> missiles          = new ArrayList<>();

    public MissileDemo(CorePlayGround parent) {
        super(parent, GameStateType.MissileDemo);
        getManager().setManagers(new ViewManager(NUMBERS_OF_BLOCKS.getMul(StaticConfig.BLOCK_SIZE),
                                                 parent.getCanvas().getWidth(),
                                                 parent.getCanvas().getHeight(),
                                                 3));
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
        if (Input.getKeyDown(Keys.ESCAPE)) {
            parent.stopDemo();
        }
        if (Input.getMouseUp(Input.BUTTON_LEFT)) {
            missiles.add(new SimpleMissile(Input.getMousePosition(), this));
        }
        doInput();
    }
}
