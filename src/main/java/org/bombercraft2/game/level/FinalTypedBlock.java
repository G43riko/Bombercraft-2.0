package org.bombercraft2.game.level;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.core.Texts;
import org.bombercraft2.game.GameAble;
import org.bombercraft2.game.misc.GCanvas;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.play_ground.misc.map.SimpleTypedBlock;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public final class FinalTypedBlock extends SimpleTypedBlock {
    private int health;

    public FinalTypedBlock(@NotNull JSONObject object, @NotNull GameAble parent) {
        super(new GVector2f(), 0, parent);

        fromJSON(object);
    }

    public FinalTypedBlock(@NotNull GVector2f position, int type, @NotNull GameAble parent) {
        super(position, type, parent);
        this.health = this.type.getHealth();
    }

    private static GVector2f getInterSect(GVector2f position, GVector2f ss, GVector2f se, GVector2f blockSize) {
        ArrayList<GVector2f> res = new ArrayList<>();

        GVector2f p = position.getMul(blockSize);
        res.add(org.glib2.math.physics.LineLineIntersect.linesIntersect(ss,
                                                                        se,
                                                                        p.getAdd(new GVector2f(blockSize.getX(),
                                                                                               0)),
                                                                        p));
        res.add(org.glib2.math.physics.LineLineIntersect.linesIntersect(ss,
                                                                        se,
                                                                        p.getAdd(new GVector2f(0,
                                                                                               blockSize.getY())),
                                                                        p));
        res.add(org.glib2.math.physics.LineLineIntersect.linesIntersect(ss,
                                                                        se,
                                                                        p.getAdd(new GVector2f(
                                                                                blockSize.getX(),
                                                                                0)),
                                                                        p.getAdd(blockSize)));
        res.add(org.glib2.math.physics.LineLineIntersect.linesIntersect(ss,
                                                                        se,
                                                                        p.getAdd(new GVector2f(0,
                                                                                               blockSize.getY())),
                                                                        p.getAdd(blockSize)));

        res = res.stream().filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
        if (res.size() == 0) {
            return null;
        }

        return res.stream().reduce((a, b) -> a.dist(ss) < b.dist(ss) ? a : b).get();
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
//		if(type == NOTHING)
//			return;
        GVector2f size = StaticConfig.BLOCK_SIZE.getMul(parent.getZoom());
        GVector2f pos = position.getMul(size).getSub(parent.getOffset());

        GCanvas.drawImage(g2, type.getImage(), pos, size);
        // g2.drawImage(type.getImage(), pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), null);
    }

    @Override
    public void fromJSON(@NotNull JSONObject json) {
        super.fromJSON(json);
        jsonWrapper(() -> health = json.getInt(Texts.HEALTH));
    }

    //OTHERS

    @Contract(pure = true)
    @NotNull
    public JSONObject toJSON() {
        JSONObject result = super.toJSON();

        jsonWrapper(() -> result.put(Texts.HEALTH, health));

        return result;
    }

    public boolean hit(int damage) {
        health -= damage;
        boolean res = health <= 0;
        if (res) {
            remove();
        }
        return res;
    }

    public void remove() {
        remove(true);
    }

    /*
    public void drawSprites(Graphics2D g2) {
        FinalTypedBlock t = getParent().getLevel().getHashMap().getBlock(scale.getXi(), scale.getYi() - 1);
        FinalTypedBlock b = getParent().getLevel().getHashMap().getBlock(scale.getXi(), scale.getYi() + 1);
        FinalTypedBlock r = getParent().getLevel().getHashMap().getBlock(scale.getXi() + 1, scale.getYi());
        FinalTypedBlock l = getParent().getLevel().getHashMap().getBlock(scale.getXi() - 1, scale.getYi());

        GVector2f size = SIZE.mul(parent.getZoom());
        GVector2f pos = scale.mul(size).getSub(parent.getOffset());

        if (t != null && t.getType() != type) {
            g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 8, 4),
                         pos.getXi(),
                         pos.getYi() - StaticConfig.BLOCK_SIZE.getYi(),
                         size.getXi(),
                         size.getYi(),
                         null);
        }

        if (b != null && b.getType() != type) {
            g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 2, 4),
                         pos.getXi(),
                         pos.getYi() + StaticConfig.BLOCK_SIZE.getYi(),
                         size.getXi(),
                         size.getYi(),
                         null);
        }

        if (r != null && r.getType() != type) {
            g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 1, 4),
                         pos.getXi() + StaticConfig.BLOCK_SIZE.getXi(),
                         pos.getYi(),
                         size.getXi(),
                         size.getYi(),
                         null);
        }

        if (l != null && l.getType() != type) {
            g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 4, 4),
                         pos.getXi() - StaticConfig.BLOCK_SIZE.getXi(),
                         pos.getYi(),
                         size.getXi(),
                         size.getYi(),
                         null);
        }
    }
    */

    private void remove(boolean addExplosion) {
        type = BlockType.NOTHING;
        health = 0;
    }

    public void drawShadow(@NotNull Graphics2D g2, @NotNull Color color, int length, int angle) {
        if (type == BlockType.NOTHING) {
            return;
        }

        double finalAngle = Math.toRadians(angle + 90);
        GVector2f offset = new GVector2f(-Math.cos(finalAngle), Math.sin(finalAngle)).getMul(length);
        GVector2f pos = position.getMul(StaticConfig.BLOCK_SIZE).getSub(parent.getOffset());
        g2.setColor(color);

        /*   2---3
         *  /    |
         * 1     4
         *      /
         *     5
         *
         */
        int[] xPos = new int[]{
                (int) (pos.getX()),
                (int) (pos.getAdd(offset).getX()),
                (int) (pos.getAdd(offset).getX() + StaticConfig.BLOCK_SIZE.getX()),
                (int) (pos.getAdd(offset).getX() + StaticConfig.BLOCK_SIZE.getX()),
                (int) (pos.getXi() + StaticConfig.BLOCK_SIZE.getX())
        };

        int[] yPos = new int[]{
                (int) (pos.getY()),
                (int) (pos.getSub(offset).getY()),
                (int) (pos.getSub(offset).getY()),
                (int) (pos.getY() + StaticConfig.BLOCK_SIZE.getY() - offset.getY()),
                (int) (pos.getY() + StaticConfig.BLOCK_SIZE.getY())
        };

        g2.fillPolygon(xPos, yPos, 5);
    }

    /*
    public void renderWalls(Graphics2D g2) {
        Map map = getParent().getLevel().getHashMap();
        boolean t = map.isWalkable(scale.getXi(), scale.getYi() - 1);
        boolean b = map.isWalkable(scale.getXi(), scale.getYi() + 1);
        boolean l = map.isWalkable(scale.getXi() - 1, scale.getYi());
        boolean r = map.isWalkable(scale.getXi() + 1, scale.getYi());

        GVector2f size = SIZE.mul(parent.getZoom());
        GVector2f pos = scale.mul(size).getSub(parent.getOffset());

        int offset = (int) (StaticConfig.WALL_OFFSET * parent.getZoom());

        GVector2f p0 = pos.getSub(offset);
        GVector2f p1 = pos.getAdd(size).getSub(new GVector2f(-offset, offset + size.getY()));
        GVector2f p2 = pos.getAdd(size).getAdd(offset);
        GVector2f p3 = pos.getAdd(size).getSub(new GVector2f(offset + size.getX(), -offset));

        FinalTypedBlock block0 = map.getBlock(scale.getXi() - 1, scale.getYi() - 1);
        FinalTypedBlock block1 = map.getBlock(scale.getXi() + 1, scale.getYi() - 1);
        FinalTypedBlock block2 = map.getBlock(scale.getXi() + 1, scale.getYi() + 1);
        FinalTypedBlock block3 = map.getBlock(scale.getXi() - 1, scale.getYi() + 1);
        boolean val0 = block0 != null && !block0.isWalkable();
        boolean val1 = block1 != null && !block1.isWalkable();
        boolean val2 = block2 != null && !block2.isWalkable();
        boolean val3 = block3 != null && !block3.isWalkable();


        boolean walls3D = true;
        if (walls3D) {
            g2.setColor(StaticConfig.WALL_DARKER_COLOR);
            if (t) {
                g2.fillPolygon(new int[]{pos.getXi(),
                                         pos.getXi() + size.getXi(),
                                         p1.getXi() + (val1 ? -offset : 0),
                                         p0.getXi() + (true ? +offset * 2 : 0)},
                               new int[]{pos.getYi(), pos.getYi(), p1.getYi(), p0.getYi()},
                               4);
            }
            g2.setColor(StaticConfig.WALL_LIGHTER_COLOR);
            if (r) {
                g2.fillPolygon(new int[]{pos.getXi() + size.getXi(),
                                         pos.getXi() + size.getXi(),
                                         p2.getXi(),
                                         p1.getXi()},
                               new int[]{pos.getYi(),
                                         pos.getYi() + size.getYi(),
                                         p2.getYi() + (true ? -offset * 2 : 0),
                                         p1.getYi() + (val1 ? +offset : 0)},
                               4);
            }

        }
        else {
            int i = offset * 2;
            g2.setColor(StaticConfig.WALL_LIGHTER_COLOR);
            if (t) {
                g2.fillPolygon(new int[]{pos.getXi(),
                                         pos.getXi() + size.getXi(),
                                         p1.getXi() + (val1 ? -i : 0),
                                         p0.getXi() + (val0 ? +i : 0)},
                               new int[]{pos.getYi(), pos.getYi(), p1.getYi(), p0.getYi()},
                               4);
            }
            if (r) {
                g2.fillPolygon(new int[]{pos.getXi() + size.getXi(),
                                         pos.getXi() + size.getXi(),
                                         p2.getXi(),
                                         p1.getXi()},
                               new int[]{pos.getYi(),
                                         pos.getYi() + size.getYi(),
                                         p2.getYi() + (val2 ? -i : 0),
                                         p1.getYi() + (val1 ? +i : 0)},
                               4);
            }

            g2.setColor(StaticConfig.WALL_DARKER_COLOR);
            if (b) {
                g2.fillPolygon(new int[]{pos.getXi(),
                                         p3.getXi() + (val3 ? +i : 0),
                                         p2.getXi() + (val2 ? -i : 0),
                                         pos.getXi() + size.getXi()},
                               new int[]{pos.getYi() + size.getYi(),
                                         p3.getYi(),
                                         p2.getYi(),
                                         pos.getYi() + size.getYi()},
                               4);
            }
            if (l) {
                g2.fillPolygon(new int[]{pos.getXi(), p0.getXi(), p3.getXi(), pos.getXi()},
                               new int[]{pos.getYi(),
                                         p0.getYi() + (val0 ? +i : 0),
                                         p3.getYi() + (val3 ? -i : 0),
                                         pos.getYi() + size.getYi()},
                               4);
            }
        }
    }
    */

    public void build(@NotNull BlockType type) {
        this.type = type;
        this.health = type.getHealth();
    }

    @Contract(pure = true)
    @NotNull
    public GVector2f getSur() {return position/*.getDiv(SIZE).toInt()*/;}

    @Contract(pure = true)
    @NotNull
    public GVector2f getPosition() {return position.getMul(StaticConfig.BLOCK_SIZE);}

    @Contract(pure = true)
    @NotNull
    public BlockType getType() {return type;}

    @Contract(pure = true)
    public int getHealth() {return health;}

    @Contract(pure = true)
    public boolean isWalkable() {return type.isWalkable();}
}
