package org.engine.registry;

public class CoreRegistry {
    private static Context context;

    private CoreRegistry() {
    }

    public static <T, U extends T> U put(Class<T> type, U object) {
        if (context == null) {
            return null;
        }
        context.put(type, object);
        return object;
    }

    public static void setContext(Context context) {
        if (System.getSecurityManager() != null) {
            System.getSecurityManager().checkPermission(new RuntimePermission("permRegister"));
        }
        CoreRegistry.context = context;
    }

    public static <T> T get(Class<T> type) {
        if (context == null) {
            return null;
        }
        if (type == Context.class) {
            return type.cast(context);
        }
        return context.get(type);
    }
}
