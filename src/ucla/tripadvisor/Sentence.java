package ucla.tripadvisor;

import java.util.HashSet;
import java.util.Set;

/**
 * NP, noun phrase, is a noun and set of adjs associated with this noun
 * 
 * @author victor
 */
class NP {
	ADJSet adjs;
	String noun;

	public NP(String noun) {
		this(noun, new ADJSet());
	}

	public NP(String noun, ADJSet adjs) {
		this.noun = noun;
		this.adjs = adjs;
	}

	public boolean contains(String noun, String adj) {
		return containsAdj(adj) && containsNoun(noun);
	}

	public boolean containsAdj(String e) {
		return adjs.contains(e);
	}

	public boolean containsNoun(String e) {
		return noun.startsWith(e);
	}
	
	public String toString() {
		return noun + " : " + adjs.toString(); 
	}
}

/**
 * ADJSet is a set of adjectives
 * @author Victor
 *
 */
class ADJSet {
	Set<String> adjs = new HashSet<String>();

	public ADJSet() {
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

public class Sentence {
	Integer reviewId;
	Integer sentenceId;
	ADJSet adjs = new ADJSet();
	Set<NP>	nps = new HashSet<NP>();

	public Sentence(int reviewId, int sentenceId) {
		this.reviewId = reviewId;
		this.sentenceId = sentenceId;
	}
	
	public void addAdjs(String[] adjs) {
		this.adjs.addAll(adjs);
	}

	public void addNP(NP np) {
		nps.add(np);
	}

	public Integer getReviewId() {
		return reviewId;
	}
	
	public Integer getSentenceId() {
		return sentenceId;
	}
	
	/**
	 * We return boolean value for simplicity currently.
	 * @param noun
	 * @param adj
	 * @return
	 */
	public boolean calculateScore(String noun, String adj) {
		boolean adjContain = false;
		boolean nounContain = false;

		for (NP np: nps) {
			if (np.containsNoun(noun)) {
				nounContain = true;
				if (np.containsAdj(adj)) {
					adjContain = true;
				}
			}
		}

		if (nounContain) {
			if (adjs.contains(adj)) {
				adjContain = true;
			}
		}

		return adjContain && nounContain;
	}
	
	public String getSentenceText() {
		return QueryUtil.lookupReview(reviewId);
	}

	public String getReviewText() {
		return QueryUtil.lookupSentence(reviewId, sentenceId);
	}
	
	public String toString() {
		return reviewId + ": " + sentenceId;
		//return nps + " | " + adjs + " | " + reviewId + " | " + sentenceId;
	}
}

