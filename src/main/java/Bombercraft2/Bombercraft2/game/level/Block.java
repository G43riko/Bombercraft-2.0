package Bombercraft2.Bombercraft2.game.level;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.Iconable;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import utils.GLogger;
import utils.ImageUtils;
import utils.SpriteViewer;
import utils.math.GVector2f;
import utils.math.LineLineIntersect;
import utils.resouces.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Block extends Entity<GameAble> {
    public enum Type implements Iconable {
        NOTHING("block_floor", 0, true),
        WOOD("block_wood", 1, false),
        IRON("block_iron", 10, false),
        GRASS("block_grass", 0, true),
        WATER("block_water", 0, true),
        PATH	("block_path",   0, true),
        STONE	("block_stone",  7, false),
        FUTURE("block_future", 0, true);

        private final Image   image;
        private final Color   miniMapColor;
        private final int     health;
        private final boolean walkable;

        Type(String imageName, int health, boolean walkable) {
            this.health = health;
            this.walkable = walkable;
            image = ResourceLoader.loadTexture(imageName + Config.EXTENSION_IMAGE);
            final BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
                                                                  image.getHeight(null),
                                                                  BufferedImage.TYPE_INT_ARGB);

            final Graphics2D bGr = bufferedImage.createGraphics();
            bGr.drawImage(image, 0, 0, null);
            bGr.dispose();

            miniMapColor = ImageUtils.getAverageColor(bufferedImage,
                                                      0,
                                                      0,
                                                      image.getWidth(null),
                                                      image.getHeight(null));
        }

        boolean isWalkable() {return walkable;}

        int getHealth() {return health;}

        @NotNull
        public Image getImage() {return image;}

        Color getMiniMapColor() {return miniMapColor;}
    }

    //	private static HashMap<String, Type> blocks = new HashMap<String, Type>();
    private Type type;
    private int  health;


    public Block(@NotNull JSONObject object, @NotNull GameAble parent) {
        super(new GVector2f(), parent);

        try {
            position = new GVector2f(object.getString(Texts.POSITION));
            health = object.getInt(Texts.HEALTH);
            type = Type.valueOf(object.getString(Texts.TYPE));
        }
        catch (JSONException e) {
            GLogger.error(GLogger.GError.CANNOT_PARSE_BLOCK, e);
        }
    }

    public Block(@NotNull GVector2f position, int type, @NotNull GameAble parent) {
        this(position, getTypeFromInt(type), parent);
    }

    private Block(@NotNull GVector2f position, Type type, @NotNull GameAble parent) {
        super(position, parent);
        this.health = type.getHealth();
        this.type = type;
    }


    @Override
    public void render(@NotNull Graphics2D g2) {
//		if(type == NOTHING)
//			return;
        GVector2f size = Config.BLOCK_SIZE.mul(getParent().getZoom());
        GVector2f pos = position.mul(size).sub(getParent().getOffset());

        g2.drawImage(type.getImage(), pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), null);
    }

    @NotNull
    @Override
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

    public void remove() {remove(true);}

    private void remove(boolean addExplosion) {
        if (addExplosion) {
            getParent().addExplosion(getPosition().add(Config.BLOCK_SIZE_HALF),
                                     Config.BLOCK_SIZE,
                                     type.getMiniMapColor(),
                                     5, false, false);
        }
        type = Type.NOTHING;
        health = 0;
    }

    public void drawSprites(Graphics2D g2) {
        Block t = getParent().getLevel().getMap().getBlock(position.getXi(), position.getYi() - 1);
        Block b = getParent().getLevel().getMap().getBlock(position.getXi(), position.getYi() + 1);
        Block r = getParent().getLevel().getMap().getBlock(position.getXi() + 1, position.getYi());
        Block l = getParent().getLevel().getMap().getBlock(position.getXi() - 1, position.getYi());

        GVector2f size = Config.BLOCK_SIZE.mul(getParent().getZoom());
        GVector2f pos = position.mul(size).sub(getParent().getOffset());

        if (t != null && t.getType() != type) {
            g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 8, 4),
                         pos.getXi(),
                         pos.getYi() - Config.BLOCK_SIZE.getYi(),
                         size.getXi(),
                         size.getYi(),
                         null);
        }

        if (b != null && b.getType() != type) {
            g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 2, 4),
                         pos.getXi(),
                         pos.getYi() + Config.BLOCK_SIZE.getYi(),
                         size.getXi(),
                         size.getYi(),
                         null);
        }

        if (r != null && r.getType() != type) {
            g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 1, 4),
                         pos.getXi() + Config.BLOCK_SIZE.getXi(),
                         pos.getYi(),
                         size.getXi(),
                         size.getYi(),
                         null);
        }

        if (l != null && l.getType() != type) {
            g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 4, 4),
                         pos.getXi() - Config.BLOCK_SIZE.getXi(),
                         pos.getYi(),
                         size.getXi(),
                         size.getYi(),
                         null);
        }
    }

    public void drawShadow(Graphics2D g2, Color color, int length, int angle) {
        if (type == Type.NOTHING) { return; }

        double finalAngle = Math.toRadians(angle + 90);
        GVector2f offset = new GVector2f(-Math.cos(finalAngle), Math.sin(finalAngle)).mul(length);
        GVector2f pos = position.mul(Config.BLOCK_SIZE).sub(getParent().getOffset());
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
                (int) (pos.add(offset).getX() + Config.BLOCK_SIZE.getX()),
                (int) (pos.add(offset).getX() + Config.BLOCK_SIZE.getX()),
                (int) (pos.getXi() + Config.BLOCK_SIZE.getX())
        };

        int[] yPos = new int[]{
                (int) (pos.getY()),
                (int) (pos.sub(offset).getY()),
                (int) (pos.sub(offset).getY()),
                (int) (pos.getY() + Config.BLOCK_SIZE.getY() - offset.getY()),
                (int) (pos.getY() + Config.BLOCK_SIZE.getY())
        };

        g2.fillPolygon(xPos, yPos, 5);
    }

    public void build(Type type) {
        this.type = type;
        this.health = type.getHealth();
    }

    public void renderWalls(Graphics2D g2) {
        Map map = getParent().getLevel().getMap();
        boolean t = map.isWalkable(position.getXi(), position.getYi() - 1);
        boolean b = map.isWalkable(position.getXi(), position.getYi() + 1);
        boolean l = map.isWalkable(position.getXi() - 1, position.getYi());
        boolean r = map.isWalkable(position.getXi() + 1, position.getYi());

        GVector2f size = Config.BLOCK_SIZE.mul(getParent().getZoom());
        GVector2f pos = position.mul(size).sub(getParent().getOffset());

        int offset = (int) (Config.WALL_OFFSET * getParent().getZoom());

        GVector2f p0 = pos.sub(offset);
        GVector2f p1 = pos.add(size).sub(new GVector2f(-offset, offset + size.getY()));
        GVector2f p2 = pos.add(size).add(offset);
        GVector2f p3 = pos.add(size).sub(new GVector2f(offset + size.getX(), -offset));

        Block block0 = map.getBlock(position.getXi() - 1, position.getYi() - 1);
        Block block1 = map.getBlock(position.getXi() + 1, position.getYi() - 1);
        Block block2 = map.getBlock(position.getXi() + 1, position.getYi() + 1);
        Block block3 = map.getBlock(position.getXi() - 1, position.getYi() + 1);
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



    public GVector2f getSur() {return position/*.div(SIZE).toInt()*/;}

    @Contract(pure = true)
    @NotNull
    public GVector2f getPosition() {return position.mul(Config.BLOCK_SIZE);}

    public Type getType() {return type;}

    public int getHealth() {return health;}

    public boolean isWalkable() {return type.isWalkable();}

    public static Type getTypeFromInt(int num) {
        return num > 0 && num < Type.values().length ? Type.values()[num] : Type.NOTHING;
    }

    public GVector2f getInterSect(GVector2f ss, GVector2f se) {
        ArrayList<GVector2f> res = new ArrayList<>();

        GVector2f p = position.mul(Config.BLOCK_SIZE);
        res.add(LineLineIntersect.linesIntersect(ss, se, p.add(new GVector2f(Config.BLOCK_SIZE.getX(), 0)), p));
        res.add(LineLineIntersect.linesIntersect(ss, se, p.add(new GVector2f(0, Config.BLOCK_SIZE.getY())), p));
        res.add(LineLineIntersect.linesIntersect(ss,
                                                 se,
                                                 p.add(new GVector2f(Config.BLOCK_SIZE.getX(), 0)),
                                                 p.add(Config.BLOCK_SIZE)));
        res.add(LineLineIntersect.linesIntersect(ss,
                                                 se,
                                                 p.add(new GVector2f(0, Config.BLOCK_SIZE.getY())),
                                                 p.add(Config.BLOCK_SIZE)));

        res = res.stream().filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
        if (res.size() == 0) {
            return null;
        }

        return res.stream().reduce((a, b) -> a.dist(ss) < b.dist(ss) ? a : b).get();
    }
}

	
