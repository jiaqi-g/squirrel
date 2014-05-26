package squirrel.parse;

import java.util.List;

import common.Database;

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
	List<Sentence> rankedSentences;

	public Record(String aspect, String trait, List<Sentence> rankedSentences) {
		this.aspect = aspect;
		this.trait = trait;
		this.rankedSentences = rankedSentences;
	}

	/**
	 * Record's output
	 * @return
	 */
	public String getPrettyText() {
		StringBuilder sb = new StringBuilder();
		int rank = 1;
		//System.out.println("@@@" + rankedSentences);
		//System.out.println("@@@" + rankedSentences.values());
		//for (Sentence sentence: rankedSentences.keySet()) {
		//System.out.println("@@@" + rankedSentences.keySet().contains(sentence));
		//}

		sb.append("\n");
		for (Sentence sentence: rankedSentences) {
			sb.append(rank++);
			sb.append(" (" + sentence.getSentenceFullId() + ") ");
			sb.append(sentence.getScore() + "");
			sb.append("\n");
			//sb.append("Matched: ");
			sb.append(sentence.getSentenceText());
			sb.append("\n\n");
		}
		
		if (rank == 1) {
			sb.append("No results!\n\n");
		}

		return sb.toString();
	}

	public String getAspect() {
		return aspect;
	}

	public String getTrait() {
		return trait;
	}

	public String getPrettyReviews(List<Sentence> sentences) {
		List<TripAdvisorReview> reviews = Database.getReviewTexts(sentences);
		StringBuilder sb = new StringBuilder();
		for (TripAdvisorReview review: reviews) {
			sb.append("************ ");
			sb.append(review.getOffering_id());
			sb.append("************\n");
			sb.append(review.getText());
		}
		return sb.toString();
	}

	public String toString() {
		return aspect + "/" + trait + " " + rankedSentences.toString();
	}
}
