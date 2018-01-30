package Bombercraft2.playGround.Misc;

import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PostFxManager {
    @NotNull
    private final BufferedImage image;
    @NotNull
    private final Graphics2D    g2;

    public PostFxManager(@NotNull GVector2f parentSize) {
        this.image = new BufferedImage(parentSize.getXi(),
                                       parentSize.getYi(),
                                       BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) image.getGraphics();
    }

    public void addImage(@NotNull Image image, @NotNull GVector2f position, @NotNull GVector2f size) {
        g2.drawImage(image, position.getXi(), position.getYi(), size.getXi(), size.getYi(), null);
    }

    public void addArc(@NotNull Color color, @NotNull GVector2f position, @NotNull GVector2f size) {
        g2.setColor(color);
        g2.fillArc(position.getXi(), position.getYi(), size.getXi(), size.getYi(), 0, 360);
    }

    public void addRect(@NotNull Color color, @NotNull GVector2f position, @NotNull GVector2f size) {
        g2.setColor(color);
        g2.fillRect(position.getXi(), position.getYi(), size.getXi(), size.getYi());
    }

    @NotNull
    public BufferedImage getImage() {
        return image;
    }
}
