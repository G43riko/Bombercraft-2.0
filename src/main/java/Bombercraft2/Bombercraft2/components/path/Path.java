package Bombercraft2.Bombercraft2.components.path;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Path extends Entity<GameAble> {
    private ArrayList<GVector2f> points;

    public Path(@NotNull GameAble parent, @NotNull GVector2f start, @NotNull GVector2f end) {
        super(new GVector2f(), parent);
        HashMap<String, Integer> h = new HashMap<>();

        getParent().getLevel()
                   .getMap()
                   .getBlocks()
                   .forEach(a -> h.put(a.getPosition().div(Config.BLOCK_SIZE).toString(), a.isWalkable() ? 0 : 1));
        new Thread(() -> {
            points = PathFinder.findPath(h, start.toString(), end.toString(), true);
            if (!points.isEmpty()) {
                points.add(0, end);
                points.add(start);
            }
        }).start();
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        if (points == null) { return; }
        g2.setColor(Color.blue);

        for (int i = 1; i < points.size(); i++) {
            GVector2f a = points.get(i)
                                .mul(Config.BLOCK_SIZE)
                                .add(Config.BLOCK_SIZE_HALF)
                                .mul(getParent().getZoom())
                                .sub(getParent().getOffset());
            GVector2f b = points.get(i - 1)
                                .mul(Config.BLOCK_SIZE)
                                .add(Config.BLOCK_SIZE_HALF)
                                .mul(getParent().getZoom())
                                .sub(getParent().getOffset());
            g2.drawLine(a.getXi(), a.getYi(), b.getXi(), b.getYi());
        }
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public JSONObject toJSON() {
        return null;
    }

    @Override
    public GVector2f getSur() {
        return null;
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getSize() {
        return Config.BLOCK_SIZE.mul(getParent().getLevel().getMap().getNumberOfBlocks()).mul(getParent().getZoom());
    }
}
