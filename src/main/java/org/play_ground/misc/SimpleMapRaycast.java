package org.play_ground.misc;

import org.bombercraft2.core.Visible;
import org.glib2.math.vectors.GVector2f;
import org.play_ground.misc.map.AbstractMap;
import org.play_ground.misc.map.SimpleTypedBlock;

import java.util.function.BiFunction;

public class SimpleMapRaycast<T extends Visible> {
    private final AbstractMap<T>                                   map;
    private       SimpleTypedBlock                                 firstTarget;
    private       GVector2f                                        lastBlock;
    private       BiFunction<SimpleTypedBlock, GVector2f, Boolean> onHit;

    public SimpleMapRaycast(AbstractMap<T> map) {
        this.map = map;
    }


    private GVector2f getChangedBlockPosition(int x, int y) {
        final int fx = (int) Math.floor(x / map.getBlockSize().getX());
        final int fy = (int) Math.floor(y / map.getBlockSize().getY());

        if (lastBlock == null || lastBlock.getXi() != fx || lastBlock.getYi() != fy) {
            return new GVector2f(fx, fy);
        }
        return null;
    }


    /**
     * @param x -
     * @param y -
     * @return - True if ray shouldn't continue
     */
    private boolean onPointDraw(int x, int y) {
        if (onHit == null) {
            return false;
        }
        GVector2f actBlock = getChangedBlockPosition(x, y);
        // ak je iný blok ako naposledy
        if (actBlock != null) {
            lastBlock = actBlock;
            SimpleTypedBlock block = map.getBlockOnPos(lastBlock);
            if (!block.getType().isWalkable()) {
                return onHit.apply(block, new GVector2f(x, y));
            }
        }
        return false;
    }

    public void onHit(BiFunction<SimpleTypedBlock, GVector2f, Boolean> callback) {
        onHit = callback;
    }

    public SimpleTypedBlock getFirstBlock(GVector2f start, GVector2f end) {
        BiFunction<SimpleTypedBlock, GVector2f, Boolean> tmpCallBack = onHit;
        onHit = (SimpleTypedBlock block, GVector2f sur) -> {
            firstTarget = block;
            return true;
        };
        drawLine(start, end);
        onHit = tmpCallBack;
        return firstTarget;
    }

    public void drawLine(GVector2f start, GVector2f end) {
        drawLine(start.getXi(), start.getYi(), end.getXi(), end.getYi());
    }

    // TODO: treba kontrolovať nech sa nevykresluje zbyčne(mimo mapy, mimo view);
    private void drawLine(int x1, int y1, int x2, int y2) {
        int d = 0;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int dx2 = dx << 1; // slope scaling factors to
        int dy2 = dy << 1; // avoid floating point

        final int ix = x1 < x2 ? 1 : -1; // increment direction
        final int iy = y1 < y2 ? 1 : -1;

        int x = x1;
        int y = y1;

        if (dx >= dy) {
            while (!onPointDraw(x, y) && x != x2) {
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            }
        }
        else {
            while (!onPointDraw(x, y) && y != y2) {
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
        }
    }

}
