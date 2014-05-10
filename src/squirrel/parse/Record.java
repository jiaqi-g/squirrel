package squirrel.parse;

import java.util.Map;
import squirrel.nlp.Sentence;

/**
 * We want to present to user, a list of Records by performing Query1, Query2, ...
 * which is the expansion of the original query.
 * 
 * Query1: matched_sent1(review), matched_sent2(review), ...
 * Query2: matched_sent1(review), matched_sent2(review), ...
 * 
 * @author Victor
 */
public class Record {
	String aspect;
	String trait;
	Map<Sentence, Double> rankedSentences;

	public Record(String aspect, String trait, Map<Sentence, Double> rankedSentences) {
		this.aspect = aspect;
		this.trait = trait;
		this.rankedSentences = rankedSentences;
	}
	
	public String getPrettyText() {
		StringBuilder sb = new StringBuilder();
		int rank = 1;
		for (Sentence sentence: rankedSentences.keySet()) {
			sb.append("Rank " + rank++ + ":\n");
			sb.append("Matched: ");
			sb.append(sentence.getSentenceText());
			sb.append("Review: " + sentence.getReview().getId());
		}
		if (rank == 1) {
			sb.append("No results!");
		}
		return sb.toString();
	}

	public String getAspect() {
		return aspect;
	}
	
	public String getTrait() {
		return trait;
	}
	
	public String toString() {
		return aspect + "/" + trait + " " + rankedSentences.toString();
	}
}
