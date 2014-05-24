package squirrel.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import common.FileSystem;

import squirrel.nlp.similarity.Match;

public class SimpleThreadPool {
	
	public static List<List<String>> partitionWordList(List<String> lst, int numOfPieces) {
		List<List<String>> partitions = new ArrayList<List<String>>();
		for (int i=0; i<numOfPieces; i++) {
			partitions.add(new ArrayList<String>());
		}
		
		for (int i=0; i<lst.size(); i++) {
			partitions.get(i % numOfPieces).add(lst.get(i));
		}
		
		return partitions;
	}
	
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.println("usage: java -jar lsa.jar nouns_file max_thread_number");
			System.exit(0);
		}
		
		String nounsFileName = args[0];
		Integer threadNum = Integer.parseInt(args[1]);
		
		List<String> words = FileSystem.readAllLines(nounsFileName);
		
		ExecutorService executor = Executors.newFixedThreadPool(threadNum);
		for (int i = 0; i < words.size(); i++) {
			Runnable worker = new Match(i, words.get(i), words);
			executor.execute(worker);
		}
		executor.shutdown();
		
		while (!executor.isTerminated()) {
		}
		
		System.out.println("[Main] Finished all threads");
	}
}