package utils.resouces;

import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

final class ResourceSaver {
    public static void saveImage(@NotNull BufferedImage image, @NotNull String fileName) {
        saveImage(image, fileName, "png");
    }

    private static void saveImage(@NotNull BufferedImage image, @NotNull String fileName, @NotNull String format) {
        try {

            File outputFile = new File(fileName + "." + format);
            ImageIO.write(image, format, outputFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
