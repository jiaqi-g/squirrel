package squirrel.nlp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import squirrel.common.ReviewUtil;
import squirrel.nlp.similarity.NounSimilarityResult;
import squirrel.parse.TripAdvisorReview;

public class Sentence {
	TripAdvisorReview review;
	Integer sentenceId; /* sentenceId starts from 0 in every review */
	String sentenceText;

	Set<NP>	nps = new HashSet<NP>();
	
	public Sentence(TripAdvisorReview review, int sentenceId, String sentenceText) {
		this.review = review;
		this.sentenceId = sentenceId;
		this.sentenceText = sentenceText;
	}

	public void addNP(NP np) {
		nps.add(np);
	}

	public TripAdvisorReview getReview() {
		return review;
	}
	
	public Integer getSentenceId() {
		return sentenceId;
	}
	
	public String getSentenceText() {
		return sentenceText;
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
	
	public String toString() {
		//return review.getId() + ":" + sentenceId;
		return sentenceId + ": " + sentenceText;
	}

}