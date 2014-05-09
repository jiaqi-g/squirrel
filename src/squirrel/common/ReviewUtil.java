package squirrel.common;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import common.FileSystem;
import common.JsonToJavaUtil;

import squirrel.nlp.NP;
import squirrel.nlp.Sentence;
import squirrel.nlp.similarity.NounSimilarityResult;
import squirrel.parse.ReviewList;
import squirrel.parse.TripAdvisorReview;

/**
 * Utility class for loading reviews into memory for later manipulation.
 * 
 * @author victor
 *
 */
public class ReviewUtil {
	
	//private static String reviewFolderPath = "sample/hotel_93396";
	private static String reviewFilePath = "docs/review_first_1000.txt";
	private static String nounAdjsFilePath = "map.txt";
	private static ReviewList reviews;
	
	static {
		try {
			loadReviewFiles();
			loadNounAdjsPairs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void loadReviewFiles() throws Exception {
		log("load review file...");
		
		try (Scanner scanner =  new Scanner(FileSystem.getFile(reviewFilePath), StandardCharsets.UTF_8.name())){
			int cnt = 1;
			while (scanner.hasNextLine()){
				TripAdvisorReview review = JsonToJavaUtil.getTripAdvisorReviewBean(scanner.nextLine().trim());
				review.replaceNonEnglishWords();
				review.transformTextToSentences();
				reviews.add(review);
				log("" + cnt++ + " " + (review.isNonEnglish? "true" : ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Format: reviewId; sentenceId; noun|adj1,adj2,adj3; noun|adj1,adj2,adj3; noun|adj1,adj2,adj3; ...
	 * 
	 * TODO: for more complex text inputs, using JSON instead
	 */
	private static void loadNounAdjsPairs() throws Exception {
		log("load noun-adj pairs file...");
		
		List<String> lines = FileSystem.readAllLines(nounAdjsFilePath);
		
		for (String line : lines) {
			String[] elems = line.split(";");

			Long reviewId = Long.parseLong(elems[0].trim());
			Integer sentenceId = Integer.parseInt(elems[1].trim());
	
			Sentence sent = reviews.get(reviewId).getSentence(sentenceId);
			for (int i = 2; i < elems.length; i++) {
				String[] nounAdjs = elems[i].split("\\|");
				String noun = nounAdjs[0];
				String adjListString = nounAdjs[1];
				sent.addNP(new NP(noun, adjListString));
			}
			
		}
	}
	
	/**
	 * This method will refer to the  similarity map in DB or on Disk Files
	 * 
	 * @param noun
	 * @param relatedWords
	 * @return
	 */
	public static NounSimilarityResult getSimilarityBetweenNouns(String noun, List<String> relatedWords) {
		NounSimilarityResult rs = new NounSimilarityResult(noun);
		for (String word: relatedWords) {
			// TODO: modify this to reflect actual score read from db.
			Double score = noun.equals(word)? 1.0 : 0.0;
			rs.add(word, score);	
		}
		return rs;
	}

	public static ReviewList getReviews() {
		return reviews;
	}
	
	private static void log(String string) {
		System.out.println("[Review] " + string);
	}
}
