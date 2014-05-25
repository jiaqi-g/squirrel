package squirrel.common;

import java.util.Set;

import common.Database;

import squirrel.nlp.similarity.AdjectiveSimilarity;
import squirrel.nlp.similarity.WordSimilarityResultList;

public class WordSynonymsUtil {

	public static WordSimilarityResultList getAdjSynonyms(String adj) {
		switch (Conf.adjSource) {
		case FILE:
			/*
			String synonymsFileName = "python/synnoms.txt";
			List<List<String>>  synonymsGroups = new ArrayList<List<String>>();

			List<String> lines = FileSystem.readAllLines(synonymsFileName);
			for (String line: lines) {
				List<String> lst = new ArrayList<String>();
				String[] tmp = line.split(",");
				for (String adj: tmp) {
					lst.add(adj.trim());
				}
				synonymsGroups.add(lst);
			}

			return synonymsGroups;
			 */
			break;
		case DB:
			break;
		case WEB:
			WordSimilarityResultList rs = (new AdjectiveSimilarity(adj)).getTopSimilaryWordsFromWeb();
			rs.filterWordsBelowScore(Conf.adjSimilarityThreshold);
			return rs;
		}

		return null;
	}

	public static WordSimilarityResultList getNounSynonyms(String noun) {
		switch (Conf.nounSource) {
		case FILE:
			break;
		case DB:
			return Database.getSimilarityScoresOfNoun(noun, Conf.nounSimilarityThreshold);
		case WEB:
			break;
		}
		
		return null;
	}

}