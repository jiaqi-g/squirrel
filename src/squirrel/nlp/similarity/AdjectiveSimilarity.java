package squirrel.nlp.similarity;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import common.Log;

/**
 * UMBC Top-N Similarity Service as adjective similarity service
 * @author victor
 *
 */
public class AdjectiveSimilarity {

	public static String url = "http://swoogle.umbc.edu/SimService/GetSimilarity";
	public static Integer topN = 50;

	private WordSimilarityResultSet scoreMap;
	private String adj;

	public AdjectiveSimilarity(String adj) {
		this.scoreMap = new WordSimilarityResultSet(adj);
		this.adj = adj;
		
		//add itself
		scoreMap.add(adj, 1.0);
	}

	private static void warn(String string) {
		Log.warn("[UMBC]", string);
	}

	/**
	 *
	 * @param noun
	 * @param compareNoun
	 * @return
	 */
	public WordSimilarityResultSet getTopSimilaryWordsFromWeb() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("operation", "top_sim");
		data.put("word", adj);
		data.put("pos", "JJ");
		data.put("N", topN + "");
		data.put("sim_type", "concept");
		data.put("corpus", "webbase");
		data.put("query", "Get Top-N Most Similar Words");

		try {
			Document document = Jsoup.connect(url)
					.data(data)
					//.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.116 Safari/537.36")
					.post();

			String cssQuery = "textarea";
			String[] tmpLst = document.select(cssQuery).html().split(",");
			if (!tmpLst[0].startsWith("Warning:")) {
				for (int i=0; i<tmpLst.length; i++) {
					String pair = tmpLst[i].trim();
					if (pair != "") {
						String[] tuple = pair.split(" ");
						scoreMap.add(tuple[0].split("_")[0], Double.parseDouble(tuple[1]));
					}
				}
			}
		}
		catch (NumberFormatException e) {
			//null result
			//return 0.0;
		}
		catch (SocketTimeoutException e) {
			warn(adj + " time out! Redo ...");
		}
		catch (ConnectException e) {
			warn(adj + " refused! Redo ...");
		}
		catch (Exception e) {
			e.printStackTrace();
			//return 0.0;
		}

		return scoreMap;
	}

	public static void main(String[] args) throws Exception {
		System.out.println((new AdjectiveSimilarity("superb")).getTopSimilaryWordsFromWeb());
	}
}