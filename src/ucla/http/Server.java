package ucla.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;

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
            Map<String, Object> params = (Map<String, Object>)t.getAttribute("parameters");
            String query = (String) params.get("content");
            String url = "";
            
            String content = new ucla.askubuntu.Main(url).test();
            String response = PageHelper.getOutlinePage(url, content);
            //System.out.println(response);
            
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
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