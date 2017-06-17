package utils;

import java.io.OutputStream;
import java.io.PrintStream;

public class GLogger extends PrintStream{
	private static GLogger instanceLog = new GLogger(System.out);
	private static GLogger instanceErr = new GLogger(System.err);
	
	private GLogger(OutputStream stream) {super(stream);}
	
	private String prepareString(){
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
    public void println(Object o) {;super.println(o + prepareString());}
    
    public static void notImplemented(){
    	instanceErr.println("neimplementované");
    }
	public static void printLine(Object content){
		instanceLog.println(content);
	}
}
