package squirrel.nlp.similarity;

public class WordSimilarityScore implements Comparable<WordSimilarityScore> {
	private String word;
	private Double similarity;

	public WordSimilarityScore(String word, Double similarity) {
		this.word = word;
		this.similarity = similarity;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}
	
	/*
	@Override
	public boolean equals(Object o){
		if (this == o) return true;
		if (!(o instanceof WordSimilarityScore)) return false;
		WordSimilarityScore that = (WordSimilarityScore)o;
		return word.equals(that.word);
	}*/
	
	public String toString() {
		return word + ": " + similarity;
	}

	@Override
	public int compareTo(WordSimilarityScore o) {
		if (similarity < o.getSimilarity()) {
			return 1;
		}
		else {
			return -1;
		}
	}
}