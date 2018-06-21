package org.bombercraft2.game.entity.towers;


import org.bombercraft2.core.Render;
import org.bombercraft2.game.GameAble;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public class TowerMachineGun extends Tower {

    public TowerMachineGun(GVector2f position, GameAble parent) {
        super(position, parent, Type.TOWER_MACHINE_GUN);
    }

    @Override
    public void update(float delta) {
        if (target != null && canShot()) { fire(); }
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        GVector2f pos = position.mul(getParent().getZoom()).sub(getParent().getOffset());
        GVector2f size = getSize().mul(getParent().getZoom());
        int[] xPos = new int[]{pos.getXi() + size.getXi() / 2,
                               pos.getXi() + size.getXi(),
                               pos.getXi() + size.getXi() / 2,
                               pos.getXi()};
        int[] yPos = new int[]{pos.getYi(),
                               pos.getYi() + size.getYi() / 2,
                               pos.getYi() + size.getYi(),
                               pos.getYi() + size.getYi() / 2};

        g2.setColor(backgroundColor);
        g2.fillPolygon(xPos, yPos, 4);

        g2.setStroke(new BasicStroke(borderSize * getParent().getZoom()));
        g2.setColor(borderColor);
        g2.drawPolygon(xPos, yPos, 4);

        pos = pos.add(size.div(2));


        if (angle - actAngle < cannonsSpeed) {
            actAngle = angle;
        }
        else {
            actAngle += actAngle < angle ? cannonsSpeed : -cannonsSpeed;
        }

        if (target != null) {
            GVector2f dir = position.sub(target.getPosition()).Normalized();
            angle = Math.atan2(dir.getX(), dir.getY());

            if (target.getPosition().dist(position) > range || !target.isAlive()) {
                target = null;
            }
        }
        else {
            setRandTarget();
        }


        GVector2f toMouse = getDirection();
        GVector2f point2 = pos.add(toMouse.mul(cannonLength * getParent().getZoom()));
        g2.setStroke(new BasicStroke(cannonWidth * getParent().getZoom()));
        g2.setColor(canonColor);
        g2.drawLine(pos.getXi(), pos.getYi(), point2.getXi(), point2.getYi());


        if (true || getParent().getVisibleOption(Render.TOWER_RANGE)) {
            g2.setColor(rangeColor);
            g2.setStroke(new BasicStroke(1));
            pos = pos.sub(range * getParent().getZoom());
            int finalSize = (int) (range * 2 * getParent().getZoom());
            g2.drawArc(pos.getXi(), pos.getYi(), finalSize, finalSize, 0, 360);
        }

    }

    private void setRandTarget() {
//		ArrayList<Enemy> enemies = getParent().getEnemiesAround(position, range);
//		
//		if(enemies.size() > 0){
//			//target = enemies.get((int)(Math.random() * enemies.size())); -- vyberalo n�hodn�ho nie najbli��ie
//			target = enemies.stream()
//							.reduce((a, b) -> a.getPosition().dist(position) > b.getPosition().dist(position) ? a : b).get();
//			GVector2f dir = position.sub(target.getPosition()).Normalized();
//			angle = Math.atan2(dir.getX(), dir.getY());
//		}
    }

}
