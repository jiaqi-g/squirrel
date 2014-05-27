package squirrel.common;

import squirrel.cli.CliArgNumException;

/**
 * Class for log management
 * @author victor
 *
 */
public class Log {

	public static void log(String s) {
		if (Conf.debug) {
			try {
				throw new RuntimeException();
			}
			catch (Exception e) {
				StackTraceElement[] traces = e.getStackTrace();
				String[] callerClassName = traces[1].getClassName().split("\\.");
				//String callerMethodName = traces[1].getMethodName();
				System.out.println("[" + callerClassName[callerClassName.length-1] + "] " + s);
			}
		}
	}

	public static void warn(String s) {
		try {
			throw new RuntimeException();
		}
		catch (Exception e) {
			StackTraceElement[] traces = e.getStackTrace();
			String[] callerClassName = traces[1].getClassName().split("\\.");
			//String callerMethodName = traces[1].getMethodName();
			System.out.println("[" + callerClassName[callerClassName.length-1] + "] " + s);
		}
	}
	
	public static void cliArgError() {
		throw new CliArgNumException();
		//System.exit(0);
	}
}