package utils;

import java.awt.Graphics2D;
import java.awt.Image;

import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;
import utils.resouces.ResourceLoader;

public class SpriteAnimation {
    private final int       delay;
    private       int       actDelay;
    private final GVector2f imageSize;
    private final GVector2f images;
    private final int       numberOfImages;
    private final Image     image;
    private       int       frame;

    public SpriteAnimation(@NotNull String name, int numX, int numY, int delay) {
        this.delay = delay;
        actDelay = 0;
        image = ResourceLoader.loadTexture(name);
        images = new GVector2f(numX, numY);
        imageSize = new GVector2f(image.getWidth(null), image.getHeight(null)).div(images);
        frame = 0;
        numberOfImages = numX * numY;
    }

    public boolean renderAndCheckLastFrame(@NotNull Graphics2D g2, @NotNull GVector2f pos) {
        return renderAndCheckLastFrame(g2, pos, imageSize);
    }

    public boolean renderAndCheckLastFrame(@NotNull Graphics2D g2, @NotNull GVector2f pos, @NotNull GVector2f size) {
        GVector2f point = new GVector2f(frame % images.getXi(),
                                        frame / images.getXi());
        g2.drawImage(image,
                     pos.getXi(),
                     pos.getYi(),
                     size.getXi() + pos.getXi(),
                     size.getYi() + pos.getYi(),
                     point.getXi() * imageSize.getXi(),
                     point.getYi() * imageSize.getYi(),
                     (point.getXi() + 1) * imageSize.getXi(),
                     (point.getYi() + 1) * imageSize.getYi(),
                     null);

        return checkTiming();
    }


    private boolean checkTiming() {
        if (actDelay == 0) {
            actDelay = delay;

            frame++;
            if (frame == numberOfImages) {
                frame = 0;
                return true;
            }

        }
        else { actDelay--; }

        return false;
    }
}
