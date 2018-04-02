package Bombercraft2.playGround.Demos;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.components.path.PathFinder;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.ViewManager;
import Bombercraft2.playGround.Misc.drawableLine.BasicDrawablePath;
import Bombercraft2.playGround.Misc.map.SimpleMap;
import Bombercraft2.playGround.SimpleAbstractGame;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

public class MapDemo extends SimpleAbstractGame<CorePlayGround> {
    private final static GVector2f NUMBERS_OF_BLOCKS = new GVector2f(100, 100);
    private final        SimpleMap map;

    private GVector2f         firstClick;
    private BasicDrawablePath path;

    public MapDemo(CorePlayGround parent) {
        super(parent, Type.MapDemo);
        getManager().setManagers(new ViewManager(NUMBERS_OF_BLOCKS.mul(Config.BLOCK_SIZE),
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
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }

        if (Input.getMouseDown(Input.BUTTON_LEFT)) {
            if (firstClick == null) {
                firstClick = Input.getMousePosition();
            }
            else {
                final GVector2f start = getManager().getViewManager().transformInvert(firstClick)
                                                    .div(Config.BLOCK_SIZE)
                                                    .toInt();
                final GVector2f end = getManager().getViewManager().transformInvert(Input.getMousePosition())
                                                  .div(Config.BLOCK_SIZE)
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
