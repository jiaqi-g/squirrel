package ucla.google;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	
	public Main() {
		
	}
	
	public List<String> search(String query) throws IOException {
		String code = URLEncoder.encode(query, "UTF-8");
		System.out.println("[INFO] encoded query: " + code);
		
		String url = "https://www.google.com/search?q=" + code;
		/**
		 * add userAgent header to deceive google search engine
		 */
		Document document = Jsoup.connect(url).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.116 Safari/537.36").get();
				
		String cssQuery = "div[data-async-econtext^=query:]";
		Element elem = document.select(cssQuery).get(0);
		
		String cssQuery2 = "div[class=rc]";
		Elements elems = elem.select(cssQuery2);
		
		List<String> res = new ArrayList<String>();
		
		for (Element e: elems) {
			res.add(e.child(0).select("a[href]").attr("href").trim());
		}
		
		//String text = Jsoup.parse(elem.text().replaceAll("(?i)<br[^>]*>", "br2n")).text();
		return res;
		//return elems.toString();
		
		//Element elemAns = elem.children().get(3);
		//System.out.println(elemAns);
		
		//String html = "<html><head><title>First parse</title></head>" + "<body><p>Parsed HTML into a doc.</p></body></html>";
		//Document document = Jsoup.parse(html);
				
		//String html = response.getContentAsString();  
		//Document document = Jsoup.parse(html);  
		
		//Elements elements = document.select("#errorRef");
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(new Main().search("update java alternatives"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}