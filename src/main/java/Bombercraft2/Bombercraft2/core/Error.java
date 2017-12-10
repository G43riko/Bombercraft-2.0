package Bombercraft2.Bombercraft2.core;

public enum Error {
    CANNOT_READ_JSON;

    public static void makeError(Error error) {
        throw getError(error);
    }
    public static java.lang.Error getError(Error error) {
        return new java.lang.Error(Error.CANNOT_READ_JSON.toString());
    }
}
