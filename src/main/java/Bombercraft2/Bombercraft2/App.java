package Bombercraft2.Bombercraft2;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Hello world!
 *
 */
public class App{
    public static void main(String[] args){
    	
        Bombercraft game = new Bombercraft();
		game.run(); 
    }
}
