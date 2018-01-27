package Bombercraft2.Bombercraft2.game.level;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.playGround.Misc.SimpleTypedBlock;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import utils.GLogger;
import utils.math.GVector2f;
import utils.math.LineLineIntersect;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public final class FinalTypedBlock extends SimpleTypedBlock {
    public final static GVector2f SIZE = Config.BLOCK_SIZE;

    private int health;


    public FinalTypedBlock(@NotNull JSONObject object, @NotNull GameAble parent) {
        super(new GVector2f(), 0, parent);

        try {
            position = new GVector2f(object.getString(Texts.POSITION));
            health = object.getInt(Texts.HEALTH);
            type = Block.Type.valueOf(object.getString(Texts.TYPE));
        }
        catch (JSONException e) {
            GLogger.error(GLogger.GError.CANNOT_PARSE_BLOCK, e);
        }
    }

    public FinalTypedBlock(@NotNull GVector2f position, int type, @NotNull GameAble parent) {
        super(position, type, parent);
        this.health = this.type.getHealth();
    }


    @Override
    public void render(@NotNull Graphics2D g2) {
//		if(type == NOTHING)
//			return;
        GVector2f size = SIZE.mul(parent.getZoom());
        GVector2f pos = position.mul(size).sub(parent.getOffset());

        g2.drawImage(type.getImage(), pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), null);
    }

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();

        try {
            result.put(Texts.HEALTH, health);
            result.put(Texts.TYPE, type);
            result.put(Texts.POSITION, position);
        }
        catch (JSONException e) {
            GLogger.error(GLogger.GError.CANNOT_SERIALIZE_BLOCK, e);
        }

        return result;
    }

    //OTHERS

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

    private void remove(boolean addExplosion) {
        type = Block.Type.NOTHING;
        health = 0;
    }

    /*
    public void drawSprites(Graphics2D g2) {
        FinalTypedBlock t = getParent().getLevel().getMap().getBlock(position.getXi(), position.getYi() - 1);
        FinalTypedBlock b = getParent().getLevel().getMap().getBlock(position.getXi(), position.getYi() + 1);
        FinalTypedBlock r = getParent().getLevel().getMap().getBlock(position.getXi() + 1, position.getYi());
        FinalTypedBlock l = getParent().getLevel().getMap().getBlock(position.getXi() - 1, position.getYi());

        GVector2f size = SIZE.mul(parent.getZoom());
        GVector2f pos = position.mul(size).sub(parent.getOffset());

        if (t != null && t.getType() != type) {
            g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 8, 4),
                         pos.getXi(),
                         pos.getYi() - FinalTypedBlock.SIZE.getYi(),
                         size.getXi(),
                         size.getYi(),
                         null);
        }

        if (b != null && b.getType() != type) {
            g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 2, 4),
                         pos.getXi(),
                         pos.getYi() + FinalTypedBlock.SIZE.getYi(),
                         size.getXi(),
                         size.getYi(),
                         null);
        }

        if (r != null && r.getType() != type) {
            g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 1, 4),
                         pos.getXi() + FinalTypedBlock.SIZE.getXi(),
                         pos.getYi(),
                         size.getXi(),
                         size.getYi(),
                         null);
        }

        if (l != null && l.getType() != type) {
            g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 4, 4),
                         pos.getXi() - FinalTypedBlock.SIZE.getXi(),
                         pos.getYi(),
                         size.getXi(),
                         size.getYi(),
                         null);
        }
    }
    */

    public void drawShadow(@NotNull Graphics2D g2, @NotNull Color color, int length, int angle) {
        if (type == Block.Type.NOTHING) {
            return;
        }

        double finalAngle = Math.toRadians(angle + 90);
        GVector2f offset = new GVector2f(-Math.cos(finalAngle), Math.sin(finalAngle)).mul(length);
        GVector2f pos = position.mul(FinalTypedBlock.SIZE).sub(parent.getOffset());
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
                (int) (pos.add(offset).getX()),
                (int) (pos.add(offset).getX() + FinalTypedBlock.SIZE.getX()),
                (int) (pos.add(offset).getX() + FinalTypedBlock.SIZE.getX()),
                (int) (pos.getXi() + FinalTypedBlock.SIZE.getX())
        };

        int[] yPos = new int[]{
                (int) (pos.getY()),
                (int) (pos.sub(offset).getY()),
                (int) (pos.sub(offset).getY()),
                (int) (pos.getY() + FinalTypedBlock.SIZE.getY() - offset.getY()),
                (int) (pos.getY() + FinalTypedBlock.SIZE.getY())
        };

        g2.fillPolygon(xPos, yPos, 5);
    }

    public void build(@NotNull Block.Type type) {
        this.type = type;
        this.health = type.getHealth();
    }

    /*
    public void renderWalls(Graphics2D g2) {
        Map map = getParent().getLevel().getMap();
        boolean t = map.isWalkable(position.getXi(), position.getYi() - 1);
        boolean b = map.isWalkable(position.getXi(), position.getYi() + 1);
        boolean l = map.isWalkable(position.getXi() - 1, position.getYi());
        boolean r = map.isWalkable(position.getXi() + 1, position.getYi());

        GVector2f size = SIZE.mul(parent.getZoom());
        GVector2f pos = position.mul(size).sub(parent.getOffset());

        int offset = (int) (Config.WALL_OFFSET * parent.getZoom());

        GVector2f p0 = pos.sub(offset);
        GVector2f p1 = pos.add(size).sub(new GVector2f(-offset, offset + size.getY()));
        GVector2f p2 = pos.add(size).add(offset);
        GVector2f p3 = pos.add(size).sub(new GVector2f(offset + size.getX(), -offset));

        FinalTypedBlock block0 = map.getBlock(position.getXi() - 1, position.getYi() - 1);
        FinalTypedBlock block1 = map.getBlock(position.getXi() + 1, position.getYi() - 1);
        FinalTypedBlock block2 = map.getBlock(position.getXi() + 1, position.getYi() + 1);
        FinalTypedBlock block3 = map.getBlock(position.getXi() - 1, position.getYi() + 1);
        boolean val0 = block0 != null && !block0.isWalkable();
        boolean val1 = block1 != null && !block1.isWalkable();
        boolean val2 = block2 != null && !block2.isWalkable();
        boolean val3 = block3 != null && !block3.isWalkable();


        boolean walls3D = true;
        if (walls3D) {
            g2.setColor(Config.WALL_DARKER_COLOR);
            if (t) {
                g2.fillPolygon(new int[]{pos.getXi(),
                                         pos.getXi() + size.getXi(),
                                         p1.getXi() + (val1 ? -offset : 0),
                                         p0.getXi() + (true ? +offset * 2 : 0)},
                               new int[]{pos.getYi(), pos.getYi(), p1.getYi(), p0.getYi()},
                               4);
            }
            g2.setColor(Config.WALL_LIGHTER_COLOR);
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
            g2.setColor(Config.WALL_LIGHTER_COLOR);
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

            g2.setColor(Config.WALL_DARKER_COLOR);
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


    @Contract(pure = true)
    @NotNull
    public GVector2f getSur() {return position/*.div(SIZE).toInt()*/;}

    @Contract(pure = true)
    @NotNull
    public GVector2f getPosition() {return position.mul(SIZE);}

    @Contract(pure = true)
    @NotNull
    public Block.Type getType() {return type;}

    @Contract(pure = true)
    public int getHealth() {return health;}

    @Contract(pure = true)
    public boolean isWalkable() {return type.isWalkable();}

    @Contract(pure = true)
    @Nullable
    public GVector2f getInterSect(@NotNull GVector2f ss, @NotNull GVector2f se) {
        ArrayList<GVector2f> res = new ArrayList<>();

        GVector2f p = position.mul(FinalTypedBlock.SIZE);
        res.add(LineLineIntersect.linesIntersect(ss, se, p.add(new GVector2f(FinalTypedBlock.SIZE.getX(), 0)), p));
        res.add(LineLineIntersect.linesIntersect(ss, se, p.add(new GVector2f(0, FinalTypedBlock.SIZE.getY())), p));
        res.add(LineLineIntersect.linesIntersect(ss,
                                                 se,
                                                 p.add(new GVector2f(FinalTypedBlock.SIZE.getX(), 0)),
                                                 p.add(FinalTypedBlock.SIZE)));
        res.add(LineLineIntersect.linesIntersect(ss,
                                                 se,
                                                 p.add(new GVector2f(0, FinalTypedBlock.SIZE.getY())),
                                                 p.add(FinalTypedBlock.SIZE)));

        res = res.stream().filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
        if (res.size() == 0) {
            return null;
        }

        return res.stream().reduce((a, b) -> a.dist(ss) < b.dist(ss) ? a : b).get();
    }
}
