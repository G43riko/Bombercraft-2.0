package Bombercraft2.Bombercraft2.game.entity.flora;

import Bombercraft2.Bombercraft2.game.GameAble;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.awt.*;

public class Tree extends Flora {
    public Tree(Trees type, GVector2f position, GameAble parent) {
        super(position, parent, type);
        scale = (float) Math.random() / 2 + 0.25f;
    }

    public Tree(GameAble parent, JSONObject data) {
        super(new GVector2f(), parent, Trees.TREE1);
        fromJSON(data);
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        GVector2f pos = position.sub(getParent().getOffset());
        g2.drawImage(type.getImage(),
                     pos.getXi(),
                     pos.getYi(),
                     (int) (type.getSize().getX() * scale),
                     (int) (type.getSize().getY() * scale),
                     null);
        super.render(g2);
    }


    @Contract(pure = true)
    @NotNull
    @Override
    public GVector2f getSize() {
        return type.getSize().mul(scale);
    }
}
