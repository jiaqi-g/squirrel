package squirrel.nlp.similarity;

public class WordSimilarityScore {
	private String word;
	private Double similarity;
		
	public WordSimilarityScore(String word, Double similarity) {
		this.word = word;
		this.similarity = similarity;
	}
	
	public String getNoun() {
		return word;
	}
	
	public void setNoun(String noun) {
		this.word = noun;
	}
	
	public Double getSimilarity() {
		return similarity;
	}
	
	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}
	
	public String toString() {
		return word + ": " + similarity;
	}
}