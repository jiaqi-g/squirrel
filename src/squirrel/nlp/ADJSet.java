package squirrel.nlp;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import squirrel.nlp.similarity.WordSimilarityResultList;
import squirrel.nlp.similarity.WordSimilarityScore;

/**
 * ADJSet is a set of adjectives
 * @author Victor
 *
 */
public class ADJSet implements Iterable<String> {
	Set<String> adjs = new HashSet<String>();

	public ADJSet() {
	}

	public ADJSet(WordSimilarityResultList adjSynonyms) {
		for (WordSimilarityScore score: adjSynonyms) {
			adjs.add(score.getWord());
		}
	}
	
	/**
	 * a list of adjs seperated by ","
	 * @param adjList
	 */
	public ADJSet(String adjListString) {
		String[] adjs = adjListString.split(",");
		addAll(adjs);
	}
	
	public ADJSet(String[] adjs) {
		addAll(adjs);
	}
	
	public ADJSet(Set<String> adjs) {
		this.adjs = adjs;
	}
	
	public ADJSet(List<String> adjs) {
		adjs.addAll(adjs);
	}

	public void addAll(String[] adjs) {
		for (String adj: adjs) {
			this.adjs.add(adj.trim());
		}
	}

	public void add(String e) {
		adjs.add(e);
	}

	public boolean contains(String e) {
		return adjs.contains(e);
	}
	
	private boolean subContains(String e) {
		for (String adj: adjs) {
			if (e.contains(adj)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean belongToAdjSet(ADJSet queryAdjs) {
		for (String adj: adjs) {
			if (queryAdjs.subContains(adj)) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return adjs.toString();
	}

	@Override
	public Iterator<String> iterator() {
		return adjs.iterator();
	}
}