package squirrel.parse;


public class BasicSentence {
	protected Long reviewId;
	protected Integer sentenceId; /* sentenceId starts from 0 in every review */
	protected String sentenceText;
	
	public BasicSentence(Long reviewId, int sentenceId, String sentenceText) {
		this.reviewId = reviewId;
		this.sentenceId = sentenceId;
		this.sentenceText = sentenceText;
	}
	
	public BasicSentence(Long reviewId, int sentenceId) {
		this.reviewId = reviewId;
		this.sentenceId = sentenceId;
	}
	
	public Long getReviewId() {
		return reviewId;
	}
	
	public Integer getSentenceId() {
		return sentenceId;
	}
	
	public String getSentenceText() {
		return sentenceText;
	}
	
	@Override
	public String toString() {
		//return review.getId() + ":" + sentenceId;
		//return sentenceId + "-" + sentenceText;
		return sentenceText;
	}
}