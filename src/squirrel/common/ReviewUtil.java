package squirrel.common;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import common.FileSystem;
import common.JsonToJavaUtil;
import common.Log;

import squirrel.dump.DumpTextUtil;
import squirrel.nlp.NP;
import squirrel.nlp.Sentence;
import squirrel.nlp.similarity.NounSimilarityResult;
import squirrel.nlp.similarity.WikiLSA;
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
	//private static String reviewFilePath = "docs/review_sample";
	private static String reviewFilePath = "docs/review_first_1000.txt";
	//private static String reviewFilePath = "docs/review.txt";

	private static String nounAdjsFilePath = "nounAdjs.txt";
	private static ReviewList reviews = new ReviewList();

	static {
		try {
			loadReviewFiles();
			loadNounAdjsPairs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void log(String string) {
		Log.log("[REVIEW]", string);
	}

	private static void loadReviewFiles() throws Exception {
		log("load review file...");

		try {
			Scanner scanner =  new Scanner(FileSystem.getFile(reviewFilePath), StandardCharsets.UTF_8.name());
			int cnt = 1;
			while (scanner.hasNextLine()){
				TripAdvisorReview review = JsonToJavaUtil.getTripAdvisorReviewBean(scanner.nextLine().trim());
				review.replaceNonEnglishWords();
				review.transformTextToSentences();
				
				reviews.add(review);
				log("process " + cnt + " " + (review.isNonEnglish? "true" : ""));
				cnt += 1;
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
				String noun = nounAdjs[0].trim();
				String adjListString = nounAdjs[1].trim();
				sent.addNP(new NP(noun, adjListString));
			}

		}
	}

	/**
	 * This method will refer to the  similarity map in DB, Disk or WikiLSA website
	 * 
	 * @param noun
	 * @param relatedWords
	 * @return
	 */
	public static NounSimilarityResult getSimilarityBetweenNouns(String noun, List<String> relatedWords) {
		NounSimilarityResult rs = new NounSimilarityResult(noun);
		for (String word: relatedWords) {
			// TODO: modify this to reflect actual score read from db.
/*			WikiLSA lsa = new WikiLSA(rs);
			lsa.setWords(noun, word);
			lsa.retrieveScoreFromWeb();	*/
		}
		return rs;
	}

	public static ReviewList getReviews() {
		return reviews;
	}
}
