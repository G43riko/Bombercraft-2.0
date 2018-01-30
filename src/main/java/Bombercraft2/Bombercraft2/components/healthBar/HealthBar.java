package Bombercraft2.Bombercraft2.components.healthBar;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.playGround.Misc.SimpleGameAble;
import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public class HealthBar {
    private final HealthAble     target;
    private final SimpleGameAble parent;

    public HealthBar(@NotNull HealthAble target, @NotNull SimpleGameAble parent) {
        this.parent = parent;
        this.target = target;
    }

    public void render(@NotNull Graphics2D g2) {
        GVector2f pos = target.getPosition().mul(parent.getZoom()).sub(parent.getOffset());
        g2.setStroke(new BasicStroke(Config.HEALTH_BAR_BORDER_WIDTH));
        int maxWidth = (int)((target.getSize().getXi() - (Config.HEALTH_BAR_HORIZONTAL_OFFSET << 1)) * parent.getZoom());
        final int height = (int)(Config.HEALTH_BAR_HORIZONTAL_HEIGHT * parent.getZoom());


        g2.setColor(Config.HEALTH_BAR_BORDER_COLOR);
        g2.drawRect(pos.getXi() + Config.HEALTH_BAR_HORIZONTAL_OFFSET,
                    pos.getYi() + Config.HEALTH_BAR_VERTICAL_OFFSET,
                    maxWidth,
                    height);

        g2.setColor(Config.HEALTH_BAR_FILL_COLOR);
        g2.fillRect(pos.getXi() + Config.HEALTH_BAR_HORIZONTAL_OFFSET,
                    pos.getYi() + Config.HEALTH_BAR_VERTICAL_OFFSET,
                    target.getActHealth() * maxWidth / target.getMaxHealth(),
                    height);
    }

}
