package ucla.tripadvisor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ucla.nlp.Sentence;

public class Query {
	String aspect;
	String trait;

	Map<String, Double> aspectRankings = new HashMap<String, Double>();
	List<String> traitSynonyms = new ArrayList<String>();

	/**
	 * We want to present to user, a list of results by performing Query1, Query2, ...
	 * which is the expansion of the original query.
	 * 
	 * Query1: matched_review:sent, matched_review:sent
	 * Query2: matched_review:sent
	 * 
	 * @author Victor
	 */
	class Record {
		String aspect;
		String trait;
		Map<Sentence, Double> rankedSentences;

		public Record(String aspect, String trait, Map<Sentence, Double> rankedSentences) {
			this.aspect = aspect;
			this.trait = trait;
			this.rankedSentences = rankedSentences;
		}

		public String toString() {
			return aspect + "/" + trait + " " + rankedSentences.toString();
		}
	}

	public Query(String aspect, String trait) {
		this.aspect = aspect;
		this.trait = trait;

		traitSynonyms = QueryUtil.getSynonyms(trait);
	}

	private List<Record> search() {
		List<Record> res = new ArrayList<Record>();
		//first we search the trait itself
		res.add(new Record(aspect, trait, QueryUtil.getRankedResults(aspect, trait)));
		
		for (String synonym: traitSynonyms) {
			if (!synonym.equals(trait)) {
				res.add(new Record(aspect, synonym, QueryUtil.getRankedResults(aspect, synonym)));
			}
		}
		
		return res;
	}

	public static void main(String[] args) throws Exception {
		Class.forName(QueryUtil.class.getCanonicalName());
		
		while (true) {
			System.out.print("Enter Query as \"Aspect/Trait\" : ");
			try {
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				String s = bufferRead.readLine();
				String[] tmp = s.split("/");
				if (tmp.length != 2) {
					System.out.print("Error Input! \n");
					continue;
				} else {
					Query query = new Query(tmp[0].trim(), tmp[1].trim());
					//System.out.println("Results: ");
					List<Record> res = query.search();
					for (int i=0; i<res.size(); i++) {
						System.out.println("Query" + (i+1) + ": " + res.get(i));
					}
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
