package ucla.lucene;

public class reviewSentBean {
	private int id;
	private long reviewId;
	private long sentId;
	private String noun;
	private String adjString;
	
	public reviewSentBean(long reviewId, long sentId, String noun, String adjString){
		this.reviewId = reviewId;
		this.sentId = sentId;
		this.noun = noun;
		this.adjString = adjString;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getReviewId() {
		return reviewId;
	}
	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}
	public long getSentId() {
		return sentId;
	}
	public void setSentId(long sentId) {
		this.sentId = sentId;
	}
	public String getNoun() {
		return noun;
	}
	public void setNoun(String noun) {
		this.noun = noun;
	}
	public String getAdjString() {
		return adjString;
	}
	public void setAdjString(String adjString) {
		this.adjString = adjString;
	}
	public String toString(){
		return "reviewId: "+this.reviewId+" sentId:"+this.sentId+" noun:"+this.noun+" adj:"+this.adjString;
	}

}
