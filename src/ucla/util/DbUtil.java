package ucla.util;

import java.util.List;

import ucla.similarity.ResultSet;

public class DbUtil {

	public static ResultSet getSimilarityScoreFromDb(String noun, List<String> relatedWords) {
		ResultSet resultSet = new ResultSet(noun);
		for (String word: relatedWords) {
			// TODO: modify this to reflect actual score read from db.
			Double score = noun.equals(word)? 1.0 : 0.0;
			resultSet.add(word, score);	
		}
		return resultSet;
	}
}
