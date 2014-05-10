package squirrel.nlp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import common.Database;

import squirrel.common.ReviewUtil;
import squirrel.nlp.similarity.NounSimilarityResult;
import squirrel.parse.BasicSentence;
import squirrel.parse.TripAdvisorReview;

/**
 * Extends Basic Sentence to support NLP features
 * @author victor
 *
 */
public class Sentence extends BasicSentence {
	TripAdvisorReview review;
	Set<NP>	nps = new HashSet<NP>();
	
	/**
	 * construct from memory
	 * @param reviewId
	 * @param sentenceId
	 * @param sentenceText
	 */
	public Sentence(TripAdvisorReview review, int sentenceId, String sentenceText) {
		super(review.getId(), sentenceId, sentenceText);
		this.review = review;
	}
	
	/**
	 * construct from db
	 * @param reviewId
	 * @param sentenceId
	 * @param sentenceText
	 */
	public Sentence(Long reviewId, int sentenceId, String sentenceText) {
		super(reviewId, sentenceId, sentenceText);
		this.review = Database.getReview(reviewId);
	}
	
	public void addNP(NP np) {
		nps.add(np);
	}

	public TripAdvisorReview getReview() {
		return review;
	}
	
	public String getNPSetString() {
		return nps.toString();
	}
	
	public NounSimilarityResult getNounSimilarityResult(String noun, String adj) {
		//select out related nouns
		List<String> relatedWords = new ArrayList<String>();
		for (NP np: nps) {
			// TODO: modify following exact match on adjs
			if (np.containsAdj(adj)) {
				relatedWords.add(np.noun);
				//new Thread(new WikiLSA(noun, np.noun));
			}
		}
		
		return ReviewUtil.getSimilarityBetweenNouns(noun, relatedWords);
	}
	
	/*
	@Override
	public int hashCode() {
		return (review.getId() + ":" + sentenceId).hashCode();
	}*/
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Sentence)) {
			return false;
		}
		
		Sentence sent = (Sentence) obj;
		return sent.getReview().getId().equals(reviewId) && sent.getSentenceId().equals(sentenceId);
	}

}