package squirrel.common;

public class Conf {
	enum Type {
		FILE, DB, WEB
	}
	
	public static boolean debug = false;
	
	public static final Double nounSimilarityThreshold = 0.5;
	public static final Double adjSimilarityThreshold = 0.6;
	public static final Double sentenceSimilarityThreshold = nounSimilarityThreshold;
	
	public static final Type adjSource = Type.WEB;
	public static final Type nounSource = Type.DB;
}
