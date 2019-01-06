package org.play_ground.misc.map;

import org.bombercraft2.StaticConfig;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.play_ground.misc.SimpleGameAble;
import org.utils.noises.PerlinNoise;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class SimpleMap extends AbstractMap<SimpleTypedBlock> {
    private final static boolean       PRERENDER = false;
    private              BufferedImage image     = null;

    public SimpleMap(SimpleGameAble parent, GVector2f numberOfBlocks) {
        super(parent, numberOfBlocks, numberOfBlocks.getMul(StaticConfig.BLOCK_SIZE));
        createRandomMap();
    }

    protected void createRandomMap() {
        float[][] data = PerlinNoise.generatePerlinNoise(PerlinNoise.generateWhiteNoise(numberOfItems.getXi(),
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

    @Nullable
    @Override
    public SimpleTypedBlock getBlockOnAbsolutePos(GVector2f click) {
        return getItem(click.getAdd(parent.getManager().getViewManager().getOffset())
                               .getDiv(StaticConfig.BLOCK_SIZE)
                               .getDiv(parent.getManager().getViewManager().getZoom()));
    }

    @Override
    public AbstractBlock getBlockOnPos(GVector2f click) {
        return getItem(click);
    }

    public void render(@NotNull Graphics2D g2) {
        if (!PRERENDER) {
            renderToImage(g2);
            return;
        }
        if (image == null) {
            image = new BufferedImage(numberOfItems.getXi() * StaticConfig.DEFAULT_BLOCK_WIDTH,
                                      numberOfItems.getYi() * StaticConfig.DEFAULT_BLOCK_HEIGHT,
                                      BufferedImage.TYPE_INT_ARGB);

            renderToImage((Graphics2D) image.getGraphics());
        }
        g2.drawImage(image,
                     0,
                     0,
                     parent.getCanvasSize().getXi(),
                     parent.getCanvasSize().getYi(),
                     parent.getManager().getViewManager().getOffset().getXi(),
                     parent.getManager().getViewManager().getOffset().getYi(),
                     parent.getManager().getViewManager().getOffset().getXi() + parent.getCanvasSize().getXi(),
                     parent.getManager().getViewManager().getOffset().getYi() + parent.getCanvasSize().getYi(),
                     null);


    }

    private void renderToImage(Graphics2D g2) {
        new HashMap<>(items).entrySet()
                            .stream()
                            .map(java.util.Map.Entry::getValue)
                            .filter(parent::isVisible)//dame prec nevyditelne
                            .forEach(a -> a.render(g2));

    }

    public HashMap<String, Integer> getHashMap() {
        HashMap<String, Integer> h = new HashMap<>();
        items.forEach((key, value) -> h.put(value.getPosition().getDiv(StaticConfig.BLOCK_SIZE).toString(),
                                            value.getType().isWalkable() ? 0 : 1));
        return h;
    }
}
