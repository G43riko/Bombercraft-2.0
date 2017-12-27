package Bombercraft2.Bombercraft2.core;

import java.awt.*;
import java.util.Stack;

public class GameStatesManager {
    private final Stack<GameState> states = new Stack<>();

    public GameState push(GameState state) {
        return states.push(state);
    }

    public void render(Graphics2D g2) {
        if(!states.empty()) {
            states.peek().render(g2);
        }
    }
    public void update(float delta) {
        if(!states.empty()) {
            states.peek().update(delta);
        }
    }

    public GameState pop() {
        return states.pop();
    }

    public void input() {
        if(!states.empty()) {
            states.peek().input();
        }
    }
}
