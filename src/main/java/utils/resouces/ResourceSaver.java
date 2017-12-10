package utils.resouces;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

final class ResourceSaver {
    public static void saveImage(BufferedImage image, String fileName) { saveImage(image, fileName, "png");}

    private static void saveImage(BufferedImage image, String fileName, String format) {
        try {

            File outputFile = new File(fileName + "." + format);
            ImageIO.write(image, format, outputFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
