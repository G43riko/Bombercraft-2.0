package Bombercraft2.playGround.Misc.particles;

import org.jetbrains.annotations.NotNull;
import utils.math.GVector2f;

import java.awt.*;

public class ParticleInstanceData {
    @NotNull
    public final Color     color;
    @NotNull
    public final GVector2f direction;
    @NotNull
    public final GVector2f size;
    @NotNull
    public final GVector2f position;
    public final int       health;

    public ParticleInstanceData(@NotNull GVector2f position,
                                @NotNull Color color,
                                @NotNull GVector2f direction,
                                @NotNull GVector2f size,
                                int health
                               ) {
        this.direction = direction;
        this.position = position;
        this.health = health;
        this.color = color;
        this.size = size;
    }
}
