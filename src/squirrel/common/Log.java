package squirrel.common;

/**
 * Class for log management
 * @author victor
 *
 */
public class Log {
	
	public static void log(String moduleName, String s) {
		if (Conf.debug) {
			System.out.println(moduleName + " " + s);
		}
	}
	
	public static void warn(String moduleName, String s) {
		System.out.println(moduleName + " " + s);
	}	
}
