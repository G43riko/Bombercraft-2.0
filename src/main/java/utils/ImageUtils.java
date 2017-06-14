package utils;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public final class ImageUtils {
	public static Color getAverageColor(BufferedImage bi, int x0, int y0, int w, int h) {
		final int x1 = x0 + w;
		final int y1 = y0 + h;
	    int sumr = 0, 
	    	sumg = 0, 
	    	sumb = 0;
	    for (int x = x0; x < x1; x++) {
	        for (int y = y0; y < y1; y++) {
	            Color pixel = new Color(bi.getRGB(x, y));
	            sumr += pixel.getRed();
	            sumg += pixel.getGreen();
	            sumb += pixel.getBlue();
	        }
	    }
	    final int num = w * h;
	    return new Color(sumr / num, sumg / num, sumb / num);
	}
	public static Canvas getCanvasFromImage(BufferedImage image){
		Canvas canvas = new Canvas();
		canvas.setSize(image.getWidth(), image.getHeight());
		Graphics2D g2 = (Graphics2D)canvas.getGraphics();
		g2.drawImage(image, 0, 0, null);
		return canvas;
	}
	public static BufferedImage getImageFromCanvas(Canvas canvas){
		BufferedImage image = new BufferedImage(canvas.getWidth(), 
												canvas.getHeight(), 
												BufferedImage.TYPE_INT_ARGB);

		
		Graphics2D g2Canvas = (Graphics2D)canvas.getGraphics();
		Graphics2D g2Image = (Graphics2D)image.getGraphics();
		g2Image.setPaint(g2Canvas.getPaint());
		g2Image.dispose();
		return image;
	}
	
	
	public static BufferedImage getImageFromGrahics(Graphics2D g2Canvas, int width, int height){
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2Image = (Graphics2D)image.getGraphics();
		g2Image.setPaint(g2Canvas.getPaint());
		g2Image.dispose();
		return image;
	}
//	image = new BufferedImage(numberOfBlocks.getXi() * Config.DEFAULT_BLOCK_WIDTH, 
//			  numberOfBlocks.getYi() * Config.DEFAULT_BLOCK_HEIGHT, 
//			  BufferedImage.TYPE_INT_ARGB);
//
//renderToImage((Graphics2D) image.getGraphics());
}
