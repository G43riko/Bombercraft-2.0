package org.playGround.Misc.particles;

import org.glib2.interfaces.InteractAbleG2;
import org.jetbrains.annotations.NotNull;
import org.playGround.Misc.SimpleGameAble;
import utils.math.GVector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ParticleManager implements InteractAbleG2 {
    @NotNull
    private SimpleGameAble      parent;
    private List<SimpleEmitter> emitters = new ArrayList<>();

    public ParticleManager(@NotNull SimpleGameAble parent) {
        this.parent = parent;
    }

    public void createEmitter(@NotNull GVector2f position, @NotNull ParticleEmitterData.Type type) {
        emitters.add(new SimpleEmitter(position, parent, type));
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        emitters.forEach(emitter -> emitter.render(g2));
    }

    @Override
    public void update(float delta) {
        emitters.forEach(emitter -> emitter.update(delta));
    }
}
