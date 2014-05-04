package ucla.tripadvisor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import ucla.nlp.ADJSet;
import ucla.nlp.NP;
import ucla.nlp.Sentence;
import ucla.util.FileUtil;
import ucla.util.MapValueComparator;

/**
 * If you use json, you can parse using the google json lib
 * 
 * @author victor
 */
public class QueryUtil {
	
	static List<List<String>> groups = new ArrayList<List<String>>();
	
	static List<String> nouns = new ArrayList<String>();
	static List<Sentence> sents = new ArrayList<Sentence>();
	
	static String mapFileName = "map.txt";
	static String synonymsFileName = "python/synnoms.txt";
	static String reviewFolderPath = "sample/hotel_93396";
	static Map<String, List<String>> reviews;
	
	static {
		try {
			reviews = FileUtil.readFolder(reviewFolderPath);
			readSynonymsFromFile();
			readMapFromFile();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Synonyms File: manually built file with adjective synonyms.
	 * @throws IOException 
	 */
	private static void readSynonymsFromFile() throws IOException {
		List<String> lines = FileUtil.readByLines(synonymsFileName);
		for (String line: lines) {
			List<String> lst = new ArrayList<String>();
			String[] tmp = line.split(",");
			for (String adj: tmp) {
				lst.add(adj.trim());
			}
			groups.add(lst);
		}
	}

	/**
	 * nouns to adj map
	 */
	private static void readMapFromFile() throws Exception {
		List<String> lines = FileUtil.readByLines(mapFileName);
		for (String line : lines) {
			String[] tmp = line.split("\\|");

			//parts
			String[] npPart = tmp[0].split(";");
			String[] adjPart = tmp[1].split(",");
			Integer reviewId = Integer.parseInt(tmp[2].trim());
			Integer sentenceId = Integer.parseInt(tmp[3].trim());
			
			//build sentence
			Sentence sent = new Sentence(reviewId, sentenceId);
			ADJSet indirectAdjs = new ADJSet(adjPart);
			
			for (String np: npPart) {
				String[] temp = np.split(":");
				
				String noun = temp[0].trim();
				ADJSet directAdjs = new ADJSet();
				if (temp.length > 1) {
					directAdjs.addAll(temp[1].split(","));
				}
				sent.addNP(new NP(noun, directAdjs, indirectAdjs));
			}
			
			//add to list
			sents.add(sent);
		}
	}

	/**
	 * currently return similarity > 0.50 by wikiLSA model, sorted results
	 * TODO
	 * 
	 * @param aspect
	 * @param trait
	 * @return
	 */
	public static Map<Sentence, Double> getRankedResults(String aspect, String trait) {
		Map<Sentence, Double> tmp = new HashMap<Sentence, Double>();
		/**
		 * For every sentence, we select out the top score matched entry to represent this sentence's score.
		 */
		for (Sentence sent: sents) {
			Entry<String, Double> topScoreEntry = sent.getTopScoreEntry(aspect, trait);
			if (topScoreEntry != null && topScoreEntry.getValue() < 0.5) {
				topScoreEntry = null;
			}
			
			if (topScoreEntry != null) {
				//System.out.println("$$$$" + sent);
				//System.out.println("$$$$" + topScoreEntry.getValue());
				tmp.put(sent, topScoreEntry.getValue());
			}
		}
		
		//sort map
		MapValueComparator<Sentence> comp = new MapValueComparator<Sentence>(tmp);
		Map<Sentence, Double> res = new TreeMap<Sentence, Double>(comp);
		res.putAll(tmp);
		
		return res;
	}
	
	public static String lookupReview(Integer reviewId) {
		String key = "review_" + reviewId;
		List<String> lines = reviews.get(key);
		
		StringBuilder builder = new StringBuilder();
		for (String line: lines) {
			builder.append(line + ". ");
		}
		return builder.toString();
	}
	
	public static String lookupSentence(Integer reviewId, Integer sentenceId) {
		String key = "review_" + reviewId;
		return reviews.get(key).get(sentenceId - 1);
	}
	
	/**
	 * Get a group of adjs with similar names to the given one, return itself if no synonyms
	 * @param trait
	 * @return
	 */
	public static List<String> getSynonyms(String trait) {
		for (List<String> lst : groups) {
			if (lst.contains(trait)) {
				return lst;
			}
		}
		List<String> lst = new ArrayList<String>();
		lst.add(trait);
		return lst;
	}

	public static void main(String[] args) {
		//System.out.println(QueryUtil.groups);
		System.out.println(QueryUtil.lookupReview(10082389));
		System.out.println(QueryUtil.lookupSentence(10082389, 3));
	}
}
