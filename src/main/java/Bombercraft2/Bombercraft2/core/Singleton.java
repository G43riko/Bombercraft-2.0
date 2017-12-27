package Bombercraft2.Bombercraft2.core;

public class Singleton {
    private static Singleton instance;
    protected Singleton() {}
    public static Singleton get() {
        if(instance == null) {
            synchronized(Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
