package squirrel.nlp.similarity;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import common.FileSystem;

class Match implements Runnable {
	Integer partId;
	List<String> part;
	List<String> frequentWords;
	String folderPath;
	
	public Match(Integer partId, List<String> part, List<String> frequentWords) {
		this.partId = partId;
		this.part = part;
		this.frequentWords = frequentWords;
		
		folderPath = "words/part_" + partId;
		FileSystem.createFolder(folderPath);
	}

	@Override
	public void run() {
		try {
			for (String word: part) {
				//File f = FileSystem.getFile(folderPath + "/" + word + ".txt");
				//FileSystem.createNewFile(fileName);
				NounSimilarityResult similarityResult = new NounSimilarityResult(word);
				WikiLSA lsa = new WikiLSA(similarityResult);
				for (String word2: frequentWords) {
					lsa.setWords(word, word2);
					lsa.retrieveScoreFromWeb();
					//Double score = lsa.retrieve();
					//f.write(word2 + "," + score + "\n");
				}
				//f.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/**
 * Path: words/part_id/word.txt
 * @author victor
 *
 */
public class WordSimilarity {
	String wordFile = "words.txt";

	private List<List<String>> partitionList(List<String> originalList, int partitionSize) {
		List<List<String>> partitions = new LinkedList<List<String>>();
		for (int i = 0; i < originalList.size(); i += partitionSize) {
			partitions.add(originalList.subList(i,
					i + Math.min(partitionSize, originalList.size() - i)));
		}
		return partitions;
	}

	private List<String> getFrequentWords() {
		try {
			return FileSystem.readAllLines(wordFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public void process(int partitionSize) {
		List<String> frequentWords = getFrequentWords();
		List<List<String>> partitions = partitionList(frequentWords, partitionSize);

		int i = 0;
		for (List<String> part: partitions) {
			// TODO: modify to the thread pool
			new Thread(new Match(i, part, frequentWords)).start();
			i += 1;
		}
	}
	
	public static void main(String[] args) throws Exception {
		new WordSimilarity().process(1);
	}
}
