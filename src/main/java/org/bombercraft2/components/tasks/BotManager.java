package org.bombercraft2.components.tasks;

import org.bombercraft2.game.bots.Bot;
import org.glib2.interfaces.InteractAbleG2;
import org.glib2.math.vectors.GVector2f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.play_ground.misc.AbstractManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BotManager extends AbstractManager implements InteractAbleG2 {
    private final List<Bot> bots = new ArrayList<>();

    public List<Bot> getFreeBots(int count) {
        return bots.stream().filter(Bot::isFree).limit(count).collect(Collectors.toList());
    }

    @Nullable
    public Bot getFreeBot() {
        return bots.stream().filter(Bot::isFree).findFirst().orElse(null);
    }

    @Nullable
    public Bot getFreeBotNearestTo(@NotNull GVector2f position) {
        return bots.stream()
                   .filter(Bot::isFree)
                   .min(Comparator.comparing(a -> a.getPosition().dist(position))).orElse(null);
    }

    public List<Bot> getFreeBotsNearestTo(int count, @NotNull GVector2f position) {
        return bots.stream()
                   .filter(Bot::isFree)
                   .sorted(Comparator.comparing(a -> a.getPosition().dist(position)))
                   .limit(count)
                   .collect(Collectors.toList());
    }

    public void addBot(@NotNull Bot bot) {
        bots.add(bot);
    }

    @Override
    public void update(float delta) {
        bots.forEach(bot -> bot.update(delta));
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        bots.forEach(bot -> bot.render(g2));
    }
}
