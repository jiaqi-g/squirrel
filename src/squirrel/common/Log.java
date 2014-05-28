package squirrel.common;

import squirrel.err.CliArgNumException;
import squirrel.err.HotelNotExistException;

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
	
	public static void cliArgError(String msg) {
		throw new CliArgNumException(msg);
		//System.exit(0);
	}
	
	public static void hotelNotExistError(String msg) {
		throw new HotelNotExistException(msg);
		//System.exit(0);
	}
}