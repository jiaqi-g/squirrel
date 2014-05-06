package ucla.nlp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import ucla.similarity.ResultSet;
import ucla.tripadvisor.QueryUtil;
import ucla.util.DbUtil;

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
	 * Core function:
	 * For sentence, we return the similarity of its highest similarity NP.
	 * Return null if no noun satisfies the adj
	 * 
	 * @param noun
	 * @param adj
	 * @return
	 */
	public Entry<String, Double> getTopScoreEntry(String noun, String adj) {
		//select out related nouns
		List<String> relatedWords = new ArrayList<String>();
		for (NP np: nps) {
			if (np.containsAdj(adj)) {
				relatedWords.add(np.noun);
				//new Thread(new WikiLSA(noun, np.noun));
			}
		}
		if (relatedWords.size() == 0) {
			return null;
		}
		
		ResultSet resultSet = DbUtil.getSimilarityScoreFromDb(noun, relatedWords);
		
		return resultSet.getTopScoreEntry();
	}
	
	public String getSentenceText() {
		return QueryUtil.lookupReview(reviewId);
	}

	public String getReviewText() {
		return QueryUtil.lookupSentence(reviewId, sentenceId);
	}
	
	public String toString() {
		return reviewId + ": " + sentenceId;
		//return "ReviewId: " + reviewId + " SentenceId: " + sentenceId;
		//return nps + " | " + adjs + " | " + reviewId + " | " + sentenceId;
	}

}