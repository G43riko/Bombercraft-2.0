package Bombercraft2.playGround.Misc;

import utils.math.GVector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PostFxManager {
    private final BufferedImage image;
    private final Graphics2D    g2;

    public PostFxManager(GVector2f parentSize) {
        this.image = new BufferedImage(parentSize.getXi(),
                                       parentSize.getYi(),
                                       BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) image.getGraphics();
    }

    public void addImage(Image image, GVector2f position, GVector2f size) {
        g2.drawImage(image, position.getXi(), position.getYi(), size.getXi(), size.getYi(), null);
    }
    public void addArc(Color color, GVector2f position, GVector2f size) {
        g2.setColor(color);
        g2.fillArc(position.getXi(), position.getYi(), size.getXi(), size.getYi(), 0, 360);
    }
    public void addRect(Color color, GVector2f position, GVector2f size) {
        g2.setColor(color);
        g2.fillRect(position.getXi(), position.getYi(), size.getXi(), size.getYi());
    }
    public BufferedImage getImage() {
        return image;
    }
}
