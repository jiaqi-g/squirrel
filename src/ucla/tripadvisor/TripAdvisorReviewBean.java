package ucla.tripadvisor;

public class TripAdvisorReviewBean {
	private String title;
	private String text;
	//private String author;
	private String date_stayed;
	private Integer offering_id;
	private Long id;
	
	public String getText() {
		return text;
	}
	
	public Long getId() {
		return id;
	}
	
	public Integer getOfferingId() {
		return offering_id;
	}
	
	// Getters and setters are not required for this example.
	// GSON sets the fields directly using reflection.
	@Override
	public String toString() {
		return offering_id + " " + title + " " + text + " " + " " + date_stayed;
	}
}
