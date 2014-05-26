package squirrel.nlp;

import java.util.HashSet;
import java.util.Set;
import squirrel.nlp.similarity.WordSimilarityResultList;
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
	Double score = 0.0;

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
	public Sentence(Long reviewId, int sentenceId, String sentenceText) {
		super(reviewId, sentenceId, sentenceText);
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
	
	/**
	 * score of sentence is maximal score of its nps
	 */
	public void computeScore(WordSimilarityResultList nounSynonyms, WordSimilarityResultList adjSynonyms) {
		ADJSet adjSet = new ADJSet(adjSynonyms);
		
		for (NP np: nps) {
			if (np.getADJSet().belongToAdjSet(adjSet)) {
				Double score = nounSynonyms.getScore(np.noun);
				if (score > this.score) {
					this.score = score;
				}
			}
		}
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
		return sent.reviewId.equals(reviewId) && sent.sentenceId.equals(sentenceId);
	}

	@Override
	public int compareTo(Sentence o) {
		if (o.score >= score) {
			return 1;
		}
		else {
			return -1;
		}
	}

}