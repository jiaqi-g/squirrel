package ucla.nlp;

/**
 * NP, noun phrase, is a noun and adjs associated with this noun
 * 
 * Direct Adjs: a fresh meal, a big bed, a nice staff
 * Indirect Adjs: meal is delicious, food with potatoes tastes good
 * 
 * @author victor
 */
public class NP {
	ADJSet directAdjs;
	ADJSet indirectAdjs;
	String noun;

	public NP(String noun) {
		this(noun, new ADJSet(), new ADJSet());
	}

	public NP(String noun, ADJSet directAdjs, ADJSet indirectAdjs) {
		this.noun = noun;
		this.directAdjs = directAdjs;
		this.indirectAdjs = indirectAdjs;
	}

	public boolean contains(String noun, String adj) {
		return containsNoun(noun) && containsAdj(adj);
	}

	public boolean containsAdj(String e) {
		return directAdjs.contains(e) || indirectAdjs.contains(e);
	}

	public boolean containsNoun(String e) {
		return noun.startsWith(e);
	}
	
	public String toString() {
		return noun + " : " + directAdjs.toString() + " " + indirectAdjs.toString(); 
	}
}