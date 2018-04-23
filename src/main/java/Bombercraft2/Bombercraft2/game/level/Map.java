package Bombercraft2.Bombercraft2.game.level;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.InteractAble;
import Bombercraft2.Bombercraft2.core.Render;
import Bombercraft2.Bombercraft2.core.Texts;
import Bombercraft2.Bombercraft2.game.misc.Direction;
import Bombercraft2.Bombercraft2.game.GameAble;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import utils.GLogger;
import utils.PerlinNoise;
import utils.math.GVector2f;
import utils.resouces.JSONAble;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Map implements InteractAble, JSONAble {
    private              HashMap<String, Block> blocks         = null;
    private              GVector2f              numberOfBlocks = null;
    private              boolean                render         = true;
    private              long                   renderedBlocks = 0;
    private              String                 defaultMap     = null;    //zaloha mapy pre reset
    private              BufferedImage          image          = null;
    private final static boolean                PRE_RENDER     = false;
    private              GVector2f              size           = null;
    private final GameAble parent;


    public Map(@NotNull JSONObject object, @NotNull GameAble parent) {
        this.parent = parent;
        fromJSON(object);
        size = numberOfBlocks.mul(Config.BLOCK_SIZE);
        GLogger.log(GLogger.GLog.MAP_SUCCESSFULLY_CREATED);
    }

    public Map(@NotNull GameAble parent, @NotNull GVector2f numberOfBlocks) {
        this.parent = parent;
        this.numberOfBlocks = numberOfBlocks;

        createRandomMap();
        defaultMap = toJSON().toString();
        size = numberOfBlocks.mul(Config.BLOCK_SIZE);
        GLogger.log(GLogger.GLog.MAP_SUCCESSFULLY_CREATED);
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        if (!render) {
            return;
        }
        if (!PRE_RENDER) {
            renderToImage(g2);
            return;
        }
        if (image == null) {
            image = new BufferedImage(numberOfBlocks.getXi() * Config.DEFAULT_BLOCK_WIDTH,
                                      numberOfBlocks.getYi() * Config.DEFAULT_BLOCK_HEIGHT,
                                      BufferedImage.TYPE_INT_ARGB);

            renderToImage((Graphics2D) image.getGraphics());
        }

        //g2.drawImage(image, -parent.getOffset().getXi(), -parent.getOffset().getYi(), null);

        g2.drawImage(image,
                     0,
                     0,
                     parent.getCanvas().getWidth(),
                     parent.getCanvas().getHeight(),
                     parent.getOffset().getXi(),
                     parent.getOffset().getYi(),
                     parent.getOffset().getXi() + parent.getCanvas().getWidth(),
                     parent.getOffset().getYi() + parent.getCanvas().getHeight(),
                     null);


    }

    private void renderToImage(Graphics2D g2) {
        if (!render) {
            return;
        }
        renderedBlocks = new HashMap<>(blocks).entrySet()
                                              .stream()
                                              .map(java.util.Map.Entry::getValue)
                                              .filter(getParent()::isVisible)//dame prec nevyditelne
                                              .peek(a -> a.render(g2))
                                              .filter(a -> a.getType() != BlockType.NOTHING)
                                              .count();
        if (parent.getVisibleOption(Render.MAP_WALLS)) {
            new HashMap<>(blocks).entrySet()
                                 .stream()
                                 .map(java.util.Map.Entry::getValue)
                                 .filter(getParent()::isVisible)//dame prec nevyditelne
                                 .filter(a -> !a.isWalkable())    //dame prec take ktore nevrhaju tien
                                 .forEach(a -> a.renderWalls(g2));
        }


    }

    private void postEdit() {
        //TODO odstranit osamele policka z vodou(vacsinu)

        clearRespawnZones(parent.getLevel().getRespawnZones());
    }

    @NotNull
    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        try {
            for (int i = 0; i < numberOfBlocks.getXi(); i++) {
                for (int j = 0; j < numberOfBlocks.getYi(); j++) {
                    result.put("b" + i + "_" + j, getBlock(i, j).toJSON());
                }
            }
            result.put(Texts.BLOCKS_NUMBER, numberOfBlocks);

        }
        catch (JSONException e) {
            GLogger.error(GLogger.GError.MAP_SERIALIZATION_FAILED, e);
        }
        return result;
    }

    //OTHERS

    public void createRandomMap() {
        render = false;
        blocks = new HashMap<>();
        float[][] data = PerlinNoise.GeneratePerlinNoise(PerlinNoise.generateWhiteNoise(numberOfBlocks.getXi(),
                                                                                        numberOfBlocks.getXi()),
                                                         6,
                                                         0.7f,
                                                         true);

        for (int i = 0; i < numberOfBlocks.getXi(); i++) {
            for (int j = 0; j < numberOfBlocks.getYi(); j++) {
//				addBlock(i, j, new Block(new GVector2f(i,j),(int)(Math.random() * 10), parent));
                addBlock(i, j, new Block(new GVector2f(i, j),
                                         (int) (Math.min(Math.max(data[i][j] * 10, 0), 10)),
                                         parent));
            }
        }
        postEdit();
        render = true;
    }

    public void fromJSON(@NotNull JSONObject object) {
        render = false;
        blocks = new HashMap<>();
        try {
            this.numberOfBlocks = new GVector2f(object.getString(Texts.BLOCKS_NUMBER));
            for (int i = 0; i < numberOfBlocks.getXi(); i++) {
                for (int j = 0; j < numberOfBlocks.getYi(); j++) {
                    addBlock(i, j, new Block(new JSONObject(object.getString("b" + i + "_" + j)), parent));
                }
            }
        }
        catch (JSONException e) {
            GLogger.error(GLogger.GError.MAP_CREATION_FAILED, e);
        }
        render = true;
    }

    /**
     * Odstrani bloky z respawnovacich zon
     *
     * @param zones - zoznam zón
     */
    private void clearRespawnZones(@NotNull List<GVector2f> zones) {
        zones.forEach(a -> remove(a.div(Config.BLOCK_SIZE).toInt()));
    }

    public void remove(GVector2f sur) {
        Block b = getBlock(sur.getXi(), sur.getYi());
        if (b != null && b.getType() != BlockType.NOTHING) {
            b.remove();
        }
    }

    public void resetMap() {
        try {
            fromJSON(new JSONObject(defaultMap));
        }
        catch (JSONException e) {
            GLogger.error(GLogger.GError.MAP_RESET_FAILED, e);
        }
    }

    //ADDERS

    private void addBlock(int i, int j, Block block) {
        blocks.put(i + "_" + j, block);
    }


    public long getRenderedBlocks() {return renderedBlocks;}

    public GVector2f getNumberOfBlocks() {return numberOfBlocks;}

    public Block getBlock(String block) {return blocks.get(block);}

    public Block getBlock(int i, int j) {return blocks.get(i + "_" + j);}

    @Contract(pure = true)
    private GameAble getParent() {return parent;}

    public boolean isWalkable(int i, int j) {
        Block b = getBlock(i, j);
        return b != null && b.isWalkable();
    }

    public Direction[] getPossibleWays(GVector2f sur) {
        ArrayList<Direction> result = new ArrayList<>();
        Block b;

        b = getBlock(sur.getXi(), sur.getYi() - 1);
        if (b != null && b.isWalkable()) {
            result.add(Direction.UP);
        }

        b = getBlock(sur.getXi() + 1, sur.getYi());
        if (b != null && b.isWalkable()) {
            result.add(Direction.RIGHT);
        }

        b = getBlock(sur.getXi(), sur.getYi() + 1);
        if (b != null && b.isWalkable()) {
            result.add(Direction.DOWN);
        }

        b = getBlock(sur.getXi() - 1, sur.getYi());
        if (b != null && b.isWalkable()) {
            result.add(Direction.LEFT);
        }

        Direction[] ret = new Direction[result.size()];
        for (int i = 0; i < result.size(); i++) {
            ret[i] = result.get(i);
        }

        return ret;
    }

    /**
     * @return NOTHING
     * @deprecated since 15.6.2017 - use Map.getRandomBlockByType(Block.Type.NOTHING)
     */
    public Block getRandomEmptyBlock() {
        return getRandomBlockByType(BlockType.NOTHING);
    }

    private Block getRandomBlockByType(BlockType type) {
        ArrayList<Block> b = blocks.entrySet()
                                   .stream()
                                   .map(java.util.Map.Entry::getValue)
                                   .filter(a -> a.getType() == type)
                                   .collect(Collectors.toCollection(ArrayList::new));
        return b.get((int) (Math.random() * b.size()));
    }

    @NotNull
    public static GVector2f globalPosToLocalPos(GVector2f pos) {
        return pos.sub(pos.mod(Config.BLOCK_SIZE)).div(Config.BLOCK_SIZE);
    }

    @NotNull
    public static GVector2f localPosToGlobalPos(GVector2f pos) {
        return pos.mul(Config.BLOCK_SIZE);
    }

    public Block getBlockOnPosition(GVector2f sur) {
        GVector2f blockSize = Config.BLOCK_SIZE;
        GVector2f pos = sur.sub(sur.mod(blockSize)).div(blockSize);

        return getBlock(pos.getXi(), pos.getYi());
    }

    /**
     * @return size
     * @deprecated since 15.6.2017 - vsetky operacie z blokmi by maly byt vykonane v tejto triede
     */
    public ArrayList<Block> getBlocks() {
        if (render) {
            return new ArrayList<>(blocks.values());
        }

        return new ArrayList<>();
    }

    public GVector2f getSize() {
        return size;
    }

}
