package utils;

import java.io.PrintStream;

public class GLogger {
	private static PrintStream stream = new PrintStream(System.out) {


        @Override
        public void println(String s) {
            println((Object) s);
        }

        @Override
        public void println(Object o) {
            StackTraceElement e = Thread.currentThread().getStackTrace()[3];
            String callSite = e == null ? "??" :
                String.format("%s.%s(%s:%d)",
                              e.getClassName(),
                              e.getMethodName(),
                              e.getFileName(),
                              e.getLineNumber());
            super.println(o + "\t-------->at " + callSite);
        }
    };
	public static void printLint(Object content){
		stream.println(content);
	}
}
