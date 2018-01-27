package Bombercraft2.playGround.Demos.mapDemo;

import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import Bombercraft2.playGround.Misc.ViewManager;
import Bombercraft2.playGround.Misc.map.SimpleMap;
import utils.math.GVector2f;

public class MapDemo extends GameState implements SimpleGameAble {
    private final static GVector2f NUMBERS_OF_BLOCKS = new GVector2f(100, 100);
    private final CorePlayGround parent;
    private final ViewManager    viewManager;
    private final SimpleMap      map;

    public MapDemo(CorePlayGround parent) {
        super(Type.MapDemo);
        viewManager = new ViewManager(NUMBERS_OF_BLOCKS.mul(Block.SIZE),
                                      parent.getCanvas().getWidth(),
                                      parent.getCanvas().getHeight(),
                                      3);
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
        viewManager.input();
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
    public GVector2f getCanvasSize() {
        return viewManager.getCanvasSize();
    }

    @Override
    public GVector2f getOffset() {
        return viewManager.getOffset();
    }
}