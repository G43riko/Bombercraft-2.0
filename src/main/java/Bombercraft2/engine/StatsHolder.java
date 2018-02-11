package Bombercraft2.engine;

import java.util.HashMap;
import java.util.Map;

public class StatsHolder {
    private Map<String, Object> data = new HashMap<>();


    public void register(String key) {
        register(key, 0);
    }

    public void register(String key, Object value) {
        if (data.get(key) != null) {
            throw new Error("Cannot register existing variable");
        }

        data.put(key, value);
    }

    private double parseDouble(Object value) {
        try {
            return Double.parseDouble(value.toString());
        }
        catch (NumberFormatException e) {
            throw new Error("Value " + value + " is not number");
        }
    }

    private Object get(String key) {
        Object value = data.get(key);
        if (value == null) {
            throw new Error("Cannot find key: " + key);
        }
        return value;
    }

    private Object set(String key, Object value) {
        data.put(key, value);
        return value;
    }

    public Object increase(String key) {
        return set(key, parseDouble(get(key)) + 1);
    }

    public Object decrease(String key) {
        return set(key, parseDouble(get(key)) - 1);
    }

    @SuppressWarnings("unchecked")
    public <T extends Number> T add(String key, T value) {
        return (T) set(key, parseDouble(get(key)) + value.doubleValue());
    }
}
