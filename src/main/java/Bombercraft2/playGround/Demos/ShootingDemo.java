package Bombercraft2.playGround.Demos;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.SimplePlayer;
import Bombercraft2.playGround.Misc.map.SimpleMap_old;
import Bombercraft2.playGround.Misc.particles.SimpleDrawableObject;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShootingDemo extends GameState {
    private final SimpleMap_old        map       = new SimpleMap_old();
    private final int                  blockSize = 40;
    private final SimplePlayer         myPlayer  = new SimplePlayer();
    private final List<SimpleDrawableObject> particles = new ArrayList<>();
    private final CorePlayGround parent;

    public ShootingDemo(CorePlayGround parent) {
        super(Type.ShootingDemo);
        this.parent = parent;

        myPlayer.posX = 160;
        myPlayer.posY = 160;
        myPlayer.radius = 20;

        generateMap();
    }

    private void generateMap() {
        final int numX = (int) Math.ceil((float) parent.getCanvas().getWidth() / blockSize);
        final int numY = (int) Math.ceil((float) parent.getCanvas().getHeight() / blockSize);
        map.init(numX, numY);
    }

    @Override
    public void update(float delta) {
        particles.forEach(particle -> particle.move(delta));
        particles.forEach(particle -> particle.checkBorders(parent.getCanvas()));


        SimpleMap_old.Field actField = map.getBlock((int) (myPlayer.posX / blockSize),
                                                    (int) (myPlayer.posY / blockSize));
        map.reset();


        actField.selected = true;
    }

    @Override
    public void render(Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());

        /***********************************************/
        final double sinA = Math.sin(myPlayer.angle);
        final double cosA = Math.cos(myPlayer.angle);
        final boolean vertical = cosA > sinA;
        float offsetX, offsetY;
        final double height = (double) blockSize / (vertical ? sinA : Math.sin(myPlayer.angle + Math.PI / 2));
        offsetX = (float) (Math.cos(myPlayer.angle) * height);
        offsetY = (float) (Math.sin(myPlayer.angle) / Math.cos(myPlayer.angle) * height);
        if (false) {
            float tmp = offsetX;
            offsetX = offsetY;
            offsetY = tmp;
        }
        System.out.println("vertical: " + offsetY + " " + offsetX + " ======= " + myPlayer.angle);

        if (sinA < 0) {
            offsetY = -offsetY;
            offsetX = -offsetX;
        }
        if (offsetX < 0) {
            offsetY = -offsetY;
        }
        // System.out.println("----------" + myPlayer.angle + " == " + sinA + " = " + cosA);

        ArrayList<GVector2f> points = new ArrayList<>();

        float currentX = myPlayer.posX + offsetX;
        float currentY = myPlayer.posY + offsetY;
        for (int i = 0; i < 3; i++) {
            SimpleMap_old.Field field = map.getBlock((int) (currentX / blockSize),
                                                     (int) (currentY / blockSize));
            if (field != null) {
                field.selected = true;
            }
            points.add(new GVector2f(currentX, currentY));
            currentY += offsetY;
            currentX += offsetX;
        }
        /***********************************************/


        map.forEach(field -> {
            g2.setColor(field.selected ? Color.DARK_GRAY : Color.LIGHT_GRAY);
            int posX = field.x * blockSize;
            int posY = field.y * blockSize;
            g2.fillRect(posX + 1, posY + 1, blockSize - 2, blockSize - 2);
        });

        myPlayer.render(g2);

        g2.setColor(Color.blue);
        particles.forEach((particle) -> particle.fillArc(g2));


        g2.setColor(Color.RED);
        points.forEach(p -> g2.fillArc(p.getXi() - 2, p.getYi() - 2, 4, 4, 0, 360));

        /*
        float[] borders = {0.0f, 1.0f};
        Color[] colors = {Color.BLUE, new Color(0, 0,0 , 0)};
        g2.setPaint(new RadialGradientPaint(60, 60, 40, borders, colors, MultipleGradientPaint.CycleMethod.REFLECT));
        g2.fillArc(20, 20, 80, 80, 0, 360);
        */
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
