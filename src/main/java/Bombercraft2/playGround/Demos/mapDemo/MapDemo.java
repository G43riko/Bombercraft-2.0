package Bombercraft2.playGround.Demos.mapDemo;

import java.awt.Canvas;
import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.SimpleBlock;
import Bombercraft2.playGround.Misc.SimpleMap;
import Bombercraft2.playGround.Misc.SimpleParentAble;
import Bombercraft2.playGround.Misc.ViewManager;
import utils.math.GVector2f;

public class MapDemo extends GameState implements SimpleParentAble{
	private final static float SPEED = 3;
	private final static GVector2f NUMBERS_OF_BLOCKS = new GVector2f(100, 100);
    private CorePlayGround parent;
    private ViewManager viewManager;
    private SimpleMap map;
    
	public MapDemo(CorePlayGround parent) {
		super(Type.MapDemo);
		viewManager = new ViewManager(NUMBERS_OF_BLOCKS.mul(SimpleBlock.SIZE), 
									  parent.getCanvas().getWidth(), 
									  parent.getCanvas().getHeight());
		this.parent = parent;
		map = new SimpleMap(this, NUMBERS_OF_BLOCKS);
		
	}

	@Override
	public void doAct(GVector2f click) {
		// TODO Auto-generated method stub
		
	}
	

    @Override
    public void render(Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
        map.render(g2);
    }

	@Override
    public boolean isVisible(Visible b) {
        return !(b.getPosition().getX() * getZoom() + b.getSize().getX() * getZoom() < getOffset().getX() ||
                b.getPosition().getY() * getZoom() + b.getSize().getY() * getZoom() < getOffset().getY() ||
                getOffset().getX() + parent.getCanvas().getWidth() < b.getPosition().getX() * getZoom() ||
                getOffset().getY() + parent.getCanvas().getHeight() < b.getPosition().getY() * getZoom());
    }
	
	
	@Override
	public void input() {
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }
		if (Input.isKeyDown(Input.KEY_A)) { viewManager.moveX(-SPEED * viewManager.getZoom()); }
        if (Input.isKeyDown(Input.KEY_D)) { viewManager.moveX( SPEED * viewManager.getZoom()); }
        if (Input.isKeyDown(Input.KEY_S)) { viewManager.moveY( SPEED * viewManager.getZoom()); }
        if (Input.isKeyDown(Input.KEY_W)) { viewManager.moveY(-SPEED * viewManager.getZoom()); }
        if (Input.isKeyDown(Input.KEY_Q)) { viewManager.zoom( SPEED / 100); }
        if (Input.isKeyDown(Input.KEY_E)) { viewManager.zoom(-SPEED / 100); }
	}
	@Override
	public void onResize() {
		viewManager.setCanvasSize(parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
	}
	@Override
	public float getZoom() {
		return viewManager.getZoom();
	}

	@Override
	public GVector2f getOffset() {
		return viewManager.getOffset();
	}

	@Override
	public Canvas getCanvas() {
		return parent.getCanvas();
	}

}
