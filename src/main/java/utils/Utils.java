package utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.imageio.ImageIO;

import Bombercraft2.Bombercraft2.game.misc.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.math.GVector2f;

public final class Utils {
    //	public static  GVector2f getMoveFromDir(int dir){
//		switch(dir){
//			case 0:
//				return new GVector2f(00, -1);
//			case 1:
//				return new GVector2f(01, 00);
//			case 2:
//				return new GVector2f(00, 01);
//			case 3:
//				return new GVector2f(-1, 00);
//			default:
//				return new GVector2f();
//		}
//	}
    public static long ping(@NotNull String ip) {return ping(ip, 1000);}

    private static long ping(@NotNull String ip, int timeout) {
        try {
            long currentTime = System.currentTimeMillis();
            InetAddress.getByName(ip).isReachable(timeout);
            return System.currentTimeMillis() - currentTime;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @NotNull
    public static String getMyIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "GError";
    }

    @NotNull
    static public String getLocalIP() {
        String result = "";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface current = interfaces.nextElement();
                //System.out.println(current);
                if (!current.isUp() || current.isLoopback() || current.isVirtual()) { continue; }
                Enumeration<InetAddress> addresses = current.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress current_address = addresses.nextElement();
                    if (current_address.isLoopbackAddress()) { continue; }
                    if (current_address instanceof Inet4Address) {
                        result = result.concat(current_address.getHostAddress() + "\n");
                    }
                    //else if (current_address instanceof Inet6Address)
                    // System.out.println(current_address.getHostAddress());
                    //System.out.println(current_address.getHostAddress());
                }
            }
        }
        catch (Exception ignored) {
        }
        return result;
    }

    @NotNull
    public static String getHostName() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostName();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    @SuppressWarnings("unchecked")
    public static <T> T choose(T... argc) {
        return argc[(int) (Math.random() * argc.length)];
    }

    public static <T> T choose(List<T> list) {
        return list.get((int) (Math.random() * list.size()));
    }

    @NotNull
    public static URL File2URL(@NotNull File file) throws MalformedURLException {
        return file.toURI().toURL();
    }

    @Nullable
    private static BufferedImage getFullscreenScreenshotImage() throws AWTException {
        Robot robot = new Robot();
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        return robot.createScreenCapture(screenRect);
    }

    public static void takeFullscreenScreenshot(@NotNull String fileName) {takeFullscreenScreenshot(fileName, "jpg");}

    private static void takeFullscreenScreenshot(@NotNull String fileName, @NotNull String format) {
        try {
            BufferedImage screenFullImage = getFullscreenScreenshotImage();
            ImageIO.write(screenFullImage, format, new File(fileName));

            System.out.println("A full screenshot saved!");
        }
        catch (AWTException | IOException ex) {
            System.err.println(ex);
        }
    }

    public static List<String> getMemoryInfo() {
        Runtime runtime = Runtime.getRuntime();

        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        List<String> result = new ArrayList<>();
        final int ration = 1048576; // 1024 * 1024
        result.add("free memory: " + String.format("%03d ", freeMemory / ration) + " MB");
        result.add("allocated memory: " + String.format("%03d ", allocatedMemory / ration) + " MB");
        result.add("max memory: " + String.format("%03d ", maxMemory / ration) + " MB");
        return result;
    }

    @NotNull
    public static GVector2f getNormalMoveFromDir(@NotNull Direction dir) {
        switch (dir) {
            case LEFT:
                return new GVector2f(-1, 0);
            case RIGHT:
                return new GVector2f(1, 0);
            case UP:
                return new GVector2f(0, -1);
            case DOWN:
                return new GVector2f(0, 1);
            default:
                return new GVector2f();
        }
    }

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SafeVarargs
    public static <T> T nvl(T... options) {
        for (T option : options) {
            if (option != null) {
                return option;
            }
        }
        return null;
    }

    public static <T> T nvl(T option1, T option2) {
        return option1 == null ? option2 : option1;
    }

    @SuppressWarnings("unchecked")
    public static <T> boolean isIn(T value, T... items) {
        for (T item : items) {
            if (value.equals(item)) {
                return true;
            }
        }
        return false;
    }

    @SafeVarargs
    public static <T> boolean isInByString(T value, T... items) {
        for (T item : items) {
            if (value.toString().equals(item.toString())) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    public static BufferedImage deepCopy(@NotNull BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static int clamp(int min, int max, int value) {
        return value < min ? min : value > max ? max : value;
    }

    public static float clamp(float min, float max, float value) {
        return value < min ? min : value > max ? max : value;
    }

    @NotNull
    public static Color lerpColor(@NotNull Color a, @NotNull Color b, float ratio) {
        final int red = (int) ((ratio * a.getRed()) + ((1 - ratio) * b.getRed()));
        final int green = (int) ((ratio * a.getGreen()) + ((1 - ratio) * b.getGreen()));
        final int blue = (int) ((ratio * a.getBlue()) + ((1 - ratio) * b.getBlue()));
        final int alpha = (int) ((ratio * a.getAlpha()) + ((1 - ratio) * b.getAlpha()));
        return new Color(clamp(0, 255, red),
                         clamp(0, 255, green),
                         clamp(0, 255, blue),
                         clamp(0, 255, alpha));
    }

    @NotNull
    public static BufferedImage deepCopy2(@NotNull final BufferedImage src) {
        BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        int[] srcBuffer = ((DataBufferInt) src.getRaster().getDataBuffer()).getData();
        int[] dstBuffer = ((DataBufferInt) dst.getRaster().getDataBuffer()).getData();
        int width = src.getWidth();
        int height = src.getHeight();
        int dstOffset = 0 + 0 * dst.getWidth();
        int srcoffset = 0;
        for (int y = 0; y < height; y++, dstOffset += dst.getWidth(), srcoffset += width) {
            System.arraycopy(srcBuffer, srcoffset, dstBuffer, dstOffset, width);
        }
        return dst;
    }
}
