package org.bombercraft2.game.level;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.game.Iconable;
import org.jetbrains.annotations.NotNull;
import org.utils.ImageUtils;
import org.utils.resources.ResourceUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public enum BlockType implements Iconable {
    NOTHING("block_floor", 0, true),
    WOOD("block_wood", 1, false),
    IRON("block_iron", 10, false),
    GRASS("block_grass", 0, true),
    WATER("block_water", 0, true),
    PATH("block_path", 0, true),
    STONE("block_stone", 7, false),
    FUTURE("block_future", 0, true);

    private final Image   image;
    private final Color   miniMapColor;
    private final int     health;
    private final boolean walkable;

    BlockType(String imageName, int health, boolean walkable) {
        this.health = health;
        this.walkable = walkable;
        image = ResourceUtils.getBufferedImage(StaticConfig.IMAGES_PATH + imageName + StaticConfig.EXTENSION_IMAGE);
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

    public boolean isWalkable() {return walkable;}

    int getHealth() {return health;}

    @NotNull
    public Image getImage() {return image;}

    Color getMiniMapColor() {return miniMapColor;}
}