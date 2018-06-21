package org.bombercraft2.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Stack;

public class GameStatesManager {
    private final Stack<GameState> states = new Stack<>();

    @NotNull
    public GameState push(@NotNull GameState state) {
        return states.push(state);
    }

    @Nullable
    public GameState getActive() {
        return states.isEmpty() ? null : states.peek();
    }

    public void render(Graphics2D g2) {
        if (!states.empty()) {
            states.peek().render(g2);
        }
    }

    public void update(float delta) {
        if (!states.empty()) {
            states.peek().update(delta);
        }
    }

    public void input() {
        if (!states.empty()) {
            states.peek().input();
        }
    }

    @Nullable
    public GameState popIfIs(GameStateType... state) {
        if (is(state)) {
            GameState oldState = pop();
            oldState.cleanUp();
            return oldState;
        }
        return null;
    }

    public void onResize() {
        states.forEach(GameState::onResize);
    }

    public boolean is(@NotNull GameStateType... types) {
        if (states.isEmpty()) {
            return false;
        }
        for (GameStateType type : types) {
            if (type == states.peek().getType()) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    public GameState pop() {
        return states.pop();
    }

}
