package ucla.http;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.google.gson.Gson;

public class GoogleResults {

	final static String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0";
	final static String charset = "UTF-8";

	private ResponseData responseData;
	public ResponseData getResponseData() { return responseData; }
	public void setResponseData(ResponseData responseData) { this.responseData = responseData; }
	public String toString() { return "ResponseData[" + responseData + "]"; }

	static class ResponseData {
		private List<Result> results;
		public List<Result> getResults() { return results; }
		public void setResults(List<Result> results) { this.results = results; }
		public String toString() { return "Results[" + results + "]"; }
	}

	static class Result {
		private String url;
		private String title;
		public String getUrl() { return url; }
		public String getTitle() { return title; }
		public void setUrl(String url) { this.url = url; }
		public void setTitle(String title) { this.title = title; }
		public String toString() { return "Result[url:" + url +",title:" + title + "]"; }
	}

	static List<Result> searchGoogle(String searchString, int startIndex) throws Exception {
		URL url = new URL(google + "&start=" + startIndex +"&q=" + URLEncoder.encode(searchString, charset));
		Reader reader = new InputStreamReader(url.openStream(), charset);
		GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);

		return results.getResponseData().getResults();
	}

	public static void main(String[] args) throws Exception {

		String searchString = "java code search google";
		//String searchString = "noodles";
		
		int start = 0;
		for (int i=0; i<2; i++) {
			List<Result> lst = GoogleResults.searchGoogle(searchString, start);

			// Show title and URL of 1st result.
			for (int k=0; k<lst.size(); k++) {
				System.out.println("===" + (start + k) + "===");
				System.out.println(lst.get(k).getTitle());
				System.out.println(lst.get(k).getUrl());
			}

			start += lst.size();
		}

	}

}