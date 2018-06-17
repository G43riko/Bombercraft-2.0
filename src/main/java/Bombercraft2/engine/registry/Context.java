package Bombercraft2.engine.registry;

public interface Context {
    <T> T get(Class<? extends T> type);
    <T, U extends T> void put(Class<T> type, U object);
}
