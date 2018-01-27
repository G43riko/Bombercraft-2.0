package Bombercraft2.playGround.Demos;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.SimplePlayer;
import Bombercraft2.playGround.Misc.particles.SimpleParticle;
import org.jetbrains.annotations.NotNull;
import utils.math.GClosest;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClosestDemo extends GameState {
    private final List<SimpleParticle> barrels = new ArrayList<>();
    private final CorePlayGround parent;
    private final SimplePlayer mySimplePlayer = new SimplePlayer();


    public ClosestDemo(CorePlayGround parent) {
        super(Type.BasicDemo);
        this.parent = parent;

        mySimplePlayer.posX = 40;
        mySimplePlayer.posY = 40;
        mySimplePlayer.radius = 20;


        SimpleParticle barrel = new SimpleParticle();
        barrel.radius = 50;
        barrel.color = Color.GREEN;
        barrel.posX = 400;
        barrel.posY = 400;

        barrels.add(barrel);
    }

    private void renderBarrel(SimpleParticle barrel, Graphics2D g2) {
        g2.setColor(barrel.color);
        g2.fillArc((int)(barrel.posX - barrel.radius),
                   (int)(barrel.posY - barrel.radius),
                   barrel.radius << 1,
                   barrel.radius << 1,
                   0,
                   360);
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());

        barrels.forEach(barrel -> renderBarrel(barrel, g2));

        mySimplePlayer.render(g2);

        drawClosestPoint(g2, barrels.get(0));

    }

    private void drawClosestPoint(Graphics2D g2, SimpleParticle barrel) {
        GVector2f closest = GClosest.getClosestPointOnLine(mySimplePlayer.posX,
                                                           mySimplePlayer.posY,
                                                           mySimplePlayer.posX + (int) (Math.cos(mySimplePlayer.angle) * mySimplePlayer.length * 100),
                                                           mySimplePlayer.posY + (int) (Math.sin(mySimplePlayer.angle) * mySimplePlayer.length * 100),
                                                           barrel.posX,
                                                           barrel.posY);


        final int targetSize = 20;
        g2.setColor(Color.BLUE);
        g2.fillArc(closest.getXi() - (targetSize >> 1),
                   closest.getYi() - (targetSize >> 1),
                   targetSize,
                   targetSize,
                   0,
                   360);

        g2.setColor(Color.RED);
        g2.drawLine((int)barrel.posX, (int)barrel.posY, closest.getXi(), closest.getYi());
        int maxSize = parent.getCanvas().getWidth() + parent.getCanvas().getHeight();
        g2.drawLine((int)mySimplePlayer.posX,
                    (int)mySimplePlayer.posY,
                    (int)(mySimplePlayer.posX + Math.cos(mySimplePlayer.angle) * maxSize),
                    (int)(mySimplePlayer.posY + Math.sin(mySimplePlayer.angle) * maxSize));
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }

        mySimplePlayer.input();
    }

    @Override
    public void doAct(GVector2f click) {

    }
}
