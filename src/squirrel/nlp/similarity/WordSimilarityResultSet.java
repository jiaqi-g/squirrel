package squirrel.nlp.similarity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class provides thread-safe access of a noun and a list of its similar nouns.
 * 
 * @author victor
 *
 */
public class WordSimilarityResultSet {
	String word;
	List<WordSimilarityScore> scoreList = new ArrayList<WordSimilarityScore>();
	
	public WordSimilarityResultSet(String word) {
		this.word = word;
	}
	
	public synchronized void add(String word, Double score) {
		scoreList.add(new WordSimilarityScore(word, score));
	}
	
	public void sort() {
		Collections.sort(scoreList);
	}
	
	//TODO: make faster
	public Double getScore(String word) {
		for (WordSimilarityScore score: scoreList) {
			if (score.getWord().equals(word)) {
				return score.getSimilarity();
			}
		}
		return 0.0;
	}
	
	public List<String> getWordsAboveScore(Double threshold) {
		List<String> res = new ArrayList<String>();
		
		for (WordSimilarityScore score: scoreList) {
			if (score.getSimilarity() > threshold) {
				res.add(score.getWord());
			}
		}
		
		return res;
	}
	
	public WordSimilarityScore getTopScore() {
		WordSimilarityScore top = null;
		Double highest = 0.0;
		for (WordSimilarityScore score: scoreList) {
			if (score.getSimilarity() > highest) {
				top = score;
				highest = score.getSimilarity();
			}
		}
		return top;
	}
	
	public String getPrettyString() {
		StringBuilder sb = new StringBuilder();
		for (WordSimilarityScore score: scoreList) {
			sb.append(score.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public String toString() {
		return word + " : " + scoreList;
	}
	
	public static void main(String[] args) {
		WordSimilarityResultSet rs = new WordSimilarityResultSet("good");
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