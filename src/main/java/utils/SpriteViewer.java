package utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.math.GVector2f;
import utils.resouces.ResourceLoader;

public final class SpriteViewer {
    private static final HashMap<String, SpriteViewer> loadedImages = new HashMap<>();
    @NotNull
    private final GVector2f images;
    @NotNull
    private final GVector2f imageSize;
    @NotNull
    private final Image     image;

    static {
        setImage("tileset.png", 29, 3);
        setImage("tileset2.png", 12, 10);
        setImage("tileset2-b.png", 16, 8);
        setImage("wall_sprite.png", 16, 1);
        setImage("wall_sprite2.png", 5, 1);
        setImage("tileset_test.png", 15, 1);
        setImage("tileset_test2.png", 15, 1);
    }


    private SpriteViewer(@NotNull String name, int x, int y) {
        image = ResourceLoader.loadTexture(name);
        images = new GVector2f(x, y);
        imageSize = new GVector2f(image.getWidth(null), image.getHeight(null)).div(images);
    }

    private static void setImage(@NotNull String name, int x, int y) {
        if (loadedImages.containsKey(name)) {
            return;
        }

        loadedImages.put(name, new SpriteViewer(name, x, y));
    }

    public static Image getImage(@NotNull String name, int x, int y) {
        @Nullable
        SpriteViewer viewer = loadedImages.get(name);
        if (viewer == null) {
            throw new Error("Unknown sprina name: " + name);
        }


        if (x < 0 || y < 0 || x > viewer.images.getX() || y > viewer.images.getY()) {
            throw new Error("Wrong sprita coordinates [" + x + ", " + y + "]. Max coordinates: " + viewer.images);
        }

        BufferedImage img = new BufferedImage(viewer.imageSize.getXi(),
                                              viewer.imageSize.getYi(),
                                              BufferedImage.TYPE_INT_ARGB);

        Graphics2D tg2 = (Graphics2D) img.getGraphics();

        tg2.drawImage(viewer.image,
                      0,
                      0,
                      viewer.imageSize.getXi(),
                      viewer.imageSize.getYi(),
                      x * viewer.imageSize.getXi(),
                      y * viewer.imageSize.getYi(),
                      (x + 1) * viewer.imageSize.getXi(),
                      (y + 1) * viewer.imageSize.getYi(),
                      null);

        return img;
    }
}
