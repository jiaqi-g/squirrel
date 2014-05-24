package squirrel.nlp;

import java.util.HashSet;
import java.util.Set;
import squirrel.nlp.similarity.WordSimilarityResultSet;
import squirrel.parse.BasicSentence;
import squirrel.parse.TripAdvisorReview;

/**
 * Extends Basic Sentence to support NLP features
 * @author victor
 *
 */
public class Sentence extends BasicSentence implements Comparable<Sentence> {
	TripAdvisorReview review;
	Set<NP>	nps = new HashSet<NP>();
	
	//score would change given different queries
	Double score;
	
	/**
	 * construct from memory
	 */
	public Sentence(TripAdvisorReview review, int sentenceId, String sentenceText) {
		super(review.getId(), sentenceId, sentenceText);
		this.review = review;
	}
	
	/**
	 * construct from db
	 */
	public Sentence(Long reviewId, int sentenceId) {
		super(reviewId, sentenceId);
		//this.review = Database.getReview(reviewId);
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
	
	private boolean containsQueryAdjs(ADJSet queryAdjs) {
		for (NP np: nps) {
			if (np.containsAdjSet(queryAdjs)) {
				return true;
			}
		}
		return false;
	}
	
	private Double getHighestNounScore(WordSimilarityResultSet aspectSimilarityDb) {
		Double res = 0.0;
		for (NP np: nps) {
			Double tmp = aspectSimilarityDb.getScore(np.noun);
			if (tmp > res) {
				res = tmp;
			}
		}
		return res;
	}
	
	/**
	 * we treat sentence score as highest score of its noun
	 */
	public Double computeScore(ADJSet queryAdjs, WordSimilarityResultSet aspectSimilarityDb) {
		if (containsQueryAdjs(queryAdjs)) {
			return getHighestNounScore(aspectSimilarityDb);
		}
		return 0.0;
	}
	
	public Double getScore() {
		return score;
	}
	
	public String getSentenceFullId() {
		return getReviewId() + ":" + getSentenceId();
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
	
	@Override
	public int compareTo(Sentence o) {
		if (o.score > score) {
			return 1;
		}
		else {
			return -1;
		}
	}

}