package squirrel.common;

/**
 * Confs could be read from config file
 * @author victor
 *
 */
public class Conf {
	public static Boolean debug = false;
	public static Boolean record = true;
	
	public static String db_driver = "com.mysql.jdbc.Driver";
	public static String db_url=""; 
	public static String db_user="";        
	public static String db_password="";

	public static Double nounScoreRatio = 0.8;
	public static Double nounSimilarityThreshold = 0.5;
	public static Double adjSimilarityThreshold = 0.6;
	public static Double sentenceSimilarityThreshold = nounSimilarityThreshold;

	public static Integer hotelId = 93396;
}