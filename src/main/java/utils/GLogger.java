package utils;



import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GLogger extends PrintStream {
    public enum GLog {
        LEVEL_CREATED,
        MAP_CREATED,
        PROFILE_LOADED,
        FILE_READ,
        CORE_GAME_CREATED,
        TEXTURE_LOAD, GAME_CREATED
    }
    public enum GError {
        CANNOT_READ_JSON,
        CREATE_LEVEL_FAILED,
        CREATE_MAP_FAILED,
        CANNON_READ_FILE,
        CANNON_PARSE_PROFILE,
        CREATE_CORE_GAME_FAILED,
        CANNON_READ_TEXTURE, CANNON_WRITE_FILE, CANNON_CREATE_FILEWRITTER, CANNOT_PARSE_JSON_FILE, CREATE_GAME_FAILED
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
    // private static Map<Long, Wrapper> logs = new HashMap<>();
    private static List<Wrapper> logs = new LinkedList<>();
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
        instanceErr.println("not implemented");
    }

    public static void printLine(Object content) {
        instanceLog.println(content);
    }
}
