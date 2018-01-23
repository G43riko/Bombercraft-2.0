package Bombercraft2.playGround.Misc;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Visible;
import utils.PerlinNoise;
import utils.math.GVector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class SimpleChunk implements Visible {
    public final static GVector2f SIZE = Config.BLOCK_SIZE.mul(Config.CHUNK_SIZE);
    private final SimpleChunkedMap parent;
    private final GVector2f        position;
    private BufferedImage                     image  = null;
    private HashMap<String, SimpleTypedBlock> blocks = null;

    public SimpleChunk(SimpleChunkedMap parent, GVector2f position) {
        this.position = position;
        this.parent = parent;
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
                                                    parent.getParent(),
                                                    getPosition()));
            }
        }
    }

    private void addBlock(int i, int j, SimpleTypedBlock block) {
        blocks.put(i + "_" + j, block);
    }

    public void render(Graphics2D g2) {
        blocks.entrySet()
              .stream()
              .map(Map.Entry::getValue)
              .filter(parent.getParent()::isVisible)
              .forEach(a -> a.render(g2));

        if (Config.SHOW_CHUNK_BORDERS) {
            g2.setColor(Color.black);
            final GVector2f realPosition = getPosition().mul(parent.getParent().getZoom()).sub(parent.getParent().getOffset());
            final GVector2f realSize = SIZE.mul(parent.getParent().getZoom());
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(realPosition.getXi(), realPosition.getYi(), realSize.getXi(), realSize.getYi());
        }
    }

    @Override
    public GVector2f getPosition() {
        return position.mul(SIZE);
    }

    @Override
    public GVector2f getSize() {
        return SIZE;
    }
}
