package ucla.lucene;


public class sentCntBean {
	private int id;
	private int reviewId;
	private int sentId;
	private String sent;
	public sentCntBean(int reviewId, int sentId, String sent) {
	
		this.reviewId = reviewId;
		this.sentId = sentId;
		this.sent = sent;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	
	public int getSentId() {
		return sentId;
	}
	public void setSentId(int sentId) {
		this.sentId = sentId;
	}
	public String getSent() {
		return sent;
	}
	public void setSent(String sent) {
		this.sent = sent;
	}
	public String toString() {
		String str = "reviewId: %d, sentId: %d, Sent: %s";
		return String.format(str, this.reviewId, this.sentId, this.sent);
	}
	
}
