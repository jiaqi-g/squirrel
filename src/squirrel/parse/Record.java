package squirrel.parse;

import java.util.List;

import squirrel.nlp.ADJSet;
import squirrel.nlp.Sentence;

/**
 * We want to present to user, a list of Records by performing Query1, Query2, ...
 * which is the expansion of the original query.
 * 
 * Query1: matched_sent1(review), matched_sent2(review), ...
 * Query2: matched_sent1(review), matched_sent2(review), ...
 * 
 * @author Victor
 */
public class Record {
	String aspect;
	String trait;
	ADJSet traitSynonyms;
	List<Sentence> rankedSentences;

	public Record(String aspect, String trait, ADJSet traitSynonyms, List<Sentence> rankedSentences) {
		this.aspect = aspect;
		this.trait = trait;
		this.traitSynonyms = traitSynonyms;
		this.rankedSentences = rankedSentences;
	}
	
	/**
	 * Record's output
	 * @return
	 */
	public String getPrettyText() {
		StringBuilder sb = new StringBuilder();
		int rank = 1;
		//System.out.println("@@@" + rankedSentences);
		//System.out.println("@@@" + rankedSentences.values());
		//for (Sentence sentence: rankedSentences.keySet()) {
			//System.out.println("@@@" + rankedSentences.keySet().contains(sentence));
		//}
		
		for (Sentence sentence: rankedSentences) {
			sb.append("Rank " + rank++);
			sb.append(" (" + sentence.getSentenceFullId() + ") ");
			sb.append(sentence.getScore() + "");
			sb.append("\n");
			//sb.append("Matched: ");
			sb.append(sentence.getSentenceText());
			sb.append("\n");
		}
		if (rank == 1) {
			sb.append("No results!\n");
		}
		
		return sb.toString();
	}

	public String getAspect() {
		return aspect;
	}
	
	public String getTrait() {
		return trait;
	}
	
	public String toString() {
		return aspect + "/" + trait + " " + rankedSentences.toString();
	}
}
