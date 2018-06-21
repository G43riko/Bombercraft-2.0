package org.playGround.Misc;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.playGround.Misc.bots.SimpleMyPlayer;
import org.playGround.Misc.bots.SimplePlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerManager extends AbstractManager {
    @NotNull
    private final SimpleGameAble parent;

    @NotNull
    private List<SimplePlayer> players = new ArrayList<>();
    @Nullable
    private SimpleMyPlayer     myPlayer;

    private int renderedPlayers = 0;

    public PlayerManager(@NotNull SimpleGameAble parent) {
        this(parent, null);
    }

    public PlayerManager(@NotNull SimpleGameAble parent, @Nullable SimpleMyPlayer myPlayer) {
        setMyPlayer(myPlayer);
        this.parent = parent;
    }

    public void addPlayer(SimplePlayer player) {
        players.add(player);
    }

    @Nullable
    public SimpleMyPlayer getMyPlayer() {
        return myPlayer;
    }

    public void setMyPlayer(@Nullable SimpleMyPlayer myPlayer) {
        this.myPlayer = myPlayer;
    }

    @Override
    @NotNull
    public List<String> getLogInfo() {
        List<String> result = new ArrayList<>();
        result.add("Players: " + renderedPlayers + " / " + players.size());
        if (myPlayer != null) {
            result.addAll(myPlayer.getLogInfo());
        }
        return result;
    }

    public void render(@NotNull Graphics2D g2) {
        renderedPlayers = 0;
        players.stream().filter(parent::isVisible).forEach((player) -> {
            player.render(g2);
            renderedPlayers++;
        });
        if (myPlayer != null) {
            myPlayer.render(g2);
        }
    }

    public void update(float delta) {
        if (myPlayer != null) {
            myPlayer.update(delta);
        }
        players.parallelStream().forEach((player) -> {
            player.update(delta);
            player.input();
        });
    }

    public void input() {
        if (myPlayer != null) {
            myPlayer.input();
        }
    }
}
