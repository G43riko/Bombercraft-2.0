package Bombercraft2.playGround.Misc.map;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import utils.PerlinNoise;
import utils.math.GVector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class SimpleChunk extends Entity<SimpleGameAble> {
    public final static GVector2f                         SIZE           = Config.BLOCK_SIZE.mul(Config.CHUNK_SIZE);
    private             BufferedImage                     image          = null;
    private             int                               renderedBlocks = 0;
    private             HashMap<String, SimpleTypedBlock> blocks         = null;

    public SimpleChunk(SimpleChunkedMap parent, GVector2f position) {
        super(position, parent.getParent());
        createRandomMap();
    }

    private void createRandomMap() {
        blocks = new HashMap<>();
        float[][] data = PerlinNoise.GeneratePerlinNoise(PerlinNoise.generateWhiteNoise(Config.CHUNK_SIZE.getXi(),
                                                                                        Config.CHUNK_SIZE.getXi()),
                                                         6,
                                                         0.7f,
                                                         true);

        for (int i = 0; i < Config.CHUNK_SIZE.getXi(); i++) {
            for (int j = 0; j < Config.CHUNK_SIZE.getYi(); j++) {
                addBlock(i, j, new SimpleTypedBlock(new GVector2f(i, j),
                                                    (int) (Math.min(Math.max(data[i][j] * 10, 0), 10)),
                                                    parent,
                                                    getPosition()));
            }
        }
    }

    private void addBlock(int i, int j, SimpleTypedBlock block) {
        blocks.put(i + "_" + j, block);
    }

    public SimpleTypedBlock getBlock(GVector2f pos) {
        return getBlock(pos.getXi(), pos.getYi());
    }

    public SimpleTypedBlock getBlock(int i, int j) {
        return blocks.get(i + "_" + j);
    }

    public void render(@NotNull Graphics2D g2) {
        renderedBlocks = 0;
        blocks.entrySet()
              .stream()
              .map(Map.Entry::getValue)
              .filter(parent::isVisible)
              .forEach(a -> {
                  a.render(g2);
                  renderedBlocks++;
              });

        if (Config.SHOW_CHUNK_BORDERS) {
            g2.setColor(Color.black);

            final GVector2f transformedPosition = parent.getManager().getViewManager().transform(getPosition());
            final GVector2f realSize = SIZE.mul(parent.getManager().getViewManager().getZoom());
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(transformedPosition.getXi(), transformedPosition.getYi(), realSize.getXi(), realSize.getYi());
        }
    }

    public int getRenderedBlocks() {
        return renderedBlocks;
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getPosition() {
        return position.mul(SIZE);
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public JSONObject toJSON() {
        return null;
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getSize() {
        return SIZE;
    }
}
