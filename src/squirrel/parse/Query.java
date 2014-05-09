package squirrel.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import common.MapValueComparator;

import squirrel.common.ReviewUtil;
import squirrel.common.SynonymsUtil;
import squirrel.nlp.Sentence;
import squirrel.nlp.similarity.NounSimilarityResult;

public class Query {
	String aspect;
	String trait;

	Map<String, Double> aspectRankings = new HashMap<String, Double>();
	List<String> traitSynonyms = new ArrayList<String>();
	
	public Query(String aspect, String trait) {
		this.aspect = aspect;
		this.trait = trait;

		traitSynonyms = SynonymsUtil.getSynonyms(trait);
	}

	public List<Record> process() {
		List<Record> res = new ArrayList<Record>();
		//first we search the trait itself
		res.add(new Record(aspect, trait, getRankedResults(aspect, trait)));
		
		for (String synonym: traitSynonyms) {
			if (!synonym.equals(trait)) {
				res.add(new Record(aspect, synonym, getRankedResults(aspect, synonym)));
			}
		}
		
		return res;
	}
	
	/**
	 * currently return similarity > 0.50 by wikiLSA model, sorted results
	 * TODO
	 * 
	 * @param aspect
	 * @param traitSynonym
	 * @return
	 */
	private Map<Sentence, Double> getRankedResults(String aspect, String traitSynonym) {
		Map<Sentence, Double> tmp = new HashMap<Sentence, Double>();
		
		// For every sentence, we select out the top score matched entry to represent this sentence's score.
		for (TripAdvisorReview review: ReviewUtil.getReviews()) {
			for (Sentence sent: review.getSentences()) {
				NounSimilarityResult similarityResult = sent.getNounSimilarityResult(aspect, traitSynonym);
				Entry<String, Double> topScoreEntry = similarityResult.getTopScoreEntry();
				
				if (topScoreEntry != null && topScoreEntry.getValue() < 0.5) {
					topScoreEntry = null;
				}
				
				if (topScoreEntry != null) {
					tmp.put(sent, topScoreEntry.getValue());
				}
			}
		}
		
		//sort map
		MapValueComparator<Sentence> comp = new MapValueComparator<Sentence>(tmp);
		Map<Sentence, Double> res = new TreeMap<Sentence, Double>(comp);
		res.putAll(tmp);
		
		return res;
	}
}