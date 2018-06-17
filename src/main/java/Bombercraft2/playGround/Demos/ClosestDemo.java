package Bombercraft2.playGround.Demos;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.GameStateType;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.SimpleParticlePlayer;
import Bombercraft2.playGround.Misc.particles.SimpleParticle;
import org.glib2.math.physics.GClosest;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClosestDemo extends GameState {
    private final List<SimpleParticle> barrels                = new ArrayList<>();
    private final CorePlayGround       parent;
    private final SimpleParticlePlayer mySimpleParticlePlayer = new SimpleParticlePlayer();


    public ClosestDemo(CorePlayGround parent) {
        super(GameStateType.BasicDemo);
        this.parent = parent;

        mySimpleParticlePlayer.posX = 40;
        mySimpleParticlePlayer.posY = 40;
        mySimpleParticlePlayer.radius = 20;


        SimpleParticle barrel = new SimpleParticle();
        barrel.radius = 50;
        barrel.color = Color.GREEN;
        barrel.posX = 400;
        barrel.posY = 400;

        barrels.add(barrel);
    }

    private void renderBarrel(SimpleParticle barrel, Graphics2D g2) {
        g2.setColor(barrel.color);
        g2.fillArc((int) (barrel.posX - barrel.radius),
                   (int) (barrel.posY - barrel.radius),
                   barrel.radius << 1,
                   barrel.radius << 1,
                   0,
                   360);
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());

        barrels.forEach(barrel -> renderBarrel(barrel, g2));

        mySimpleParticlePlayer.render(g2);

        drawClosestPoint(g2, barrels.get(0));

    }

    private void drawClosestPoint(Graphics2D g2, SimpleParticle barrel) {
        org.glib2.math.vectors.GVector2f closest = GClosest.getClosestPointOnLine(mySimpleParticlePlayer.posX,
                                                                                  mySimpleParticlePlayer.posY,
                                                                                  mySimpleParticlePlayer.posX + (int) (Math
                                                                                          .cos(
                                                                                                  mySimpleParticlePlayer.angle) * mySimpleParticlePlayer.length * 100),
                                                                                  mySimpleParticlePlayer.posY + (int) (Math
                                                                                          .sin(
                                                                                                  mySimpleParticlePlayer.angle) * mySimpleParticlePlayer.length * 100),
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
        g2.drawLine((int) barrel.posX, (int) barrel.posY, closest.getXi(), closest.getYi());
        int maxSize = parent.getCanvas().getWidth() + parent.getCanvas().getHeight();
        g2.drawLine((int) mySimpleParticlePlayer.posX,
                    (int) mySimpleParticlePlayer.posY,
                    (int) (mySimpleParticlePlayer.posX + Math.cos(mySimpleParticlePlayer.angle) * maxSize),
                    (int) (mySimpleParticlePlayer.posY + Math.sin(mySimpleParticlePlayer.angle) * maxSize));
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }

        mySimpleParticlePlayer.input();
    }
}
