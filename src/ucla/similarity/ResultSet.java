package ucla.similarity;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import ucla.util.MapValueComparator;

/**
 * This result set provides thread-safe access of a noun and a list of its similar nouns.
 * 
 * @author victor
 *
 */
public class ResultSet {
	String noun;
	HashMap<String, Double> map = new HashMap<String, Double>();
	
	public ResultSet(String noun) {
		this.noun = noun;
	}
	
	public synchronized void add(String word, Double score) {
		map.put(word, score);
	}
	
	/**
	 * Use red-black tree to sort map
	 * @return
	 */
	public synchronized Map<String, Double> sort() {
		MapValueComparator<String> comp = new MapValueComparator<String>(map);
		TreeMap<String, Double> treeMap = new TreeMap<String, Double>(comp);
		treeMap.putAll(map);
		return treeMap;
	}
	
	public Entry<String, Double> getTopScoreEntry() {
		Entry<String, Double> top = null;
		Double highest = 0.0;
		for (Entry<String, Double> entry: map.entrySet()) {
			if (entry.getValue() > highest) {
				top = entry;
				highest = entry.getValue();
			}
		}
		return top;
	}
	
	public String toString() {
		return noun + " : " + map;
	}
	
	public static void main(String[] args) {
		ResultSet rs = new ResultSet("good");
		rs.add("good", 1.0);
		rs.add("nice", 0.5);
		rs.add("excellent", 0.8);
		rs.add("bad", 0.2);
		System.out.println(rs.getTopScoreEntry());
	}
}