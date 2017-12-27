package Bombercraft2.playGround.Demos;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import utils.math.GVector2f;

import java.awt.*;

public class BasicDemo extends GameState {
    private class Field {
        public int x;
        public int y;
        public boolean selected = false;

        Field(int x, int y) {this.x = x; this.y = y;}
    }

    private Field[][] fields;
    private int blockSize   = 50;
    private int playerSize  = 20;
    private int playerSpeed = 1;
    private CorePlayGround parent;
    private Field player = new Field(50, 50);

    public BasicDemo(CorePlayGround parent) {
        super(Type.BasicDemo);
        this.parent = parent;

        final int numX = (int) Math.ceil((float) parent.getCanvas().getWidth() / blockSize);
        final int numY = (int) Math.ceil((float) parent.getCanvas().getHeight() / blockSize);
        System.out.println("numX: " + numX + ", numY: " + numY);
        fields = new Field[numX][numY];
        for (int i = 0; i < numX; i++) {
            for (int j = 0; j < numY; j++) {
                fields[i][j] = new Field(i, j);
            }
        }
    }

    @Override
    public void render(Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());


        for (Field[] field : fields) {
            for (Field aField : field) {
                aField.selected = false;
            }
        }

        GVector2f mousePos = Input.getMousePosition();
        preRayCast(player.x + (playerSize >> 1), player.y + (playerSize >> 1), mousePos.getXi(), mousePos.getYi());

        g2.setColor(Color.GRAY);
        for (Field[] field : fields) {
            for (Field aField : field) {
                if (!aField.selected) {
                    int posX = aField.x * blockSize;
                    int posY = aField.y * blockSize;
                    g2.fillRect(posX + 1, posY + 1, blockSize - 2, blockSize - 2);
                }
            }
        }

        g2.setColor(Color.GREEN);
        for (Field[] field : fields) {
            for (Field aField : field) {
                if (aField.selected) {
                    int posX = aField.x * blockSize;
                    int posY = aField.y * blockSize;
                    g2.fillRect(posX + 1, posY + 1, blockSize - 2, blockSize - 2);
                }
            }
        }

        g2.setColor(Color.BLUE);
        g2.fillArc(player.x, player.y, playerSize, playerSize, 0, 360);


        g2.setColor(Color.RED);
        g2.drawLine(player.x + (playerSize >> 1), player.y + (playerSize >> 1), mousePos.getXi(), mousePos.getYi());
    }

    private void preRayCast(int x1, int y1, int x2, int y2) {
        double atan2 = Math.atan2(y1 - y2, x2 - x1);
        // System.out.println("atan2: " + atan2 + ", sin: " + Math.sin(atan2) + ", cos: " + Math.cos(atan2));
        int lastX = (int) Math.ceil(x2 / blockSize);
        int lastY = (int) Math.ceil(y2 / blockSize);
        int firstX = (int) Math.ceil(x1 / blockSize);
        int firstY = (int) Math.ceil(y1 / blockSize);
        // drawLine(Math.min(lastX, firstX), Math.min(lastY, firstY), Math.max(lastX, firstX), Math.max(lastY, firstY));
        drawLinePixelByPixel(Math.min(x2, x1), y1, Math.max(x2, x1), y2);
    }

    private void rayCast(int x1, int y1, int x2, int y2) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                fields[i][j].selected = true;
            }
        }
    }
    private void drawLine(int x1, int y1, int x2, int y2){
        int  x = x1;
        int  y = y1;
        int  e = -(x2 - x1);
        while(x <= x2) {
            fields[(int)Math.floor(x / blockSize)][(int)Math.floor(y / blockSize)].selected = true;

            while(e > 0){
                y += y2 > y1 ? blockSize : -blockSize;
                e -= x2 - x1 << 1;
                fields[(int)Math.floor(x / blockSize)][(int)Math.floor(y / blockSize)].selected = true;
            }
            x += blockSize;
            e += (y2 > y1 ? (y2 - y1) : (y1 - y2)) << 1;
        }
    }
    private void drawLinePixelByPixel(int x1, int y1, int x2, int y2){
        int  x = x1;
        int  y = y1;
        int  e = -(x2 - x1);
        while(x <= x2) {
            fields[(int)Math.floor(x / blockSize)][(int)Math.floor(y / blockSize)].selected = true;

            while(e > 0){
                y += y2 > y1 ? 1 : -1;
                e -= x2 - x1 << 1;
                fields[(int)Math.floor(x / blockSize)][(int)Math.floor(y / blockSize)].selected = true;
            }
            x += 1;
            e += (y2 > y1 ? (y2 - y1) : (y1 - y2)) << 1;
        }
    }

    @Override
    public void input() {
        if (Input.getKeyDown(Input.KEY_ESCAPE)) {
            parent.stopDemo();
        }

        if (Input.isKeyDown(Input.KEY_A)) { player.x -= playerSpeed; }
        if (Input.isKeyDown(Input.KEY_D)) { player.x += playerSpeed; }
        if (Input.isKeyDown(Input.KEY_S)) { player.y += playerSpeed; }
        if (Input.isKeyDown(Input.KEY_W)) { player.y -= playerSpeed; }
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void cleanUp() {

    }

    @Override
    public void doAct(GVector2f click) {

    }
}
