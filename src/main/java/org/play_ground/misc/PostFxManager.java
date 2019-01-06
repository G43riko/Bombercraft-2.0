package org.play_ground.misc;

import org.bombercraft2.core.Visible;
import org.bombercraft2.game.misc.GCanvas;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PostFxManager extends AbstractManager {
    @NotNull
    private final BufferedImage  image;
    @NotNull
    private final Graphics2D     g2;
    @NotNull
    private final SimpleGameAble parent;
    private final List<Visible>  items = new ArrayList<>();

    public PostFxManager(@NotNull SimpleGameAble parent, @NotNull GVector2f parentSize) {
        this.image = new BufferedImage(parentSize.getXi(),
                                       parentSize.getYi(),
                                       BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) image.getGraphics();
        this.parent = parent;
    }

    public void addImage(@NotNull Image image, @NotNull GVector2f position, @NotNull GVector2f size) {
        items.add(new Visible() {
            @Override
            public @NotNull GVector2f getPosition() {
                return position;
            }

            @Override
            public @NotNull GVector2f getSize() {
                return size;
            }
        });
        GCanvas.drawImage(g2, image, position, size);
        // g2.drawImage(image, scale.getXi(), scale.getYi(), size.getXi(), size.getYi(), null);
    }

    public void addArc(@NotNull Color color, @NotNull GVector2f position, @NotNull GVector2f size) {
        g2.setColor(color);
        g2.fillArc(position.getXi(), position.getYi(), size.getXi(), size.getYi(), 0, 360);
    }

    public void addRect(@NotNull Color color, @NotNull GVector2f position, @NotNull GVector2f size) {
        GCanvas.fillRect(g2, position, size, color);
        // g2.setColor(color);
        // g2.fillRect(scale.getXi(), scale.getYi(), size.getXi(), size.getYi());
    }

    @NotNull
    public BufferedImage getImage() {
        return image;
    }

    public void render(Graphics2D g2) {
        if (items.isEmpty()) {
            return;
        }
        if (items.stream().noneMatch(parent::isVisible)) {
            return;
        }
        GVector2f canvasSize = parent.getManager().getViewManager().getCanvasSize();
        float zoom = parent.getManager().getViewManager().getZoom();
        GVector2f offset = parent.getManager().getViewManager().getOffset().getDiv(zoom);
        g2.drawImage(image,
                     0,
                     0,
                     canvasSize.getXi(),
                     canvasSize.getYi(),
                     offset.getXi(),
                     offset.getYi(),
                     offset.getXi() + (int) (canvasSize.getXi() / zoom),
                     offset.getYi() + (int) (canvasSize.getYi() / zoom),
                     null);
    }
}
