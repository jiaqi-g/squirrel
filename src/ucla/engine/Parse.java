package ucla.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.xml.sax.InputSource;

import de.l3s.boilerpipe.document.TextBlock;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.extractors.DefaultExtractor;
import de.l3s.boilerpipe.extractors.ArticleSentencesExtractor;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;

public class Parse {
	
	static String prefix = "http://api.diffbot.com/v2/article?token=";
	static String token = "74d98b7aeb7c985a157449137b1068c2";
	static String[] urls = new String[]{
		"http://blog.diffbot.com/using-customize-and-correct-to-make-instant-api-fixes/",
		"http://askubuntu.com/questions/315646/update-java-alternatives-vs-update-alternatives-config-java",
		"http://www.cnn.com/2014/04/23/world/asia/malaysia-airlines-plane/index.html?hpt=hp_inthenews",
		"http://www.quora.com/Content-Extraction/Whats-the-best-method-to-extract-article-text-from-HTML-documents",
		"http://www.weather.com/weather/tenday/Los+Angeles+CA+USCA0638",
		"http://www.amazon.com/gp/product/1118261364/ref=s9_simh_gw_p14_d0_i3?pf_rd_m=ATVPDKIKX0DER&pf_rd_s=center-2&pf_rd_r=14AM5W10QHVV71JCF28D&pf_rd_t=101&pf_rd_p=1688200382&pf_rd_i=507846",
	};
	
	public static String callBoiler(String u) throws Exception  {

		URL url = new URL(u);
        // NOTE We ignore HTTP-based character encoding in this demo...
        final InputStream urlStream = url.openStream();
        final InputSource is = new InputSource(urlStream);

        final BoilerpipeSAXInput in = new BoilerpipeSAXInput(is);
        final TextDocument doc = in.getTextDocument();
        urlStream.close();

        // You have the choice between different Extractors

        // System.out.println(DefaultExtractor.INSTANCE.getText(doc));
        //System.out.println(ArticleExtractor.INSTANCE.getText(doc));
        
        //List<TextBlock> blocks = doc.getTextBlocks();
        //System.out.println(ArticleExtractor.INSTANCE.getText(doc));
        //System.out.println(DefaultExtractor.INSTANCE.getText(doc));
        
        return ArticleSentencesExtractor.INSTANCE.getText(doc);
	}
	
	private static String getJSONContent(HttpURLConnection con){
		StringBuilder input = new StringBuilder();
		
		if(con!=null){
			try {
				BufferedReader br = 
						new BufferedReader(
								new InputStreamReader(con.getInputStream()));

				String r = "";
				while ((r = br.readLine()) != null){
					input.append(r);
				}
				br.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return input.toString();
	}
	
	public static String callDiffBot(String u) throws IOException  {
		String tmp = prefix + token + "&url=" + URLEncoder.encode(u, "UTF-8");
		System.out.println("[INFO] DiffBot Query API: " + tmp);
		
		URL url = new URL(tmp);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		
		//long millis = System.currentTimeMillis() % 1000;
		//System.out.println(getJSONContent(con));
		return JsonToJava.test(getJSONContent(con));
	}
	
	public static void main(String[] args) throws Exception {
		//System.out.println(callBoiler("https://help.ubuntu.com/community/Java"));
		//callDiffBot(urls[0]);
	}
}