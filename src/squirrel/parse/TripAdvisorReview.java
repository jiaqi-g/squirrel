package squirrel.parse;

import java.util.ArrayList;
import java.util.List;

import squirrel.nlp.Sentence;

/**
 * TripAdvisor Review Data Structure, this structure is used for Gson access.
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
	
	private List<Sentence> sentences = new ArrayList<Sentence>();
	public boolean isNonEnglish = false;
	
	//public static final String regex = "\\.|!|,|\\-";
	public static final String sentenceSpliters = "\\.|!";
	
	public void replaceNonEnglishWords() {
		String old = text;
		text = text.replaceAll("[^\\x00-\\x7F]|[\\-\\*\\%\\&\\$\\#\\~0-9]", "");
		//text = text.replaceAll("[\\x00-\\x7F]", "");
		
		if (old.length() != text.length()) {
			isNonEnglish = true;
		}
	}
	
	/**
	 * sentenceId starts from 0
	 */
	public void transformTextToSentences() {
		String[] tokens = text.split(sentenceSpliters);
		int sentenceId = 0;
		for (String token: tokens) {
			if (token.length() > 5) {
				sentences.add(new Sentence(this, sentenceId++, token.trim() + "."));
			}
		}
	}
	
	public Sentence getSentence(Integer sentenceId) {
		return sentences.get(sentenceId);
	}
	
	public List<Sentence> getSentences() {
		return sentences;
	}
	
	public String getFormattedSentences() {
		StringBuilder sb = new StringBuilder();
		for (Sentence sentence: sentences) {
			sb.append(sentence);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}

	public String getTitle() {
		return title;
	}
	
	public String getDate_stayed() {
		return date_stayed;
	}

	public void setDate_stayed(String date_stayed) {
		this.date_stayed = date_stayed;
	}

	public Integer getOffering_id() {
		return offering_id;
	}

	public void setOffering_id(Integer offering_id) {
		this.offering_id = offering_id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setId(Long id) {
		this.id = id;
	}
	// Getters and setters are not required for this example.
	// GSON sets the fields directly using reflection.
//	@Override
	public String toString() {
		return offering_id + " " + title + " " + text + " " + " " + date_stayed;
	}
}
