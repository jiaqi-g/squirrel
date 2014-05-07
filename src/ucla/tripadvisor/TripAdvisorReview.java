package ucla.tripadvisor;

public class TripAdvisorReview {
	private String title;
	private String text;
	//private String author;
	private String date_stayed;
	private Integer offering_id;
	private Long id;
	
	public boolean isNonEnglish = false;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void replaceNonEnglishWords() {
		String old = text;
		text = text.replaceAll("[^\\x00-\\x7F]", "");
		if (old.length() != text.length()) {
			isNonEnglish = true;
		}
	}
	
	
	public void replaceSpecialChars() {
		// TODO: replace 0-9, -, $, #, 1.2.3.
		
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
