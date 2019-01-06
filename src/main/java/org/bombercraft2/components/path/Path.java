package org.bombercraft2.components.path;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.entity.Entity;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.GLogger;

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
                .forEach(a -> h.put(a.getPosition().getDiv(StaticConfig.BLOCK_SIZE).toString(),
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
                    .getMul(StaticConfig.BLOCK_SIZE)
                    .getAdd(StaticConfig.BLOCK_SIZE_HALF)
                    .getMul(getParent().getZoom())
                    .getSub(getParent().getOffset());
            GVector2f b = points.get(i - 1)
                    .getMul(StaticConfig.BLOCK_SIZE)
                    .getAdd(StaticConfig.BLOCK_SIZE_HALF)
                    .getMul(getParent().getZoom())
                    .getSub(getParent().getOffset());
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
        return StaticConfig.BLOCK_SIZE.getMul(getParent().getLevel().getMap().getNumberOfBlocks())
                .getMul(getParent().getZoom());
    }
}
