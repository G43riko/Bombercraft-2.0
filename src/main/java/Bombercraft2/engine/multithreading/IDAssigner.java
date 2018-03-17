package Bombercraft2.engine.multithreading;

public class IDAssigner {
    private int baseID;

    public IDAssigner(int baseID) {
        this.baseID = baseID;
    }

    public int next() {
        return baseID++;
    }

    public int current() {
        return baseID;
    }
}
