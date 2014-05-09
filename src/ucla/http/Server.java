package ucla.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sun.misc.IOUtils;
import ucla.crawlerEngine.AbstractEngine;
import ucla.crawlerEngine.EngineInfo;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;
import com.sun.net.httpserver.*;

public class Server {

	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/stackoverflow", new MyHandler());
		HttpContext context = server.createContext("/search", new SearchHandler());

		server.createContext("/", new MyHandler());
		server.setExecutor(null); // creates a default executor

		context.getFilters().add(new ParameterFilter());

		System.out.println("Server starts ... ");
		server.start();
	}

	static class SearchHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			
			Map<String, Object> params = (Map<String, Object>) t.getAttribute("parameters");
			String query = (String) params.get("content");
			
			Map<EngineInfo, List<String>> engineMap = ucla.crawlerEngine.EngineUtil.getEngineURLMap(new ucla.crawlerEngine.GoogleCrawler().getResultUrls(query));
			Set<EngineInfo> engines = engineMap.keySet();
			
			StringBuilder res = new StringBuilder();
			for (EngineInfo engine: engines) {
				List<String> urls = engineMap.get(engine);
				AbstractEngine searchEngine = engine.newInstance();
				System.out.println("[INFO] " + searchEngine.name() + ": " + urls);
				
				String url = "";
				String content = "";

				//search from the first until got content
				int index = 0;
				while (index < urls.size()) {
					url = urls.get(index);
					content = searchEngine.search(url);

					if (content.length() < 1) {
						index += 1;
					} else {
						break;
					}
				}
				
				if (content.length() < 1) {
					url = "No URL";
					content = "No matched content";
				}
				
				res.append(PageHelper.getOutlinePart(url, content));
			}
			
			//encode to utf-8 to avoid errors
			ByteBuffer rs = Charset.forName("UTF-8").encode(PageHelper.makeOutlinePage(res));
			//String response = rs.toString();
			//System.out.println(response);
			
			t.sendResponseHeaders(200, rs.capacity());
			OutputStream os = t.getResponseBody();
			os.write(rs.array());
			os.close();
		}
	}

	static class DefaultHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			String response = PageHelper.getDefaultPage("Default page");
			//response = new ucla.stackoverflow.Main().test();

			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	static class MyHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			String content = "<form name=\"input\" action=\"search\" method=\"get\">"
					+ "Content: <input type=\"text\" name=\"content\">"
					+ "<input type=\"submit\" value=\"Submit\" />"
					+ "</form></body></html>";
			String response = PageHelper.getDefaultPage(content);

			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

}