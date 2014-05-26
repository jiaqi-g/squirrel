package squirrel.common;

import common.Database;

import squirrel.nlp.similarity.AdjectiveSimilarity;
import squirrel.nlp.similarity.WordSimilarityResultList;

public class WordSynonymsUtil {

	public static void log(String s) {
		Log.log("[WordSynonymsUtil]", s);
	}
	
	public static WordSimilarityResultList getAdjSynonyms(String adj) {
		switch (ConfUtil.adjSource) {
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
			if (Conf.debug) {
				log("\n");
				System.out.println(rs);
			}
			return rs;
		}

		return null;
	}

	public static WordSimilarityResultList getNounSynonyms(String noun) {
		switch (ConfUtil.nounSource) {
		case FILE:
			break;
		case DB:
			WordSimilarityResultList rs = Database.getSimilarityScoresOfNoun(noun, Conf.nounSimilarityThreshold);
			if (Conf.debug) {
				log("\n");
				System.out.println(rs);
			}
			return rs;
		case WEB:
			break;
		}
		
		return null;
	}

}