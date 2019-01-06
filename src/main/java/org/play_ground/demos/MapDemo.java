package org.play_ground.demos;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.components.path.PathFinder;
import org.bombercraft2.core.GameStateType;
import org.engine.Input;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.play_ground.CorePlayGround;
import org.play_ground.SimpleAbstractGame;
import org.play_ground.misc.ViewManager;
import org.play_ground.misc.drawable_line.BasicDrawablePath;
import org.play_ground.misc.map.SimpleMap;
import org.utils.enums.Keys;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MapDemo extends SimpleAbstractGame<CorePlayGround> {
    private final static GVector2f NUMBERS_OF_BLOCKS = new GVector2f(100, 100);
    private final        SimpleMap map;

    private GVector2f         firstClick;
    private BasicDrawablePath path;

    public MapDemo(CorePlayGround parent) {
        super(parent, GameStateType.MapDemo);
        getManager().setManagers(new ViewManager(NUMBERS_OF_BLOCKS.getMul(StaticConfig.BLOCK_SIZE),
                                                 parent.getCanvas().getWidth(),
                                                 parent.getCanvas().getHeight(),
                                                 3));
        map = new SimpleMap(this, NUMBERS_OF_BLOCKS);

    }


    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
        map.render(g2);

        if (path != null) {
            path.render(g2);
        }
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Keys.ESCAPE)) {
            parent.stopDemo();
        }

        if (Input.getMouseDown(Input.BUTTON_LEFT)) {
            if (firstClick == null) {
                firstClick = Input.getMousePosition();
            }
            else {
                final GVector2f start = getManager().getViewManager().transformInvert(firstClick)
                        .getDiv(StaticConfig.BLOCK_SIZE)
                                                    .toInt();
                final GVector2f end = getManager().getViewManager().transformInvert(Input.getMousePosition())
                        .getDiv(StaticConfig.BLOCK_SIZE)
                                                  .toInt();
                final List<GVector2f> result = new ArrayList<>();
                result.add(end);
                result.addAll(PathFinder.findPath(map.getHashMap(),
                                                  start.toString(),
                                                  end.toString(), false));
                result.add(start);
                path = new BasicDrawablePath(this, result);
                firstClick = null;
            }
        }
        doInput();
    }

    @Override
    public void update(float delta) {
        if (path != null) {
            path.update(delta);
        }
    }
}
