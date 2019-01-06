package org.play_ground.misc;

import com.sun.javafx.util.Utils;
import org.bombercraft2.StaticConfig;
import org.engine.Input;
import org.glib2.interfaces.Visible;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.utils.enums.Keys;

import java.util.ArrayList;
import java.util.List;

public class ViewManager extends AbstractManager {

    @NotNull
    private final GVector2f offset     = new GVector2f();
    @NotNull
    private final GVector2f canvasSize = new GVector2f();
    @NotNull
    private final GVector2f maxOffset  = new GVector2f();
    @NotNull
    private final GVector2f mapSize;
    @Nullable
    private       Visible   target;
    private       float     zoom       = StaticConfig.DEFAULT_ZOOM;
    private       float     minZoom;
    private       float     speed;

    public ViewManager(@NotNull GVector2f mapSize, int canvasWidth, int canvasHeight, float speed) {
        this(null, mapSize, canvasWidth, canvasHeight, speed);
    }

    public ViewManager(@Nullable Visible target,
                       @NotNull GVector2f mapSize,
                       int canvasWidth,
                       int canvasHeight,
                       float speed
                      ) {
        this.mapSize = mapSize;
        this.speed = speed;
        this.target = target;
        setCanvasSize(canvasWidth, canvasHeight);
    }

    @NotNull
    public GVector2f transform(@NotNull GVector2f position) {
        return position.getMul(zoom).getSub(offset);
    }

    @NotNull
    public GVector2f transformInvert(@NotNull GVector2f position) {
        return position.getAdd(offset).getDiv(zoom);
    }

    public void setCanvasSize(int width, int height) {
        canvasSize.set(width, height);

        calcMinZoom();
    }

    private void calcMinZoom() {
        minZoom = Math.max(canvasSize.getX() / mapSize.getX(),
                           canvasSize.getY() / mapSize.getY());
        minZoom = Math.max(minZoom, StaticConfig.MIN_ZOOM);
        checkOffset();
    }

    public void setTarget(Visible target) {
        this.target = target;
    }

    public void moveX(float value) {
        offset.setX(Utils.clamp(0f, maxOffset.getX(), offset.getX() + value));
    }

    public void moveY(float value) {
        offset.setY(Utils.clamp(0f, maxOffset.getY(), offset.getY() + value));
    }

    private void checkOffset() {
        offset.setX(Utils.clamp(0f, maxOffset.getX(), offset.getX()));
        offset.setY(Utils.clamp(0f, maxOffset.getY(), offset.getY()));

    }

    public void zoom(float value) {
        zoom = Math.max(minZoom, zoom + value);
        maxOffset.set(mapSize.getMul(zoom).getSub(canvasSize));
        checkOffset();
    }

    @Contract(pure = true)
    @NotNull
    public GVector2f getCanvasSize() {
        return canvasSize;
    }

    @NotNull
    @Override
    public List<String> getLogInfo() {
        List<String> result = new ArrayList<>();
        result.add("zoom: " + zoom);
        result.add("offset: " + offset.toDecimal(3));
        result.add("canvas size: " + canvasSize.toDecimal(3));
        return result;
    }

    @Contract(pure = true)
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
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
        GVector2f pos = target.getPosition().getMul(zoom).getAdd(StaticConfig.BLOCK_SIZE.getMul(zoom / 2));

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
        if (Input.isKeyDown(Keys.Q)) { zoom(speed / 100); }
        if (Input.isKeyDown(Keys.E)) { zoom(-speed / 100); }

        if (target != null) {
            return;
        }

        if (Input.isKeyDown(Keys.A)) { offset.addToX(-speed * zoom); }
        if (Input.isKeyDown(Keys.D)) { offset.addToX(speed * zoom); }
        if (Input.isKeyDown(Keys.S)) { offset.addToY(speed * zoom); }
        if (Input.isKeyDown(Keys.W)) { offset.addToY(-speed * zoom); }


        offset.setY(Utils.clamp(0f, maxOffset.getY(), offset.getY()));
        offset.setX(Utils.clamp(0f, maxOffset.getX(), offset.getX()));
    }
}
