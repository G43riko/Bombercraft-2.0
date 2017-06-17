package Bombercraft2.Bombercraft2.game.player;

import java.awt.Graphics2D;

import Bombercraft2.Bombercraft2.game.level.Map;
import utils.math.GVector2f;

public interface Toolable {
	default public void onSelect(){};
	default public void onUnselect(){};
	default public void render(Graphics2D g2){};

	default public void useOnLocalPos(GVector2f pos){};
	default public void useOnGlobalPos(GVector2f pos) {useOnLocalPos(Map.globalPosToLocalPos(pos));}
	default public void useOnScreenPos(GVector2f pos, GVector2f offset) {useOnGlobalPos(pos.add(offset));}
}
