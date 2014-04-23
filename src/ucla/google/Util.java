package ucla.google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ucla.engine.*;

public class Util {
	
	static Map<String, EngineInfo> engineMap = new HashMap<String, EngineInfo>();
	static Map<EngineInfo, List<String>> res = new HashMap<EngineInfo, List<String>>();
	
	public static final String ASKUBUNTU = "askubuntu";
	public static final String YELP = "yelp";
	public static final String STACKOVERFLOW = "stackoverflow";
	
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
			Set<String> websiteNameSet = engineMap.keySet();
			
			for (String websiteName: websiteNameSet) {
				if (isRelevant(url, websiteName)) {
					process(engineMap.get(websiteName), url);
				}
			}
		}
		
		return res;
	}

	private static void process(EngineInfo engineInfo, String url) {
		List<String> lst = res.get(engineInfo);
		
		if (lst == null) {
			lst = new ArrayList<String>();
			res.put(engineInfo, lst);
		}
		
		lst.add(url);
	}
	
	/**
	 * currently if the url contains websiteName, then decide it's a relevant url
	 * 
	 * @param url
	 * @param websiteName
	 * @return
	 */
	private static boolean isRelevant(String url, String websiteName) {
		return url.contains(websiteName);
	}

}