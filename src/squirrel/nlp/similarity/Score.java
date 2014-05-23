package squirrel.nlp.similarity;

public class Score {
	
	private String noun;
	private Double similarity;
		
	public Score(String noun, Double similarity) {
		this.noun = noun;
		this.similarity = similarity;
	}
	
	public String getNoun() {
		return noun;
	}
	public void setNoun(String noun) {
		this.noun = noun;
	}
	public Double getSimilarity() {
		return similarity;
	}
	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}
	
	public String toString() {
		return noun + ", " + similarity;
	}
}