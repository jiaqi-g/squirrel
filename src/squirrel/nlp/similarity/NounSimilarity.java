package squirrel.nlp.similarity;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import common.Log;

/**
 * WikiLSA as noun similarity service
 * @author victor
 *
 */
public class NounSimilarity {

	public static String url = "http://deeptutor2.memphis.edu/WikiLSA/getSimilarity";
	private WordSimilarityResultSet scoreMap;
	private String noun;
	
	public NounSimilarity(String noun) {
		this.scoreMap = new WordSimilarityResultSet(noun);
		this.noun = noun;
	}
	
	private static void log(String string) {
		Log.log("[WIKILSA]", string);
	}
	
	private static void warn(String string) {
		Log.warn("[WIKILSA]", string);
	}
	
	/**
	 * automatically ignore words that do not common to other words
	 * @param allWords
	 * @return
	 */
	public WordSimilarityResultSet getSimilarityResult(int id, List<String> allWords) {
		//boolean ignore = false;
		
		int i = 0;
		for (String compareNoun: allWords) {
			Double score = 0.0;
			score = retrieveScoreFromWeb(noun, compareNoun);
			
			scoreMap.add(compareNoun, score);
			
			report(id, i, allWords.size());
			i++;
			log(noun + " " + compareNoun + " " + score);
		}
		
		return scoreMap;
	}
	
	private void report(int id, int processed, int cnt) {
		String s = "word " + id + " ";
		if (processed == cnt/10*1) {
			s += "10% finished";
			warn(s);
		}
		else if (processed == cnt/10*2) {
			s += "20% finished";
			warn(s);
		}
		else if (processed == cnt/10*3) {
			s += "30% finished";
			warn(s);
		}
		else if (processed == cnt/10*4) {
			s += "40% finished";
			warn(s);
		}
		else if (processed == cnt/10*5) {
			s += "50% finished";
			warn(s);
		}
		else if (processed == cnt/10*6) {
			s += "60% finished";
			warn(s);
		}
		else if (processed == cnt/10*7) {
			s += "70% finished";
			warn(s);
		}
		else if (processed == cnt/10*8) {
			s += "80% finished";
			warn(s);
		}
		else if (processed == cnt/10*9) {
			s += "90% finished";
			warn(s);
		}
	}
	
	/**
	 * return false if the compare result is null
	 * @param noun
	 * @param compareNoun
	 * @return
	 */
	private Double retrieveScoreFromWeb(String noun, String compareNoun) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("word1", noun);
		data.put("word2", compareNoun);
		
		while (true) {
			try {
				Document document = Jsoup.connect(url)
						.data(data)
						//.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.116 Safari/537.36")
						.post();
	
				String cssQuery = "p";
				Element elem = document.select(cssQuery).get(3);
				Double score = Double.parseDouble(elem.html().split(":")[1].trim());
				return score;
			}
			catch (NumberFormatException e) {
				//null result
				return 0.0;
			}
			catch (SocketTimeoutException e) {
				warn(noun + " " + compareNoun + " time out! Redo ...");
			}
			catch (ConnectException e) {
				warn(noun + " " + compareNoun + " refused! Redo ...");
			}
			catch (Exception e) {
				e.printStackTrace();
				return 0.0;
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
//		String[] word1 = new String[]{"room", "dog", "cat", "expensive", "price", "pig", "room", "guy", "mean", "where"};
//		String[] word2 = new String[]{"house", "wolf", "cat", "the", "expensive", "cat", "bed", "cloth", "average", "who"};
//		int total = word1.length;
//		//String[] except1 = new String[]{"night", "hotel", "location", "clean", "room", "size", "bathroom", "towel"};
//		
//		String word = "room";
//		NounSimilarityResult p = new NounSimilarityResult(word);
//		
//		Thread[] a = new Thread[total];
//		for (int i = 0; i < total; i++) {
//			a[i] = new Thread(new WikiLSA(word, word2[i], p));
//			a[i].start();
//		}
//		
//		//Thread.sleep(500);
//		Thread.yield();
//		
//		for (int i = 0; i < total; i++) {
//			a[i].join();
//		}
//		
//		System.out.println(p);
		
		/*
		Long t1 = System.nanoTime();
		System.out.println(post.test("room", "house"));
		System.out.println(post.test("dog", "wolf"));
		System.out.println(post.test("dog", "cat"));
		System.out.println(post.test("expensive", "price"));
		System.out.println(post.test("price", "high"));
		System.out.println(post.test("price", "the"));
		Long t2 = System.nanoTime();
		System.out.println((t2 - t1) / 1000000000.0);
		 */
	}
}
