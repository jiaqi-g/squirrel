package squirrel.parse;

import java.util.List;

import squirrel.nlp.Sentence;

/**
 * TripAdvisor Review Data Structure, this structure is used 
 * 
 * @author victor
 *
 */
public class TripAdvisorReview {
	
	private String title;
	private String text;
	private String date_stayed;
	private Integer offering_id;
	private Long id;
	
	private List<Sentence> sentences;
	public boolean isNonEnglish = false;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void replaceNonEnglishWords() {
		String old = text;
		text = text.replaceAll("[^\\x00-\\x7F]|[\\-\\*\\&\\$\\#\\~0-9]", "");
		//text = text.replaceAll("[\\x00-\\x7F]", "");
		
		if (old.length() != text.length()) {
			isNonEnglish = true;
		}
	}
	
	public void transformTextToSentences() {
		// TODO
	}
	
	public Sentence getSentence(Integer sentenceId) {
		return sentences.get(sentenceId);
	}
	
	public List<Sentence> getSentences() {
		return sentences;
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
