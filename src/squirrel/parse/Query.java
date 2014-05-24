package squirrel.parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import common.Database;
import squirrel.common.SynonymsUtil;
import squirrel.nlp.ADJSet;
import squirrel.nlp.Sentence;
import squirrel.nlp.similarity.WordSimilarityResultSet;

public class Query {
	
	public static final Double similarityThreshold = 0.5;
	
	String aspect;
	String trait;
	Integer hotelId;
	
	ADJSet traitSynonyms;
	WordSimilarityResultSet aspectSimilarityDb;

	public Query(Integer hotelId, String aspect, String trait) {
		this.hotelId = hotelId;
		this.aspect = aspect;
		this.trait = trait;

		traitSynonyms = SynonymsUtil.getSynonyms(trait);
		aspectSimilarityDb = Database.getSimilarityScoresOfWord(aspect);
	}

	public Record process() {
		return new Record(aspect, trait, traitSynonyms, getRankedResults());
	}

	/**
	 * currently return similarity > 0.50 by wikiLSA model, ranked results
	 * TODO
	 * 
	 * @param aspect
	 * @param traitSynonym
	 * @return
	 */
	private List<Sentence> getRankedResults() {
		List<Sentence> sents = Database.getAllReviewSentences(hotelId);
		
		List<Sentence> res = new ArrayList<Sentence>();
		for (Sentence sent: sents) {
			Double score = sent.computeScore(traitSynonyms, aspectSimilarityDb);

			if (score > similarityThreshold) {
				res.add(sent);
			}
		}
		Collections.sort(res);
		
		return res;
	}
}