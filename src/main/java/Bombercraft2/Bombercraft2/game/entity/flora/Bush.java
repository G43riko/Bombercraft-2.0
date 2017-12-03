package Bombercraft2.Bombercraft2.game.entity.flora;

import Bombercraft2.Bombercraft2.game.GameAble;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.awt.*;

public class Bush extends Flora {

    public Bush(Bushes type, GVector2f position, GameAble parent) {
        super(position, parent, type);
        scale = (float) Math.random() + 0.5f;
    }

    public Bush(GameAble parent, JSONObject data) {
        super(new GVector2f(), parent, Bushes.BUSH1);
        fromJSON(data);
    }

    @Override
    public void render(Graphics2D g2) {
        GVector2f pos = position.sub(getParent().getOffset());
        g2.drawImage(type.getImage(),
                     pos.getXi(),
                     pos.getYi(),
                     (int) (type.getSize().getX() * scale),
                     (int) (type.getSize().getY() * scale),
                     null);
        super.render(g2);
    }


    @Override
    public GVector2f getSize() {
        return type.getSize().mul(scale);
    }

}
