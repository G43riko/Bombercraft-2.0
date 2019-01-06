package org.bombercraft2.game.player;

import org.bombercraft2.game.misc.Direction;
import org.glib2.math.vectors.GVector2f;
import utils.resouces.ResourceLoader;

import java.awt.*;
import java.util.HashMap;

public class PlayerSprite {
    private static final HashMap<String, PlayerSprite> animations = new HashMap<>();

    private final Image     image;
    private final int       numberOfImages;
    private final int       numberOfSteps;
    private final int       delay;
    private final GVector2f imageSize;
    private       int       step = 0;
    private       int       actDelay;

    private PlayerSprite(String name, int numX, int numY, int positions, int delay) {
        this.delay = delay;
        image = ResourceLoader.loadTexture(name);
        numberOfImages = numX * numY;
        actDelay = delay;
        numberOfSteps = numberOfImages / positions;
        imageSize = new GVector2f(image.getWidth(null) / numX, image.getHeight(null) / numY);
    }

    /**
     * @deprecated use {@link #setSprite(String, String, int, int, int, int)}
     */
    public static void setSprite(String name, int numX, int numY, int positions, int delay) {
        animations.put(name, new PlayerSprite(name, numX, numY, positions, delay));
    }

    /**
     * @param key       - unique key for sprite
     * @param name      - image used for sprite
     * @param numX      - number of horizontal images
     * @param numY      - number of vertical images
     * @param positions - number of scale types
     * @param delay     - number of draw cycles needed to on step
     */
    public static void setSprite(String key, String name, int numX, int numY, int positions, int delay) {
        animations.put(key, new PlayerSprite(name, numX, numY, positions, delay));
    }


    public static void drawPlayer(GVector2f position,
                                  GVector2f size,
                                  Graphics2D g2,
                                  Direction type,
                                  String name,
                                  boolean isRunning
                                 ) {
        PlayerSprite sprite = animations.get(name);

        if (sprite == null) {
            return;
        }

        GVector2f position2 = position.getAdd(size);
        int sourceX = sprite.step * sprite.imageSize.getXi();
        int sourceY = type.getID() * sprite.imageSize.getXi();

        //nastavuje to aby ked sa panacik nehybe tak aby sa stale renderoval obrazok kde sa nehybe
        if (!isRunning) {
            sourceX = 0;
            if (type == Direction.RIGHT) {
                sourceX = (sprite.numberOfSteps - 2) * sprite.imageSize.getXi();
            }
        }

        g2.drawImage(sprite.image,
                     position.getXi(), position.getYi(),
                     position2.getXi(), position2.getYi(),
                     sourceX, sourceY,
                     sourceX + sprite.imageSize.getXi(), sourceY + sprite.imageSize.getYi(),
                     null);

        checkTiming(sprite);
    }

    private static void checkTiming(PlayerSprite sprite) {
        if (sprite.actDelay == 0) {
            sprite.actDelay = sprite.delay;

            sprite.step++;
            if (sprite.step + 1 == sprite.numberOfSteps) {
                sprite.step = 0;
            }
        }
        else {
            sprite.actDelay--;
        }
    }

}
