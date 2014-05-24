package squirrel.parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import common.Database;
import squirrel.common.Conf;
import squirrel.common.WordSynonymsUtil;
import squirrel.nlp.Sentence;
import squirrel.nlp.similarity.WordSimilarityResultSet;

public class Query {
	String noun;
	String adj;
	Integer hotelId;
	
	WordSimilarityResultSet nounSynonyms;
	WordSimilarityResultSet adjSynonyms;

	public Query(Integer hotelId, String noun, String adj) {
		this.hotelId = hotelId;
		this.noun = noun;
		this.adj = adj;

		nounSynonyms = WordSynonymsUtil.getNounSynonyms(noun);
		adjSynonyms = WordSynonymsUtil.getAdjSynonyms(adj);
	}

	public Record process() {
		return new Record(noun, adj, getRankedResults());
	}
	
	private List<Sentence> getRankedResults() {
		List<Sentence> sents = Database.getAllReviewSentences(hotelId);
		
		List<Sentence> res = new ArrayList<Sentence>();
		for (Sentence sent: sents) {
			Double score = sent.computeScore(nounSynonyms, adjSynonyms);

			if (score > Conf.sentenceSimilarityThreshold) {
				res.add(sent);
			}
		}
		Collections.sort(res);
		
		return res;
	}
}