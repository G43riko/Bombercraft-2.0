package Bombercraft2.playGround.Demos;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.SimpleParticle;
import Bombercraft2.playGround.Misc.SimplePlayer;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShootingDemo extends GameState {
    private CorePlayGround parent;
    private SimplePlayer         myPlayer  = new SimplePlayer();
    private List<SimpleParticle> particles = new ArrayList<>();
    public ShootingDemo(CorePlayGround parent){
        super(Type.ShootingDemo);
        this.parent = parent;

        myPlayer.posX = 40;
        myPlayer.posY = 40;
        myPlayer.radius = 20;
    }

    @Override
    public void update(float delta) {
        particles.forEach(particle -> particle.move(delta));
        particles.forEach(particle -> particle.checkBorders(parent.getCanvas()));
    }

    @Override
    public void render(Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());
        myPlayer.render(g2);

        g2.setColor(Color.blue);
        particles.forEach((particle) -> particle.fillArc(g2));



        float[] borders = {0.0f, 1.0f};
        Color[] colors = {Color.BLUE, new Color(0, 0,0 , 0)};

        g2.setPaint(new RadialGradientPaint(60, 60, 40, borders, colors, MultipleGradientPaint.CycleMethod.REFLECT));
        g2.fillArc(20, 20, 80, 80, 0, 360);
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }
        if (Input.getMouseDown(Input.BUTTON_LEFT)) {
            SimplePlayer bullet = new SimplePlayer();

            bullet.posX = (float) (myPlayer.posX + Math.cos(myPlayer.angle) * myPlayer.length);
            bullet.posY = (float) (myPlayer.posY + Math.sin(myPlayer.angle) * myPlayer.length);

            final float BULLET_SPEED = 10f;
            bullet.velX = (float) (Math.cos(myPlayer.angle) * BULLET_SPEED);
            bullet.velY = (float) (Math.sin(myPlayer.angle) * BULLET_SPEED);
            bullet.color = Color.BLACK;
            bullet.radius = 5;

            particles.add(bullet);
        }
        if (Input.getMouseDown(Input.BUTTON_RIGHT)) {
            particles.clear();
        }
        myPlayer.input();
    }

    @Override
    public void doAct(GVector2f click) {

    }
}
