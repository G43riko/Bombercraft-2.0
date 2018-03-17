package Bombercraft2.playGround.Misc;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Visible;
import Bombercraft2.engine.Input;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.Utils;
import utils.math.GVector2f;

public class ViewManager extends AbstractManager {
    @Nullable
    private Visible target;

    private       float     zoom       = Config.DEFAULT_ZOOM;
    @NotNull
    private final GVector2f offset     = new GVector2f();
    @NotNull
    private final GVector2f canvasSize = new GVector2f();
    @NotNull
    private final GVector2f maxOffset  = new GVector2f();
    @NotNull
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

    public void setTarget(Visible target) {
        this.target = target;
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

    @Contract(pure = true)
    @NotNull
    public GVector2f getCanvasSize() {
        return canvasSize;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Contract(pure = true)
    public float getSpeed() {
        return speed;
    }

    @Contract(pure = true)
    public GVector2f getMapSize() {
        return mapSize;
    }

    @Contract(pure = true)
    public float getZoom() {
        return zoom;
    }

    @Contract(pure = true)
    @NotNull
    public GVector2f getOffset() {
        return offset;
    }

    public void update(float delta) {
        if (target == null) {
            return;
        }
        GVector2f pos = target.getPosition().mul(zoom).add(Config.BLOCK_SIZE.mul(zoom / 2));

        offset.setX(pos.getX() - canvasSize.getX() / 2);
        offset.setY(pos.getY() - canvasSize.getY() / 2);


        if (offset.getX() < 0) {
            offset.setX(0);
        }

        if (offset.getX() > (mapSize.getX() * zoom) - canvasSize.getX()) {
            offset.setX((mapSize.getX() * zoom) - canvasSize.getX());
        }

        if (offset.getY() < 0) {
            offset.setY(0);
        }

        if (offset.getY() > (mapSize.getY() * zoom) - canvasSize.getY()) {
            offset.setY((mapSize.getY() * zoom) - canvasSize.getY());
        }
    }

    public void input() {
        if (Input.isKeyDown(Input.KEY_Q)) { zoom(speed / 100); }
        if (Input.isKeyDown(Input.KEY_E)) { zoom(-speed / 100); }

        if (target != null) {
            return;
        }

        if (Input.isKeyDown(Input.KEY_A)) { offset.addToX(-speed * zoom); }
        if (Input.isKeyDown(Input.KEY_D)) { offset.addToX(speed * zoom); }
        if (Input.isKeyDown(Input.KEY_S)) { offset.addToY(speed * zoom); }
        if (Input.isKeyDown(Input.KEY_W)) { offset.addToY(-speed * zoom); }


        offset.setY(Utils.clamp(0f, maxOffset.getY(), offset.getY()));
        offset.setX(Utils.clamp(0f, maxOffset.getX(), offset.getX()));
    }
}
