package Bombercraft2.Bombercraft2.game.player;

import Bombercraft2.Bombercraft2.game.player.Player.Direction;
import utils.math.GVector2f;
import utils.resouces.ResourceLoader;

import java.awt.*;
import java.util.HashMap;

class PlayerSprite {
    private static final HashMap<String, PlayerSprite> animations = new HashMap<>();

    private final Image image;
    private final int   numberOfImages;
    private final int   numberOfSteps;
    private int       step           = 0;
    private final int       delay;
    private       int       actDelay;
    private final GVector2f imageSize;

    private PlayerSprite(String name, int numX, int numY, int positions, int delay) {
        this.delay = delay;
        image = ResourceLoader.loadTexture(name);
        numberOfImages = numX * numY;
        actDelay = delay;
        numberOfSteps = numberOfImages / positions;
        imageSize = new GVector2f(image.getWidth(null) / numX, image.getHeight(null) / numY);
    }

    public static void setSprite(String name, int numX, int numY, int positions, int delay) {
        animations.put(name, new PlayerSprite(name, numX, numY, positions, delay));
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

        GVector2f position2 = position.add(size);
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
