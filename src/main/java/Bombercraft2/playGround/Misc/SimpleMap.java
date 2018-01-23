package Bombercraft2.playGround.Misc;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import Bombercraft2.Bombercraft2.Config;
import utils.PerlinNoise;
import utils.math.GVector2f;

public class SimpleMap extends AbstractMap<SimpleTypedBlock> {
    private final static boolean       PRERENDER = false;
    private              BufferedImage image     = null;

    public SimpleMap(SimpleGameAble parent, GVector2f numberOfBlocks) {
        super(parent, numberOfBlocks);
        createRandomMap();
    }

    protected void createRandomMap() {
        float[][] data = PerlinNoise.GeneratePerlinNoise(PerlinNoise.generateWhiteNoise(numberOfItems.getXi(),
                                                                                        numberOfItems.getXi()),
                                                         6,
                                                         0.7f,
                                                         true);

        for (int i = 0; i < numberOfItems.getXi(); i++) {
            for (int j = 0; j < numberOfItems.getYi(); j++) {
                addItem(i, j, new SimpleTypedBlock(new GVector2f(i, j),
                                                   (int) (Math.min(Math.max(data[i][j] * 10, 0), 10)),
                                                   parent));
            }
        }
    }


    /*
    public void render(Graphics2D g2) {
        new HashMap<>(blocks).entrySet()
                             .stream()
                             .map(java.util.Map.Entry::getValue)
                             .filter(parent::isVisible)//dame prec nevyditelne
                             .peek(a -> a.render(g2))
                             .filter(a -> a.getType() != Block.Type.NOTHING)
                             .count();
    }
    */
    public void render(Graphics2D g2) {
        if (!PRERENDER) {
            renderToImage(g2);
            return;
        }
        if (image == null) {
            image = new BufferedImage(numberOfItems.getXi() * Config.DEFAULT_BLOCK_WIDTH,
                                      numberOfItems.getYi() * Config.DEFAULT_BLOCK_HEIGHT,
                                      BufferedImage.TYPE_INT_ARGB);

            renderToImage((Graphics2D) image.getGraphics());
        }
        g2.drawImage(image,
                     0,
                     0,
                     parent.getCanvasSize().getXi(),
                     parent.getCanvasSize().getYi(),
                     parent.getOffset().getXi(),
                     parent.getOffset().getYi(),
                     parent.getOffset().getXi() + parent.getCanvasSize().getXi(),
                     parent.getOffset().getYi() + parent.getCanvasSize().getYi(),
                     null);


    }

    private void renderToImage(Graphics2D g2) {
        new HashMap<>(items).entrySet()
                            .stream()
                            .map(java.util.Map.Entry::getValue)
                            .filter(parent::isVisible)//dame prec nevyditelne
                            .forEach(a -> a.render(g2));

    }
}

/*
public class SimpleMap {
    private final static boolean PRERENDER = false;
    private final SimpleGameAble parent;
    private BufferedImage                image          = null;
    private GVector2f                    numberOfBlocks = null;
    private HashMap<String, SimpleTypedBlock> blocks         = null;

    public SimpleMap(SimpleGameAble parent, GVector2f numberOfBlocks) {
        this.numberOfBlocks = numberOfBlocks;
        this.parent = parent;
        createRandomMap();
    }

    private void createRandomMap() {
        blocks = new HashMap<>();
        float[][] data = PerlinNoise.GeneratePerlinNoise(PerlinNoise.generateWhiteNoise(numberOfBlocks.getXi(),
                                                                                        numberOfBlocks.getXi()),
                                                         6,
                                                         0.7f,
                                                         true);

        for (int i = 0; i < numberOfBlocks.getXi(); i++) {
            for (int j = 0; j < numberOfBlocks.getYi(); j++) {
                addBlock(i, j, new SimpleTypedBlock(new GVector2f(i, j),
                                               (int) (Math.min(Math.max(data[i][j] * 10, 0), 10)),
                                               parent));
            }
        }
    }

    private void addBlock(int i, int j, SimpleTypedBlock block) {
        blocks.put(i + "_" + j, block);
    }

    //public void render(Graphics2D g2) {
    //    new HashMap<>(blocks).entrySet()
    //                         .stream()
    //                         .map(java.util.Map.Entry::getValue)
    //                         .filter(parent::isVisible)//dame prec nevyditelne
    //                         .peek(a -> a.render(g2))
    //                         .filter(a -> a.getType() != Block.Type.NOTHING)
    //                         .count();
    //}

    public void render(Graphics2D g2) {
        if (!PRERENDER) {
            renderToImage(g2);
            return;
        }
        if (image == null) {
            image = new BufferedImage(numberOfBlocks.getXi() * Config.DEFAULT_BLOCK_WIDTH,
                                      numberOfBlocks.getYi() * Config.DEFAULT_BLOCK_HEIGHT,
                                      BufferedImage.TYPE_INT_ARGB);

            renderToImage((Graphics2D) image.getGraphics());
        }
        g2.drawImage(image,
                     0,
                     0,
                     parent.getCanvasSize().getXi(),
                     parent.getCanvasSize().getYi(),
                     parent.getOffset().getXi(),
                     parent.getOffset().getYi(),
                     parent.getOffset().getXi() + parent.getCanvasSize().getXi(),
                     parent.getOffset().getYi() + parent.getCanvasSize().getYi(),
                     null);


    }

    private void renderToImage(Graphics2D g2) {
        new HashMap<>(blocks).entrySet()
                             .stream()
                             .map(java.util.Map.Entry::getValue)
                             .filter(parent::isVisible)//dame prec nevyditelne
                             .forEach(a -> a.render(g2));

    }
}
*/
