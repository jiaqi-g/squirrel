package squirrel.common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Conf {
	enum Type {
		FILE, DB, WEB
	}
	
	public static boolean debug = false;
	
	public static String db_driver = "com.mysql.jdbc.Driver";
	public static String db_url=""; 
	public static String db_user="";        
	public static String db_password="";
	
	public static Double nounSimilarityThreshold = 0.5;
	public static Double adjSimilarityThreshold = 0.6;
	public static Double sentenceSimilarityThreshold = nounSimilarityThreshold;
	
	public static Type adjSource = Type.WEB;
	public static Type nounSource = Type.DB;
	
	public static Integer hotelId = 93396;
	
	public static void loadConf() throws IOException {
		Path path = Paths.get("config");
		try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())) {
			while (scanner.hasNextLine()){
				String[] strarr = scanner.nextLine().split("=");
				String key = strarr[0].trim();
				String val = strarr[1].trim();
				
				if (key.equals("debug")) {
					debug = Boolean.parseBoolean(val);
				}
				else if (key.equals("url")) {
					db_url = val;
				}
				else if (key.equals("user")) {
					db_user = val;
				}
				else if (key.equals("password")) {
					db_password = val;
				}
				else if (key.equals("hotel_id")) {
					hotelId = Integer.parseInt(val);
				}
				else if (key.equals("nounSimilarityThreshold")) {
					nounSimilarityThreshold = Double.parseDouble(val);
				}
				else if (key.equals("adjSimilarityThreshold")) {
					adjSimilarityThreshold = Double.parseDouble(val);
				}
				else if (key.equals("sentenceSimilarityThreshold")) {
					sentenceSimilarityThreshold = Double.parseDouble(val);
				}
			}
		}
	}
	
	public static void printArgs() {
		System.out.println("[Conf Args]");
		System.out.println("debug " + debug);
		System.out.println("nounSimilarityThreshold " + nounSimilarityThreshold);
		System.out.println("adjSimilarityThreshold " + adjSimilarityThreshold);
		System.out.println("sentenceSimilarityThreshold " + sentenceSimilarityThreshold);
		System.out.println("hotelId " + hotelId);
	}
}
