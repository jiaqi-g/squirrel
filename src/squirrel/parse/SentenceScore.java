package squirrel.parse;

/**
 * 
 * @author victor
 *
 */
public class SentenceScore implements Comparable<SentenceScore> {
	private BasicSentence sentence;
	private Double score;
	
	public SentenceScore(BasicSentence sentence, Double score) {
		this.sentence = sentence;
		this.score = score;
	}
	
	public String getSentenceFullId() {
		return sentence.getReviewId() + ":" + sentence.getSentenceId();
	}
	
	public BasicSentence getBasicSentence() {
		return sentence;
	}
	
	public Double getScore() {
		return score;
	}
	
	@Override
	public String toString() {
		return sentence + ":" + score;
	}

	@Override
	public int compareTo(SentenceScore o) {
		if (o.score > score) {
			return 1;
		}
		else {
			return -1;
		}
	}
}