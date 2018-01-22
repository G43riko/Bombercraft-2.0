package Bombercraft2.playGround.Misc;

import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.Bombercraft2.game.level.Block;
import utils.math.GVector2f;

public class SimpleBlock implements Visible{
	private SimpleParentAble parent;
	private GVector2f position;
    private Block.Type type;
    
    public final static GVector2f SIZE = new GVector2f(Config.DEFAULT_BLOCK_WIDTH,
            										   Config.DEFAULT_BLOCK_HEIGHT);
    
	public SimpleBlock(GVector2f position, int type, SimpleParentAble parent) {
		this.parent = parent;
		this.position = position;

        this.type = Block.getTypeFromInt(type);
	}
	public void render(Graphics2D g2) {
        GVector2f size = SIZE.mul(parent.getZoom());
        GVector2f pos = position.mul(size).sub(parent.getOffset());

        g2.drawImage(type.getImage(), pos.getXi(), pos.getYi(), size.getXi() + 1, size.getYi() + 1, null);
    }
	
	public Block.Type getType() {
		return type;
	}
	@Override
	public GVector2f getPosition() {
		return position.mul(SIZE);
	}
	@Override
	public GVector2f getSize() {
		return SIZE;
	}
}
