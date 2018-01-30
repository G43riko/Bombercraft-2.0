package Bombercraft2.Bombercraft2.game.entity;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.GameAble;
import Bombercraft2.Bombercraft2.game.Timer;
import Bombercraft2.Bombercraft2.game.level.Block;
import Bombercraft2.Bombercraft2.game.level.Map;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bomb extends Helper {
    private final long addedAt;
    private final int             detonationTime = 2000;
    private final Timer timer;
    private final int             range          = 3;
    private final List<Block>     blocks         = new ArrayList<>();
    private final List<GVector2f> damageAreas    = new ArrayList<>();

    public Bomb(GVector2f position, GameAble parent, Helper.Type type, long addedAt) {
        super(position, parent, type);
        this.addedAt = addedAt;
        timer = new Timer(this, addedAt, detonationTime);
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        if (!alive) {
            return;
        }
        GVector2f actPos = position.sub(getParent().getOffset());

        renderSimpleArea(g2, actPos);

        timer.render(g2);
        g2.setColor(Color.BLACK);
        g2.fillArc(actPos.getXi(), actPos.getYi(), Config.BOMB_WIDTH, Config.BOMB_HEIGHT, 0, 360);
    }

    private void renderSimpleArea(Graphics2D g2, GVector2f actPos) {
        GVector2f localPos = Map.globalPosToLocalPos(position);
        int value, counter;
        g2.setColor(Config.BOMB_AREA_COLOR);


        //RIGHT
        value = localPos.getXi() + 1;
        counter = 0;
        while (getParent().getLevel().getMap().isWalkable(value, localPos.getYi()) && counter < range) {
            value++;
            counter++;
        }
        g2.fillRect(actPos.getXi() + Config.BLOCK_SIZE.getXi(),
                    actPos.getYi(),
                    Config.BLOCK_SIZE.getXi() * counter,
                    Config.BLOCK_SIZE.getYi());

        //LEFT
        value = localPos.getXi() - 1;
        counter = 0;
        while (getParent().getLevel().getMap().isWalkable(value, localPos.getYi()) && counter < range) {
            value--;
            counter++;
        }

        g2.fillRect(actPos.getXi() - Config.BLOCK_SIZE.getXi() * counter,
                    actPos.getYi(),
                    Config.BLOCK_SIZE.getXi() * counter,
                    Config.BLOCK_SIZE.getYi());


        //DOWN
        value = localPos.getYi() + 1;
        counter = 0;
        while (getParent().getLevel().getMap().isWalkable(localPos.getXi(), value) && counter < range) {
            value++;
            counter++;
        }

        g2.fillRect(actPos.getXi(),
                    actPos.getYi() + Config.BLOCK_SIZE.getYi(),
                    Config.BLOCK_SIZE.getXi(),
                    Config.BLOCK_SIZE.getYi() * counter);

        //UP
        value = localPos.getYi() - 1;
        counter = 0;
        while (getParent().getLevel().getMap().isWalkable(localPos.getXi(), value) && counter < range) {
            value--;
            counter++;
        }

        g2.fillRect(actPos.getXi(),
                    actPos.getYi() - Config.BLOCK_SIZE.getYi() * counter,
                    Config.BLOCK_SIZE.getXi(),
                    Config.BLOCK_SIZE.getYi() * counter);
    }

    private void calcTargetBlocks() {
        GVector2f actPos = position.sub(getParent().getOffset());
        GVector2f localPos = Map.globalPosToLocalPos(position);
        int value, counter;
        Block b;


        //RIGHT
        value = localPos.getXi() + 1;
        counter = 0;
        b = getParent().getLevel().getMap().getBlock(value, localPos.getYi());
        while (b != null && b.isWalkable() && counter + 1 < range) {
            value++;
            counter++;
            blocks.add(b);
            b = getParent().getLevel().getMap().getBlock(value, localPos.getYi());
        }
        if (b != null && !b.isWalkable()) {
            blocks.add(b);
        }
        counter++;
        damageAreas.add(new GVector2f(position.getXi() + Config.BLOCK_SIZE.getXi(), position.getYi()));
        damageAreas.add(new GVector2f(Config.BLOCK_SIZE.getXi() * counter, Config.BLOCK_SIZE.getYi()));


        //LEFT
        value = localPos.getXi() - 1;
        counter = 0;
        b = getParent().getLevel().getMap().getBlock(value, localPos.getYi());
        while (b != null && b.isWalkable() && counter + 1 < range) {
            value--;
            counter++;
            blocks.add(b);
            b = getParent().getLevel().getMap().getBlock(value, localPos.getYi());
        }
        if (b != null && !b.isWalkable()) {
            blocks.add(b);
        }
        counter++;
        damageAreas.add(new GVector2f(position.getXi() - Config.BLOCK_SIZE.getXi() * counter, position.getYi()));
        damageAreas.add(new GVector2f(Config.BLOCK_SIZE.getXi() * counter, Config.BLOCK_SIZE.getYi()));


        //DOWN
        value = localPos.getYi() + 1;
        counter = 0;
        b = getParent().getLevel().getMap().getBlock(localPos.getXi(), value);
        while (b != null && b.isWalkable() && counter + 1 < range) {
            value++;
            counter++;
            blocks.add(b);
            b = getParent().getLevel().getMap().getBlock(localPos.getXi(), value);
        }
        if (b != null && !b.isWalkable()) {
            blocks.add(b);
        }
        counter++;
        damageAreas.add(new GVector2f(position.getXi(), position.getYi() + Config.BLOCK_SIZE.getYi()));
        damageAreas.add(new GVector2f(Config.BLOCK_SIZE.getXi(), Config.BLOCK_SIZE.getYi() * counter));

        //UP
        value = localPos.getYi() - 1;
        counter = 0;
        b = getParent().getLevel().getMap().getBlock(localPos.getXi(), value);
        while (b != null && b.isWalkable() && counter + 1 < range) {
            value--;
            counter++;
            blocks.add(b);
            b = getParent().getLevel().getMap().getBlock(localPos.getXi(), value);
        }
        if (b != null && !b.isWalkable()) {
            blocks.add(b);
        }
        counter++;
        damageAreas.add(new GVector2f(position.getXi(), position.getYi() - Config.BLOCK_SIZE.getYi() * counter));
        damageAreas.add(new GVector2f(Config.BLOCK_SIZE.getXi(), Config.BLOCK_SIZE.getYi() * counter));

    }

    @Override
    public void update(float delta) {
        timer.update(delta);
        if (addedAt + detonationTime < System.currentTimeMillis()) {
            explode();
        }
    }

    private void explode() {
        alive = false;
        calcTargetBlocks();
        getParent().getConnector().setBombExplode(Map.globalPosToLocalPos(position), blocks, damageAreas);
        getParent().addExplosion(position.add(Config.BLOCK_SIZE_HALF), Config.BLOCK_SIZE, Color.black, 15, true, true);
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public JSONObject toJSON() {
        // TODO Auto-generated method stub
        return null;
    }
}
