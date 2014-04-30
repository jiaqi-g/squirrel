package ucla.http;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HttpPost {
	
	public HttpPost() {
		
	}
	
	public Double test(String w1, String w2) throws Exception {
		String url = "http://deeptutor2.memphis.edu/WikiLSA/getSimilarity";
		Map<String, String> data = new HashMap<String, String>();
		data.put("word1", w1);
		data.put("word2", w2);
		
		Document document = Jsoup.connect(url)
				.data(data)
				//.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.116 Safari/537.36")
				.post();
		
		String cssQuery = "p";
		Element elem = document.select(cssQuery).get(3);
		
		return Double.parseDouble(elem.html().split(":")[1].trim());
	}
	
	public static void main(String[] args) throws Exception {
		HttpPost post = new HttpPost();
		
		System.out.println(post.test("room", "house"));
		System.out.println(post.test("dog", "wolf"));
		System.out.println(post.test("dog", "cat"));
	}
}
