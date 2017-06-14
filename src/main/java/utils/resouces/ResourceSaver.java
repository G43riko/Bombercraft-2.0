package utils.resouces;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class ResourceSaver {
	public static void saveImage(BufferedImage image, String fileName){ saveImage(image, fileName, "png");}
	public static void saveImage(BufferedImage image, String fileName, String format){
		try {
			
		    File outputfile = new File(fileName + "." + format);
		    ImageIO.write(image, format, outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
