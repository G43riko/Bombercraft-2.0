package utils;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
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
import java.util.Enumeration;
import java.util.List;

import javax.imageio.ImageIO;

import Bombercraft2.Bombercraft2.game.player.Player;
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
	public static long ping(String ip){return ping(ip, 1000);}
	public static long ping(String ip, int timeout){
		try {
			long currentTime = System.currentTimeMillis();
			InetAddress.getByName(ip).isReachable(timeout);
	        return System.currentTimeMillis() - currentTime;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static String getMyIP() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "Error";
	}
	static public String getLocalIP(){
        String result = "";
        try{
       Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
    while (interfaces.hasMoreElements()){
        NetworkInterface current = interfaces.nextElement();
        //System.out.println(current);
        if (!current.isUp() || current.isLoopback() || current.isVirtual()) 
            continue;
        Enumeration<InetAddress> addresses = current.getInetAddresses();
        while (addresses.hasMoreElements()){
            InetAddress current_addr = addresses.nextElement();
            if (current_addr.isLoopbackAddress()) 
                continue;
            if (current_addr instanceof Inet4Address)
              result = result.concat(current_addr.getHostAddress() + "\n");
            //else if (current_addr instanceof Inet6Address)
            // System.out.println(current_addr.getHostAddress());
            //System.out.println(current_addr.getHostAddress());
        }
    }
        }catch (Exception e){
        }
    return result;
   }
	
	public static String getHostName(){
		try{
		    InetAddress addr = InetAddress.getLocalHost();
		    return addr.getHostName();
		}
		catch (UnknownHostException e){
		    e.printStackTrace();
		}
		return "Unknown";
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T choose(T ... argc){
		return argc[(int)(Math.random() * argc.length)];
	};
	
	public static <T> T choose(List<T> list){
		return list.get((int)(Math.random() * list.size()));
	};
	
	public static URL File2URL(File file) throws MalformedURLException{
		return file.toURI().toURL();
	}
	
	public static BufferedImage getFullscreenScreenshotImage() throws AWTException{
		Robot robot = new Robot();
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        return robot.createScreenCapture(screenRect);
	}
	public static void takeFullscreenScreenshot(String fileName){takeFullscreenScreenshot(fileName, "jpg");}
	public static void takeFullscreenScreenshot(String fileName, String format){
		try {
            BufferedImage screenFullImage = getFullscreenScreenshotImage();
            ImageIO.write(screenFullImage, format, new File(fileName));
             
            System.out.println("A full screenshot saved!");
        } catch (AWTException | IOException ex) {
            System.err.println(ex);
        }
	}

	public static GVector2f getNormalMoveFromDir(Player.Direction dir) {
		switch(dir){
			case LEFT:
				return new GVector2f(-1, 0);
			case RIGHT:
				return new GVector2f(01, 00);
			case UP:
				return new GVector2f(00, -1);
			case DOWN:
				return new GVector2f(00, 01);
			default:
				return new GVector2f();
		}
	}
	
	public static void sleep(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@SafeVarargs
	public static <T> T nvl(T ... options){
		for(int i=0 ; i<options.length ; i++){
			if(options[i] != null){
				return options[i];
			}
		}
		return null;
	}
	public static <T> T nvl(T option1, T option2){
		return option1 == null ? option2 : option1;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> boolean isIn(T value, T ...items){
		for(int i=0 ; i<items.length ; i++){
			if(value.equals(items[i])){
				return true;
			}
		}
		return false;
	}
	public static <T> boolean isInStringable(T value, T ...items){
		for(int i=0 ; i<items.length ; i++){
			if(value.toString().equals(items[i].toString())){
				return true;
			}
		}
		return false;
	}
	
	public static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	public static BufferedImage deepCopy2(final BufferedImage src) {
		BufferedImage dst 	= new BufferedImage(src.getWidth(), src.getHeight(),src.getType());
	    int[] srcbuf 		= ((DataBufferInt) src.getRaster().getDataBuffer()).getData();
	    int[] dstbuf 		= ((DataBufferInt) dst.getRaster().getDataBuffer()).getData();
	    int width 			= src.getWidth();
	    int height 			= src.getHeight();
	    int dstoffs 		= 0 + 0 * dst.getWidth();
	    int srcoffs 		= 0;
	    for (int y = 0 ; y < height ; y++ , dstoffs += dst.getWidth(), srcoffs += width ) {
	        System.arraycopy(srcbuf, srcoffs , dstbuf, dstoffs, width);
	    }
	    return dst;
	}
}
