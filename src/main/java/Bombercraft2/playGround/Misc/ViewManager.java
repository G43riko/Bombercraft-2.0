package Bombercraft2.playGround.Misc;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.engine.Input;
import org.jetbrains.annotations.NotNull;
import utils.Utils;
import utils.math.GVector2f;

public class ViewManager {
    private       float     zoom       = Config.DEFAULT_ZOOM;
    private final GVector2f offset     = new GVector2f();
    private final GVector2f canvasSize = new GVector2f();
    private final GVector2f maxOffset  = new GVector2f();
    private final GVector2f mapSize;
    private       float     minZoom;
    private       float     speed;


    public ViewManager(@NotNull GVector2f mapSize, int canvasWidth, int canvasHeight, float speed) {
        this.mapSize = mapSize;
        this.speed = speed;
        setCanvasSize(canvasWidth, canvasHeight);
    }

    public void setCanvasSize(int width, int height) {
        canvasSize.set(width, height);

        calcMinZoom();
    }

    private void calcMinZoom() {
        minZoom = Math.max(canvasSize.getX() / mapSize.getX(),
                           canvasSize.getY() / mapSize.getY());
        minZoom = Math.max(minZoom, Config.MIN_ZOOM);
        checkOffset();
    }

    public void moveX(float value) {
        offset.addToX(value);
        offset.setX(Utils.clamp(0f, maxOffset.getX(), offset.getX()));
    }

    public void moveY(float value) {
        offset.addToY(value);
        offset.setY(Utils.clamp(0f, maxOffset.getY(), offset.getY()));
    }

    private void checkOffset() {
        offset.setX(Utils.clamp(0f, maxOffset.getX(), offset.getX()));
        offset.setY(Utils.clamp(0f, maxOffset.getY(), offset.getY()));

    }

    public void zoom(float value) {
        zoom = Math.max(minZoom, zoom + value);
        maxOffset.set(mapSize.mul(zoom).sub(canvasSize));
        checkOffset();
    }

    public GVector2f getCanvasSize() {
        return canvasSize;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public float getZoom() {
        return zoom;
    }

    public GVector2f getOffset() {
        return offset;
    }

    public void input() {
        if (Input.isKeyDown(Input.KEY_A)) { offset.addToX(-speed * zoom); }
        if (Input.isKeyDown(Input.KEY_D)) { offset.addToX(speed * zoom); }
        if (Input.isKeyDown(Input.KEY_S)) { offset.addToY(speed * zoom); }
        if (Input.isKeyDown(Input.KEY_W)) { offset.addToY(-speed * zoom); }

        if (Input.isKeyDown(Input.KEY_Q)) { zoom(speed / 100); }
        if (Input.isKeyDown(Input.KEY_E)) { zoom(-speed / 100); }


        offset.setY(Utils.clamp(0f, maxOffset.getY(), offset.getY()));
        offset.setX(Utils.clamp(0f, maxOffset.getX(), offset.getX()));
    }
}
