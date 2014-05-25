package squirrel.nlp.similarity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class WordSimilarityResultList implements Iterable<WordSimilarityScore> {
	String word;
	List<WordSimilarityScore> scoreList = new ArrayList<WordSimilarityScore>();
	
	public WordSimilarityResultList(String word) {
		this.word = word;
	}
	
	public void add(String word, Double score) {
		scoreList.add(new WordSimilarityScore(word, score));
	}
	
	private void sort() {
		Collections.sort(scoreList);
	}
	
	/**
	 * Since the number of words in the scoreList is not too much, we just perform iterations
	 * @param word
	 * @return
	 */
	public Double getScore(String word) {
		for (WordSimilarityScore score: scoreList) {
			if (score.getWord().equals(word)) {
				return score.getSimilarity();
			}
		}
		return 0.0;
	}
	
	public void filterWordsBelowScore(Double threshold) {
		//TODO: needs to be tested
		sort();
		int index;
		for (index=0; index<scoreList.size(); index++) {
			if (scoreList.get(index).getSimilarity() < threshold) {
				break;
			}
		}
		scoreList = scoreList.subList(0, index);
	}
	
	public Set<String> getWordsSet() {
		Set<String> res = new HashSet<String>();
		for (WordSimilarityScore score: scoreList) {
			res.add(score.getWord());
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
		StringBuilder sb = new StringBuilder();
		for (WordSimilarityScore score: scoreList) {
			sb.append(score + "\n");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		WordSimilarityResultList rs = new WordSimilarityResultList("good");
		rs.add("good", 1.0);
		rs.add("nice", 0.5);
		rs.add("excellent", 0.8);
		rs.add("bad", 0.2);
		System.out.println(rs.getTopScore());
	}

	@Override
	public Iterator<WordSimilarityScore> iterator() {
		return scoreList.iterator();
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