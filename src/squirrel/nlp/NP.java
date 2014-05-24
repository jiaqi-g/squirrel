package squirrel.nlp;

/**
 * NP, noun pair, is a noun and adjs associated with this noun
 * TODO: Each adj is associated with a num indicates the relativeness between it and the noun.
 * 
 * @author victor
 */
public class NP {
	ADJSet adjSet;
	String noun;

	public NP(String noun, ADJSet adjSet) {
		this.noun = noun;
		this.adjSet = adjSet;
	}
	
	public NP(String noun, String adjListString) {
		this.noun = noun;
		this.adjSet = new ADJSet(adjListString);
	}

	/*
	public boolean contains(String noun, String adj) {
		return containsNoun(noun) && containsAdj(adj);
	}*/

	public boolean containsAdj(String e) {
		return adjSet.contains(e);
	}
	
	public boolean belongToAdjSet(ADJSet queryAdjs) {
		for (String adj: adjSet) {
			if (queryAdjs.subContains(adj)) {
				return true;
			}
		}
		return false;
	}

	public boolean containsNoun(String e) {
		return noun.startsWith(e);
	}
	
	public String toString() {
		return noun + ": " + adjSet.toString(); 
	}
}