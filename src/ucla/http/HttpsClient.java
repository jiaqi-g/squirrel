package ucla.http;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.io.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class HttpsClient{

	private void testIt() {
		String http_url = "http://api.diffbot.com/v2/article?token=74d98b7aeb7c985a157449137b1068c2&url=http%3A%2F%2Fblog.diffbot.com%2Fusing-customize-and-correct-to-make-instant-api-fixes%2F";
		//String https_url = "https://baidu.com";
		URL url;
		try {
			//url = new URL(https_url);
			url = new URL(http_url);
			//HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			//dumpl all cert info
			//print_https_cert(con);
			//dump all the content
			print_content(con);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void print_https_cert(HttpsURLConnection con) {
		if(con!=null){
			try {
				System.out.println("Response Code : " + con.getResponseCode());
				System.out.println("Cipher Suite : " + con.getCipherSuite());
				System.out.println("\n");

				Certificate[] certs = con.getServerCertificates();
				for(Certificate cert : certs){
					System.out.println("Cert Type : " + cert.getType());
					System.out.println("Cert Hash Code : " + cert.hashCode());
					System.out.println("Cert Public Key Algorithm : " 
							+ cert.getPublicKey().getAlgorithm());
					System.out.println("Cert Public Key Format : " 
							+ cert.getPublicKey().getFormat());
					System.out.println("\n");
				}
			} catch (SSLPeerUnverifiedException e) {
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}

	private void print_content(HttpURLConnection con) {
		if(con!=null){
			try {
				System.out.println("****** Content of the URL ********");			
				BufferedReader br = 
						new BufferedReader(
								new InputStreamReader(con.getInputStream()));

				String input;

				while ((input = br.readLine()) != null){
					System.out.println(input);
				}
				br.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new HttpsClient().testIt();
	}

}
