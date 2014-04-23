package ucla.google;

import java.util.ArrayList;
import java.util.List;

public class Util {
	
	public static List<String> getAskUbuntu(List<String> urls) {
		List<String> res = new ArrayList<String>();
		for (String url: urls) {
			if (url.contains("askubuntu")) {
				res.add(url);
			}
		}
		return res;
	}
	
	public static List<String> getYelp(List<String> urls) {
		//TODO
		return null;
	}
	
}