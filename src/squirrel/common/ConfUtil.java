package squirrel.common;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ConfUtil {
	enum Type {
		FILE, DB, WEB
	}
	
	public static Type adjSource = Type.WEB;
	public static Type nounSource = Type.DB;
	
	private static Field[] fields = Conf.class.getDeclaredFields();
	
	/**
	 * return true is conf set successfully
	 * @param key
	 * @param val
	 * @return
	 * @throws Exception
	 */
	public static boolean setConf(String key, String val) throws Exception {
		boolean success = false;
		for (Field f: fields) {
			f.setAccessible(true);
			if (f.getName().equals(key)) {
				Class<?> fieldClass = f.getType();
				if (fieldClass == Boolean.class) {
					f.set(null, Boolean.parseBoolean(val));
				} else if (fieldClass == Integer.class) {
					f.set(null, Integer.parseInt(val));
				} else if (fieldClass == Double.class) {
					f.set(null, Double.parseDouble(val));
				} else if (fieldClass == String.class) {
					f.set(null, val);
				}
				success = true;
			}
		}
		return success;
	}
	
	public static void loadConf() throws Exception {
		Path path = Paths.get("config");
		try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())) {
			while (scanner.hasNextLine()){
				String[] strarr = scanner.nextLine().split("=");
				String key = strarr[0].trim();
				String val = strarr[1].trim();
				
				setConf(key, val);
			}
		}
	}
	
	public static void printArgs() throws Exception {
		System.out.println("[Conf Args]");
		for (Field f: fields) {
			System.out.println(f.getName() + ": " + f.get(null));
		}
	}
	
	public static void main(String[] args) throws Exception {
		ConfUtil.loadConf();
		ConfUtil.printArgs();
	}
}
