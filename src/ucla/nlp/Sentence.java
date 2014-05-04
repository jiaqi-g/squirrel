package ucla.nlp;

import java.util.HashSet;
import java.util.Set;

import ucla.tripadvisor.QueryUtil;

public class Sentence {
	Integer reviewId;
	Integer sentenceId;
	Set<NP>	nps = new HashSet<NP>();

	public Sentence(int reviewId, int sentenceId) {
		this.reviewId = reviewId;
		this.sentenceId = sentenceId;
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
	 * Core function: we return boolean value for simplicity currently.
	 * @param noun
	 * @param adj
	 * @return
	 */
	public boolean calculateScore(String noun, String adj) {
		for (NP np: nps) {
			if (np.contains(noun, adj)) {
				return true;
			}
		}
		
		return false;
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