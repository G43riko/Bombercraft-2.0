package org.play_ground.misc.particles;

import org.jetbrains.annotations.NotNull;
import utils.math.BVector2f;

import java.awt.*;

public class ParticleInstanceData {
    @NotNull
    public final Color     color;
    @NotNull
    public final BVector2f direction;
    @NotNull
    public final BVector2f size;
    @NotNull
    public final BVector2f position;
    public final int       health;

    public ParticleInstanceData(@NotNull BVector2f position,
                                @NotNull Color color,
                                @NotNull BVector2f direction,
                                @NotNull BVector2f size,
                                int health
                               ) {
        this.direction = direction;
        this.position = position;
        this.health = health;
        this.color = color;
        this.size = size;
    }
}
