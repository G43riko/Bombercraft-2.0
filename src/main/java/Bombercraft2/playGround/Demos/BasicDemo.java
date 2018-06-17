package Bombercraft2.playGround.Demos;

import Bombercraft2.Bombercraft2.core.GameState;
import Bombercraft2.Bombercraft2.core.GameStateType;
import Bombercraft2.engine.Input;
import Bombercraft2.playGround.CorePlayGround;
import Bombercraft2.playGround.Misc.map.SimpleMap_old;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public class BasicDemo extends GameState {
    private final SimpleMap_old.Field[][] fields;
    private final int blockSize   = 100;
    private final int playerSize  = 1;
    private final int playerSpeed = 1;
    private final CorePlayGround parent;
    private final SimpleMap_old.Field player = new SimpleMap_old.Field(50, 50);

    public BasicDemo(CorePlayGround parent) {
        super(GameStateType.BasicDemo);
        this.parent = parent;

        final int numX = (int) Math.ceil((float) parent.getCanvas().getWidth() / blockSize);
        final int numY = (int) Math.ceil((float) parent.getCanvas().getHeight() / blockSize);
        System.out.println("numX: " + numX + ", numY: " + numY);
        fields = new SimpleMap_old.Field[numX][numY];
        for (int i = 0; i < numX; i++) {
            for (int j = 0; j < numY; j++) {
                fields[i][j] = new SimpleMap_old.Field(i, j);
            }
        }
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, parent.getCanvas().getWidth(), parent.getCanvas().getHeight());


        for (SimpleMap_old.Field[] field : fields) {
            for (SimpleMap_old.Field aField : field) {
                aField.selected = false;
            }
        }



        GVector2f mousePos = Input.getMousePosition();
        preRayCast(player.x + (playerSize >> 1), player.y + (playerSize >> 1), mousePos.getXi(), mousePos.getYi());

        g2.setColor(Color.GRAY);
        for (SimpleMap_old.Field[] field : fields) {
            for (SimpleMap_old.Field aField : field) {
                if (!aField.selected) {
                    int posX = aField.x * blockSize;
                    int posY = aField.y * blockSize;
                    g2.fillRect(posX + 1, posY + 1, blockSize - 2, blockSize - 2);
                }
            }
        }

        g2.setColor(Color.GREEN);
        for (SimpleMap_old.Field[] field : fields) {
            for (SimpleMap_old.Field aField : field) {
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
        if (x1 < x2) {
            drawLine(x1, y1, x2, y2);
        }
        else {
            drawLine(x2, y2, x1, y1);
        }
        //drawLinePixelByPixel(Math.min(x2, x1), y1, Math.max(x2, x1), y2);
    }

    private void rayCast(int x1, int y1, int x2, int y2) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                fields[i][j].selected = true;
            }
        }
    }
    private void drawLine(int x1, int y1, int x2, int y2) {
        int d = 0;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int dx2 = dx << 1; // slope scaling factors to
        int dy2 = dy << 1; // avoid floating point

        final int ix = x1 < x2 ? 1 : -1; // increment direction
        final int iy = y1 < y2 ? 1 : -1;

        int x = x1;
        int y = y1;

        if (dx >= dy) {
            while (true) {
                fields[(int)Math.floor(x / blockSize)][(int)Math.floor(y / blockSize)].selected = true;
                if (x == x2) {
                    break;
                }
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
                fields[(int)Math.floor(x / blockSize)][(int)Math.floor(y / blockSize)].selected = true;
                if (y == y2) {
                    break;
                }
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
        }
    }
    private void drawLine2(int x1, int y1, int x2, int y2){
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
}
