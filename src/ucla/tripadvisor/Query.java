package ucla.tripadvisor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ucla.tripadvisor.QueryUtil.Sentence;

public class Query {

	String aspect;
	//String trait;
	
	Map<String, Double> aspectRankings = new HashMap<String, Double>();
	List<String> traitSynonyms = new ArrayList<String>();
	
	class Record {
		String aspect;
		String trait;
		
	}
	
	public Query(String aspect, String trait) {
		this.aspect = aspect;
		//this.trait = trait;
		traitSynonyms = QueryUtil.getSynonyms(trait);
	}
	
	private List search() {
		for (String synonym: traitSynonyms) {
			List<Sentence> lst = QueryUtil.getRankedResults(aspect, synonym);
			for (Sentence sent: lst) {
				sent.getReviewId();
				sent.getSentenceId();
			}
		}
		
		return null;
	}

	public static void main(String[] args) {
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
					System.out.println("Results: ");
					System.out.println(query.search());
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
