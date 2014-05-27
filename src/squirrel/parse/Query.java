package squirrel.parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import common.Database;
import common.TimeUtil;
import squirrel.common.Conf;
import squirrel.common.Log;
import squirrel.common.WordSynonymsUtil;
import squirrel.nlp.Sentence;
import squirrel.nlp.similarity.WordSimilarityResultList;

public class Query {
	String noun;
	String adj;
	Integer hotelId;
	
	WordSimilarityResultList nounSynonyms;
	WordSimilarityResultList adjSynonyms;

	public Query(Integer hotelId, String noun, String adj) {
		this.hotelId = hotelId;
		this.noun = noun;
		this.adj = adj;
		
		nounSynonyms = WordSynonymsUtil.getNounSynonyms(noun);
		adjSynonyms = WordSynonymsUtil.getAdjSynonyms(adj);
	}

	public Record process() {
		TimeUtil.start();
		List<Sentence> sents = getRankedResults();
		return new Record(noun, adj, sents, TimeUtil.getPassedSeconds());
	}
	
	private List<Sentence> getRankedResults() {
		//TODO: can be optimized maybe
		List<Sentence> sents = Database.getAllReviewSentences(hotelId);
		
		List<Sentence> res = new ArrayList<Sentence>();
		StringBuilder sb = new StringBuilder();
		sb.append("unsorted result ");
		for (Sentence sent: sents) {
			sent.computeScore(nounSynonyms, adjSynonyms);
					
			if (sent.getScore() > Conf.sentenceSimilarityThreshold) {
				res.add(sent);
			}
			
			sb.append(sent.getSentenceFullId());
			sb.append(" ");
		}
		Log.log(sb.toString());
		Collections.sort(res);
		
		return res;
	}
	
}