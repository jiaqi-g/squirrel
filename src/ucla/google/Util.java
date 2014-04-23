package ucla.google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ucla.engine.*;

public class Util {

	public static final String ASKUBUNTU = "askubuntu";
	public static final String YELP = "yelp";
	public static final String STACKOVERFLOW = "stackoverflow";
	
	static Map<String, EngineInfo> engineMap = new HashMap<String, EngineInfo>();
	static Map<EngineInfo, List<String>> res = new HashMap<EngineInfo, List<String>>();
	
	static {
		register(ASKUBUNTU, AskUbuntuEngine.class);
		//register(STACKOVERFLOW, );
		//register(YELP, );
	}
	
	static void register(String websiteName, Class<? extends AbstractEngine> engineClass) {
		engineMap.put(websiteName, new EngineInfo(engineClass));
	}
	
	public static Map<EngineInfo, List<String>> getEngineURLMap(List<String> urls) {
		res = new HashMap<EngineInfo, List<String>>();
		
		for (String url: urls) {
			if (contains(url, ASKUBUNTU)) {
				process(engineMap.get(ASKUBUNTU), url);
			}
			/*
			else if (contains(url, YELP)) {
				process(engineMap.get(YELP), url);
			} else if (contains(url, STACKOVERFLOW)) {
				process(engineMap.get(STACKOVERFLOW), url);
			}*/
		}
		
		return res;
	}
	
	private static void process(EngineInfo engineName, String url) {
		//System.out.println("!!!" + engineName);
		
		List<String> lst = res.get(engineName);
		
		if (lst == null) {
			lst = new ArrayList<String>();
			res.put(engineName, lst);
			
		}
		
		lst.add(url);
	}
	
	private static boolean contains(String url, String label) {
		return url.contains(label);
	}

	
	
}