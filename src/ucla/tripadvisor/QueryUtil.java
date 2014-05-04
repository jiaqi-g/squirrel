package ucla.tripadvisor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ucla.util.FileUtil;

/**
 * If you use json, you can parse by using the google lib
 * 
 * @author victor
 */
public class QueryUtil {

	/**
	 * a NP, noun phrase, is a noun and set of adjs associated with this noun
	 * 
	 * @author victor
	 */
	static class NP {
		ADJSet adjs;
		String noun;

		public NP(String noun) {
			this(noun, new ADJSet());
		}

		public NP(String noun, ADJSet adjs) {
			this.noun = noun;
			this.adjs = adjs;
		}

		public boolean contains(String noun, String adj) {
			return containsAdj(adj) && containsNoun(noun);
		}

		public boolean containsAdj(String e) {
			return adjs.contains(e);
		}

		public boolean containsNoun(String e) {
			return noun.startsWith(e);
		}
		
		public String toString() {
			return noun + " : " + adjs.toString(); 
		}
	}

	static class ADJSet {
		Set<String> adjs = new HashSet<String>();

		public ADJSet() {
		}

		public ADJSet(String[] adjs) {
			addAll(adjs);
		}
		
		public void addAll(String[] adjs) {
			for (String adj: adjs) {
				this.adjs.add(adj.trim());
			}
		}

		public void add(String e) {
			adjs.add(e);
		}

		public boolean contains(String e) {
			return adjs.contains(e);
		}
		
		public String toString() {
			return adjs.toString();
		}
	}

	static class Sentence {
		Integer reviewId;
		Integer sentenceId;
		ADJSet adjs = new ADJSet();
		Set<NP>	nps = new HashSet<NP>();

		public Sentence(int reviewId, int sentenceId) {
			this.reviewId = reviewId;
			this.sentenceId = sentenceId;
		}
		
		public void addAdjs(String[] adjs) {
			this.adjs.addAll(adjs);
		}

		public void addNP(NP np) {
			nps.add(np);
		}

		public Integer getReviewId() {
			return reviewId;
		}
		
		public Integer getSentenceId() {
			return sentenceId;
		}
		
		/**
		 * We return boolean value for simplicity currently.
		 * @param noun
		 * @param adj
		 * @return
		 */
		public boolean calculateScore(String noun, String adj) {
			boolean adjContain = false;
			boolean nounContain = false;

			for (NP np: nps) {
				if (np.containsNoun(noun)) {
					nounContain = true;
					if (np.containsAdj(adj)) {
						adjContain = true;
					}
				}
			}

			if (nounContain) {
				if (adjs.contains(adj)) {
					adjContain = true;
				}
			}

			return adjContain && nounContain;
		}
		
		public String getSentenceText() {
			return QueryUtil.lookupReview(reviewId);
		}

		public String getReviewText() {
			return QueryUtil.lookupSentence(reviewId, sentenceId);
		}
		
		public String toString() {
			return nps + " | " + adjs + " | " + reviewId + " | " + sentenceId;
		}
	}

	static List<List<String>> groups = new ArrayList<List<String>>();
	static List<String> nouns = new ArrayList<String>();
	static List<Sentence> sents = new ArrayList<Sentence>();
	
	static String mapFileName = "map.txt";
	static String synonymsFileName = "synonyms.txt";
	
	static {
		try {
			readSynonymsFromFile();
			readMapFromFile();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Synonyms File: manually built file with adjective synonyms.
	 */
	private static void readSynonymsFromFile() {

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
			sent.addAdjs(adjPart);
			
			for (String np: npPart) {
				String[] temp = np.split(":");
				
				String noun = temp[0].trim();
				ADJSet adjs = new ADJSet();
				if (temp.length > 1) {
					adjs.addAll(temp[1].split(","));
				}
				sent.addNP(new NP(noun, adjs));
			}
			
			//add to list
			sents.add(sent);
		}
	}

	/**
	 * currently no ranking
	 * @param aspect
	 * @param trait
	 * @return
	 */
	public static List<Sentence> getRankedResults(String aspect, String trait) {
		// TODO
		List<Sentence> res = new ArrayList<Sentence>();
		for (Sentence sent: sents) {
			if (sent.calculateScore(aspect, trait)) {
				res.add(sent);
			}
		}
		return res;
	}
	
	public static String lookupReview(Integer reviewId) {
		// TODO
		return null;
	}
	
	public static String lookupSentence(Integer reviewId, Integer sentenceId) {
		// TODO
		return null;
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
		System.out.println(QueryUtil.sents);
	}
}
