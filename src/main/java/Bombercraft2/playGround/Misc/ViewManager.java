package Bombercraft2.playGround.Misc;

import Bombercraft2.Bombercraft2.Config;
import utils.Utils;
import utils.math.GVector2f;

public class ViewManager {
	private GVector2f mapSize;
	private GVector2f canvasSize = new GVector2f();
	private GVector2f maxOffset = new GVector2f();
	private float minZoom;
	
    private float zoom = Config.DEFAULT_ZOOM;
    private GVector2f offset = new GVector2f();
    
    public ViewManager(GVector2f mapSize, int canvasWidth, int canvasHeight) {
    	this.mapSize = mapSize;
    	setCanvasSize(canvasWidth, canvasHeight);
    }
    
    public void setCanvasSize(int width, int height) {
    	canvasSize.set(width, height);
    	
    	calcMinZoom();
    }
    
    public float getZoom() {
    	return zoom;
    }
    public GVector2f getOffset() {
    	return offset;
    }
    private void calcMinZoom() {
		minZoom = Math.max(canvasSize.getX() / mapSize.getX(), 
						   canvasSize.getY() / mapSize.getY());
		minZoom = Math.max(minZoom, Config.MIN_ZOOM);
		checkOffset();
	}
    
    public void moveX(float value) {
    	offset.addToX(value);
        offset.setX(Utils.clamp(0f, maxOffset.getX(), offset.getX()));
    }
    
    public void moveY(float value) {
    	offset.addToY(value);
        offset.setY(Utils.clamp(0f, maxOffset.getY(), offset.getY()));
    }
    
    private void checkOffset() {
        offset.setX(Utils.clamp(0f, maxOffset.getX(), offset.getX()));
        offset.setY(Utils.clamp(0f, maxOffset.getY(), offset.getY()));
    	
    }
    
    public void zoom(float value) {
		zoom = Math.max(minZoom, zoom + value);
		maxOffset.set(mapSize.mul(zoom).sub(canvasSize));
		checkOffset();
    }
}
