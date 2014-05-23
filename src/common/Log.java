package common;

/**
 * Class for log management
 * @author victor
 *
 */
public class Log {
	
	static boolean debug = false;
	
	public static void log(String moduleName, String s) {
		if (debug) {
			System.out.println(moduleName + " " + s);
		}
	}
}
