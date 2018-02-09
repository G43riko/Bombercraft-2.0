package utils;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class ImageUtils {
    @NotNull
    public static Color getAverageColor(@NotNull BufferedImage bi, int x0, int y0, int w, int h) {
        final int x1 = x0 + w;
        final int y1 = y0 + h;
        int sumRed = 0,
                sumGreen = 0,
                sumBlue = 0;
        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {
                Color pixel = new Color(bi.getRGB(x, y));
                sumRed += pixel.getRed();
                sumGreen += pixel.getGreen();
                sumBlue += pixel.getBlue();
            }
        }
        final int num = w * h;
        return new Color(sumRed / num, sumGreen / num, sumBlue / num);
    }

    @NotNull
    public static Canvas getCanvasFromImage(@NotNull BufferedImage image) {
        Canvas canvas = new Canvas();
        canvas.setSize(image.getWidth(), image.getHeight());
        Graphics2D g2 = (Graphics2D) canvas.getGraphics();
        g2.drawImage(image, 0, 0, null);
        return canvas;
    }

    @NotNull
    public static BufferedImage getImageFromCanvas(@NotNull Canvas canvas) {
        BufferedImage image = new BufferedImage(canvas.getWidth(),
                                                canvas.getHeight(),
                                                BufferedImage.TYPE_INT_ARGB);


        Graphics2D g2Canvas = (Graphics2D) canvas.getGraphics();
        Graphics2D g2Image = (Graphics2D) image.getGraphics();
        g2Image.setPaint(g2Canvas.getPaint());
        g2Image.dispose();
        return image;
    }

    @NotNull
    public static BufferedImage getImageFromGraphics(@NotNull Graphics2D g2Canvas, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2Image = (Graphics2D) image.getGraphics();
        g2Image.setPaint(g2Canvas.getPaint());
        g2Image.dispose();
        return image;
    }

    @NotNull
    public static BufferedImage resize(@NotNull BufferedImage tex, int width, int height) {
        int type = tex.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : tex.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                           RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                           RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(tex, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
//	image = new BufferedImage(numberOfBlocks.getXi() * Config.DEFAULT_BLOCK_WIDTH, 
//			  numberOfBlocks.getYi() * Config.DEFAULT_BLOCK_HEIGHT, 
//			  BufferedImage.TYPE_INT_ARGB);
//
//renderToImage((Graphics2D) image.getGraphics());
}
