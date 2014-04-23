package ucla.askubuntu;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	
	String url;
	
	public Main(String url) {
		this.url = url;
	}
	
	public String test() throws IOException {
		Document document = Jsoup.connect(url).get();
		
		String cssQuery = "div[id^=answer-]";
		//String cssQuery = "div[class=answer accepted-answer]";
		Element elem = document.select(cssQuery).get(0);
		
		String cssQuery2 = "div[class=post-text]";
		elem = elem.select(cssQuery2).get(0);
		
		//String text = Jsoup.parse(elem.text().replaceAll("(?i)<br[^>]*>", "br2n")).text();
		
		return elem.toString();
		
		//Element elemAns = elem.children().get(3);
		//System.out.println(elemAns);
		
		//String html = "<html><head><title>First parse</title></head>" + "<body><p>Parsed HTML into a doc.</p></body></html>";
		//Document document = Jsoup.parse(html);
				
		//String html = response.getContentAsString();  
		//Document document = Jsoup.parse(html);  
		
		//Elements elements = document.select("#errorRef");
	}
	
	public static void main(String[] args) {
		String url = "http://askubuntu.com/questions/315646/update-java-alternatives-vs-update-alternatives-config-java";
		//String url = "http://askubuntu.com/questions/56104/how-can-i-install-sun-oracles-proprietary-java-jdk-6-7-8-or-jre";
		try {
			System.out.println(new Main(url).test());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}