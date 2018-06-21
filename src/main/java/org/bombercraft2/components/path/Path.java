package org.bombercraft2.components.path;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.GLogger;
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
                   .forEach(a -> h.put(a.getPosition().div(StaticConfig.BLOCK_SIZE).toString(),
                                       a.isWalkable() ? 0 : 1));
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
                                .mul(StaticConfig.BLOCK_SIZE)
                                .add(StaticConfig.BLOCK_SIZE_HALF)
                                .mul(getParent().getZoom())
                                .sub(getParent().getOffset());
            GVector2f b = points.get(i - 1)
                                .mul(StaticConfig.BLOCK_SIZE)
                                .add(StaticConfig.BLOCK_SIZE_HALF)
                                .mul(getParent().getZoom())
                                .sub(getParent().getOffset());
            g2.drawLine(a.getXi(), a.getYi(), b.getXi(), b.getYi());
        }
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        try {
            result.put("points", new JSONArray(points));
        }
        catch (JSONException e) {
            GLogger.error(GLogger.GError.MAP_SERIALIZATION_FAILED, e);
        }
        return result;
    }

    @Override
    public GVector2f getSur() {
        return null;
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getSize() {
        return StaticConfig.BLOCK_SIZE.mul(getParent().getLevel().getMap().getNumberOfBlocks())
                                      .mul(getParent().getZoom());
    }
}
