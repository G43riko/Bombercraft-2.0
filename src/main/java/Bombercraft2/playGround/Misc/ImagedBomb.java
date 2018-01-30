package Bombercraft2.playGround.Misc;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.game.Timer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.math.GVector2f;
import utils.resouces.ResourceLoader;

import java.awt.*;

public class ImagedBomb extends SimpleBomb {
    //TODO: Obrázky prerenderovať do defaultného rozlíšenia
    private final static Image bombImage   = ResourceLoader.loadTexture("bomb" + Config.EXTENSION_IMAGE);
    private final static Image craterImage = ResourceLoader.loadTexture("crater" + Config.EXTENSION_IMAGE);

    @Nullable
    private final Timer timer;

    public ImagedBomb(@NotNull GVector2f position,
                      @NotNull SimpleGameAble parent
                     ) {
        this(position, parent, System.currentTimeMillis());
    }

    public ImagedBomb(@NotNull GVector2f position,
                      @NotNull SimpleGameAble parent,
                      long addedAt
                     ) {

        super(position, parent, addedAt);
        timer = new Timer(this, addedAt, detonationTime);
    }


    @Override
    public void render(@NotNull Graphics2D g2) {
        if (!alive) {
            return;
        }
        GVector2f actPos = position.sub(getParent().getOffset());

        if (timer != null) {
            timer.render(g2);
        }
        g2.setColor(Color.BLACK);
        // g2.fillArc(actPos.getXi(), actPos.getYi(), Config.BOMB_WIDTH, Config.BOMB_HEIGHT, 0, 360);
        g2.drawImage(bombImage, actPos.getXi(), actPos.getYi(), Config.BOMB_WIDTH, Config.BOMB_HEIGHT, null);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (timer != null) {
            timer.update(delta);
        }
    }

    @Contract(pure = true)
    @NotNull
    public Image getCrater() {
        return craterImage;
    }
}
