package squirrel.common;

import java.util.HashMap;
import java.util.Map;

public class HotelMap {
	static Map<String, Integer> hotelMap;
	
	static {
		hotelMap = new HashMap<String, Integer>();
		hotelMap.put("San Carlos Hotel", 80112);
		hotelMap.put("The Iroquois", 93396);
		hotelMap.put("Hotel Beacon", 93338);
	}
	
	public static Integer getHotelIdByName(String name) {
		name = name.trim();
		Log.log(name);
		for (String s :hotelMap.keySet()) {
			if (s.replace(" ", "").toLowerCase().contains(name)) {
				return hotelMap.get(s);
			}
		}
		return null;
	}
}