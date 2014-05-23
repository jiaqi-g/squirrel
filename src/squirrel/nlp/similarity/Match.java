package squirrel.nlp.similarity;

import java.io.File;
import java.util.List;

import common.FileSystem;

public class Match implements Runnable {
	String noun;
	List<String> allWords;
	int id;
	
	public static final String folderName = "scores";
	
	static {
		FileSystem.createFolder(folderName);
	}
	
	public Match(int id, String noun, List<String> allWords) {
		this.id = id;
		this.noun = noun;
		this.allWords = allWords;
	}

	@Override
	public void run() {
		try {
			File f	= FileSystem.createNewFile(folderName + "/" + noun + ".txt");
			
			WikiLSA lsa = new WikiLSA(noun);
			NounSimilarityResult resultSet = lsa.getSimilarityResult(id, allWords);
			String res = resultSet.getPrettyString();
			
			FileSystem.writeFile(f, res);
			
			System.out.println("[Match] word: " + id + " finished");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}