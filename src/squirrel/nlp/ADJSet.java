package squirrel.nlp;

import java.util.HashSet;
import java.util.Set;

/**
 * ADJSet is a set of adjectives
 * @author Victor
 *
 */
public class ADJSet {
	Set<String> adjs = new HashSet<String>();

	public ADJSet() {
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

	public String toString() {
		return adjs.toString();
	}
}