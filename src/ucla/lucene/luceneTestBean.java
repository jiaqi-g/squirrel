package ucla.lucene;

import java.util.Date;

public class luceneTestBean {
	private Integer hotelIdInteger;
	private Integer ReviewId;
	private String para;
	private Double score;
	private Date time;
	
	public luceneTestBean(String hotelId, String reviewId,
			String para, float score,Date time) {
		super();
		this.hotelIdInteger =Integer.parseInt(hotelId);
		ReviewId = Integer.parseInt(reviewId);
		this.para = para;
		this.time = time;
		this.score = (double) score;
	}
	public Integer getHotelIdInteger() {
		return hotelIdInteger;
	}
	public void setHotelIdInteger(Integer hotelIdInteger) {
		this.hotelIdInteger = hotelIdInteger;
	}
	public Integer getReviewId() {
		return ReviewId;
	}
	public void setReviewId(Integer reviewId) {
		ReviewId = reviewId;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getPara() {
		return para;
	}
	public void setPara(String para) {
		this.para = para;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String toString(){
		String timeString= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.time); 
		return "hotel: "+this.hotelIdInteger+" ReviewId: "+this.ReviewId+" score: "+this.score+" time: "+timeString+" para:"+this.para;
	}
	
}
