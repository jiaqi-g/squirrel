package squirrel.common;

import common.Database;

import squirrel.nlp.similarity.AdjectiveSimilarity;
import squirrel.nlp.similarity.WordSimilarityResultSet;

public class WordSynonymsUtil {

	public static WordSimilarityResultSet getAdjSynonyms(String adj) {
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
			WordSimilarityResultSet rs = (new AdjectiveSimilarity(adj)).getTopSimilaryWordsFromWeb();
			rs.filterWordsBelowScore(Conf.adjSimilarityThreshold);
			return rs;
		}

		return null;
	}

	public static WordSimilarityResultSet getNounSynonyms(String noun) {
		switch (Conf.nounSource) {
		case FILE:
			break;
		case DB:
			return Database.getSimilarityScoresOfWord(noun);
		case WEB:
			break;
		}
		
		return new WordSimilarityResultSet(noun);
	}

}