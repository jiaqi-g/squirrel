package squirrel.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import common.FileSystem;

/**
 * Utility class for predefined adjectives synonyms
 * @author victor
 *
 */
public class SynonymsUtil {
	
	static String synonymsFileName = "python/synnoms.txt";
	static List<List<String>> synonymsGroups = new ArrayList<List<String>>();
	
	static {
		try {
			loadSynonymsFromFile();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Synonyms File: a manually built file with adjective synonyms.
	 * @throws IOException
	 */
	private static void loadSynonymsFromFile() throws IOException {
		List<String> lines = FileSystem.readAllLines(synonymsFileName);
		for (String line: lines) {
			List<String> lst = new ArrayList<String>();
			String[] tmp = line.split(",");
			for (String adj: tmp) {
				lst.add(adj.trim());
			}
			synonymsGroups.add(lst);
		}
	}
	
	/**
	 * Get a group of adjs with similar names to the given one, return itself if no synonyms
	 * @param trait
	 * @return
	 */
	public static List<String> getSynonyms(String trait) {
		for (List<String> lst : synonymsGroups) {
			if (lst.contains(trait)) {
				return lst;
			}
		}
		List<String> lst = new ArrayList<String>();
		lst.add(trait);
		return lst;
	}
}
