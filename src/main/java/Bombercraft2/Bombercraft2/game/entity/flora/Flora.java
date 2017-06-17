package Bombercraft2.Bombercraft2.game.entity.flora;

import java.awt.Image;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.Iconable;
import Bombercraft2.Bombercraft2.game.entity.Entity;
import utils.math.GVector2f;
import utils.resouces.ResourceLoader;

public abstract class Flora extends Entity{
	protected Florable type;
	protected interface Florable extends Iconable{
		
		public GVector2f getSize();
		public FloraType getType();
	}
	public enum FloraType{
		BUSH, TREE, PLANT;
	}
	public Flora(GVector2f position, GameAble parent, Florable type) {
		super(position, parent);
		this.type = type;
	}
	
	public Florable getType(){
		return type;
	}

	public static enum Bushes implements Florable{
		BUSH1("bush1", FloraType.BUSH),
		BUSH2("bush2", FloraType.BUSH),
		BUSH3("bush3", FloraType.BUSH);
		
		private Image image;
		private FloraType type;
		private GVector2f size;
		Bushes(String imageName, FloraType type){
			this.type = type;
			image = ResourceLoader.loadTexture(imageName + Config.EXTENSION_IMAGE);
			size = new GVector2f(image.getWidth(null), image.getHeight(null));
		}
		public Image getImage(){return image;}
		public GVector2f getSize(){return size;}
		public FloraType getType(){return type;}
		
	}
	public static enum Trees implements Florable{
		TREE1("tree1", FloraType.TREE),
		TREE2("tree2", FloraType.TREE),
		TREE3("tree3", FloraType.TREE),
		TREE4("tree4", FloraType.TREE),
		TREE5("tree5", FloraType.TREE),
		TREE6("tree6", FloraType.TREE);
		
		private Image image;
		private FloraType type;
		private GVector2f size;
		Trees(String imageName, FloraType type){
			this.type = type;
			image = ResourceLoader.loadTexture(imageName + Config.EXTENSION_IMAGE);
			size = new GVector2f(image.getWidth(null), image.getHeight(null));
		}
		public Image getImage(){return image;}
		public GVector2f getSize(){return size;}
		public FloraType getType(){return type;}
	}
	public static enum Plants implements Florable{
		PLANT1("plant1", FloraType.PLANT),
		PLANT2("plant2", FloraType.PLANT),
		PLANT3("plant3", FloraType.PLANT),
		PLANT4("plant4", FloraType.PLANT),
		PLANT5("plant5", FloraType.PLANT);
		
		private Image image;
		private FloraType type;
		private GVector2f size;
		Plants(String imageName, FloraType type){
			this.type = type;
			image = ResourceLoader.loadTexture(imageName + Config.EXTENSION_IMAGE);
			size = new GVector2f(image.getWidth(null), image.getHeight(null));
		}
		public Image getImage(){return image;}
		public GVector2f getSize(){return size;}
		public FloraType getType(){return type;}
	}
}
