package squirrel.nlp.similarity;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import common.Log;

public class WikiLSA implements Runnable {

	static String url = "http://deeptutor2.memphis.edu/WikiLSA/getSimilarity";
	Map<String, String> data = new HashMap<String, String>();
	NounSimilarityResult scoreMap;
	
	public WikiLSA(NounSimilarityResult scoreMap) {
		//set words late for object reuse
		this.scoreMap = scoreMap;
	}
	
	public WikiLSA(String w1, String w2, NounSimilarityResult scoreMap) {
		setWords(w1, w2);
		this.scoreMap = scoreMap;
	}
	
	private static void log(String string) {
		Log.log("[WIKILSA]", string);
	}
	
	public void setWords(String w1, String w2) {
		data.put("word1", w1);
		data.put("word2", w2);
	}
	
	public void multiThreads() {
		/*
		//use multi-threading to calculate the similarity score from WikiLSA
		Thread[] threads = new Thread[relatedWords.size()];
		
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new WikiLSA(noun, relatedWords.get(i), resultSet));
			threads[i].start();
		}
		
		Thread.yield();
		
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
	}

	/**
	 * You can directly call this utility function in a single thread.
	 */
	public void retrieveScoreFromWeb() {
		try {
			Document document = Jsoup.connect(url)
					.data(data)
					//.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.116 Safari/537.36")
					.post();

			String cssQuery = "p";
			Element elem = document.select(cssQuery).get(3);
			Double score = Double.parseDouble(elem.html().split(":")[1].trim());
			scoreMap.add(data.get("word2"), score);
			log(data.get("word1") + " " + data.get("word2") + " " + score);
			//return score;
		}
		catch (NumberFormatException e) {
			//null result, do nothing
		}
		catch (Exception e) {
			e.printStackTrace();
			//return new Double(0);
		}
	}

	@Override
	public void run() {
		retrieveScoreFromWeb();
	}

	public static void main(String[] args) throws Exception {
		String[] word1 = new String[]{"room", "dog", "cat", "expensive", "price", "pig", "room", "guy", "mean", "where"};
		String[] word2 = new String[]{"house", "wolf", "cat", "the", "expensive", "cat", "bed", "cloth", "average", "who"};
		int total = word1.length;
		//String[] except1 = new String[]{"night", "hotel", "location", "clean", "room", "size", "bathroom", "towel"};
		
		String word = "room";
		NounSimilarityResult p = new NounSimilarityResult(word);
		
		Thread[] a = new Thread[total];
		for (int i = 0; i < total; i++) {
			a[i] = new Thread(new WikiLSA(word, word2[i], p));
			a[i].start();
		}
		
		//Thread.sleep(500);
		Thread.yield();
		
		for (int i = 0; i < total; i++) {
			a[i].join();
		}
		
		System.out.println(p);
		
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
