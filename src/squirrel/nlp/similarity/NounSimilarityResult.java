package squirrel.nlp.similarity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides thread-safe access of a noun and a list of its similar nouns.
 * 
 * @author victor
 *
 */
public class NounSimilarityResult {
	String noun;
	List<Score> scoreList = new ArrayList<Score>();
	
	public NounSimilarityResult(String noun) {
		this.noun = noun;
	}
	
	public synchronized void add(String word, Double score) {
		scoreList.add(new Score(word, score));
	}
	
	public Score getTopScore() {
		Score top = null;
		Double highest = 0.0;
		for (Score score: scoreList) {
			if (score.getSimilarity() > highest) {
				top = score;
				highest = score.getSimilarity();
			}
		}
		return top;
	}
	
	public String getPrettyString() {
		StringBuilder sb = new StringBuilder();
		for (Score score: scoreList) {
			sb.append(score.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public String toString() {
		return noun + " : " + scoreList;
	}
	
	public static void main(String[] args) {
		NounSimilarityResult rs = new NounSimilarityResult("good");
		rs.add("good", 1.0);
		rs.add("nice", 0.5);
		rs.add("excellent", 0.8);
		rs.add("bad", 0.2);
		System.out.println(rs.getTopScore());
	}
	
	/*
	 * Use red-black tree to sort map
	 *
	public synchronized Map<String, Double> sort() {
		MapValueComparator<String> comp = new MapValueComparator<String>(map);
		TreeMap<String, Double> treeMap = new TreeMap<String, Double>(comp);
		treeMap.putAll(map);
		return treeMap;
	}*/
}