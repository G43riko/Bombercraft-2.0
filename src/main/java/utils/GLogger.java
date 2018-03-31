package utils;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GLogger extends PrintStream {
    public enum GLog {
        LEVEL_SUCCESSFULLY_CREATED,
        MAP_SUCCESSFULLY_CREATED,
        PROFILE_SUCCESSFULLY_LOADED,
        PROFILE_SUCCESSFULLY_SAVED,
        FILE_READ,
        CORE_GAME_CREATED,
        TEXTURE_LOAD,
        SERVER_CREATED,
        CLIENT_CONNECTED_TO_SERVER,
        SUCCESSFULLY_CONNECTED_TO_THE_SERVER,
        CLIENT_PLAYER_SUCCESSFULLY_INITIALIZED,
        CLIENT_PLAYER_CLEANED,
        LEVEL_SUCCESSFULLY_PARSED,
        GAME_CREATED
    }
    public enum GError {
        LEVEL_CREATION_FAILED,
        MAP_CREATION_FAILED,
        MAP_PARSING_FAILED,
        MAP_SERIALIZATION_FAILED,
        MAP_RESET_FAILED,
        UNKNOWN_BLOCK_TYPE,
        PROFILE_PARSING_FAILED,
        PROFILE_SAVING_FAILED,
        CANNOT_READ_JSON,
        CANNOT_SCAN_LOCALHOST,
        UNKNOWN_LOCALHOST,
        CANNON_READ_FILE,
        CREATE_CORE_GAME_FAILED,
        CANNON_READ_TEXTURE,
        CANNON_WRITE_FILE,
        CANNON_CREATE_FILE_WRITER,
        CANNOT_PARSE_JSON_FILE,
        CANNOT_CREATE_SOCKET,
        CANNOT_CREATE_SERVER,
        SERVER_CANNOT_ACCEPT_CONNECTION,
        CLOSE_SERVER_FAILED,
        CLIENT_CANNOT_CONNECT_TO_SERVER,
        NOT_IMPLEMENTED,
        NOT_SET,
        CANNOT_CLEAN_CLIENT,
        CANNOT_SEND_MESSAGE,
        CANNOT_PARSE_MESSAGE,
        CANNOT_INITIAL_CLIENT_PLAYER,
        CANNOT_CLEAN_CLIENT_PLAYER,
        CANNOT_PARSE_LEVEL,
        CANNOT_SERIALIZE_PLAYER_INFO,
        CANNOT_GET_WEAPON_BY_TYPE,
        CANNOT_ADD_PLAYER,
        CANNOT_SERIALIZE_GAME_INFO,
        CANNOT_SERIALIZE_BASIC_INFO,
        CANNOT_ADD_PLAYERS,
        CANNOT_PARSE_BLOCK,
        CANNOT_SERIALIZE_BLOCK,
        CANNOT_SERIALIZE_LEVEL,
        CANNOT_SET_DEFAULT_PLAYER_INFO,
        // CANNOT_CREATE_MAP,
        CANNOT_LOAD_MAP,
        CANNOT_SERIALIZE_SCENE_MANAGER,
        CANNOT_LOAD_IMAGE, CREATE_GAME_FAILED
    }
    private static class Wrapper {
        Exception exception;
        GError    error;
        String[]  params;
        GLog      log;
        long created = System.nanoTime();
    }
    private static final GLogger instanceLog = new GLogger(System.out);
    private static final GLogger instanceErr = new GLogger(System.err);

    public static void makeError(GError GError) {
        throw getError(GError);
    }

    private static Error getError(GError GError) {
        return new Error(GError.toString());
    }
    private static final List<Wrapper> logs = new LinkedList<>();
    private GLogger(OutputStream stream) {super(stream);}

    public static void log(GLog log, String ... params) {
        Wrapper wrapper = new Wrapper();
        wrapper.log = log;
        wrapper.params = params;
        System.out.println(log + " " + String.join(" ", params));
        logs.add(wrapper);
    }

    public static void error(GError error, String ... params) {
        Wrapper wrapper = new Wrapper();
        wrapper.error = error;
        wrapper.params = params;
        System.err.println(error + " " + String.join(" ", params));
        logs.add(wrapper);
    }
    public static void error(GError error, Exception exception, String ... params) {
        Wrapper wrapper = new Wrapper();
        wrapper.error = error;
        wrapper.exception = exception;
        wrapper.params = params;
        System.err.println(error + " " + String.join(" ", params));
        logs.add(wrapper);
    }

    public static List<Wrapper> getLogs() {
        return logs.stream().filter((a) -> a.error == null).collect(Collectors.toList());
    }
    public static List<Wrapper> getErrors() {
        return logs.stream().filter((a) -> a.log == null).collect(Collectors.toList());
    }

    private String prepareString() {
        StackTraceElement e = Thread.currentThread().getStackTrace()[4];
        String callSite = e == null ? "??" :
                String.format("%s.%s(%s:%d)",
                              e.getClassName(),
                              e.getMethodName(),
                              e.getFileName(),
                              e.getLineNumber());

        return "\t-------->at " + callSite;
    }

    @Override
    public void println(String s) {super.println(s + prepareString());}

    public void println(Object o) {super.println(o + prepareString());}

    public static void notImplemented() {
        instanceErr.println(GError.NOT_IMPLEMENTED);
    }
    public static void notSet(String item) { instanceErr.println(GError.NOT_SET + ": " + item); }

    public static void printLine(Object content) {
        instanceLog.println(content);
    }
}
