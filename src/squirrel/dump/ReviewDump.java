package squirrel.dump;

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
import squirrel.nlp.similarity.WordSimilarityResultSet;
import squirrel.nlp.similarity.NounSimilarity;
import squirrel.parse.ReviewList;
import squirrel.parse.TripAdvisorReview;

/**
 * Utility class for loading reviews into memory for later manipulation.
 * 
 * @author victor
 *
 */
public class ReviewDump {
	/**
	 * example dump these hotelIds
	 */
	private static int[] ids = new int[]{80112, 93338, 93396, 98952, 239853, 1966350, 1762573, 1776857, 1200103, 1158926};
	
	private static void log(String string) {
		Log.log("[REVIEW]", string);
	}
	
	public static ReviewList loadReviewFiles(String reviewFilePath, ArrayList<Integer> hotelIds) {
		log("load review file...");
		ReviewList reviews = new ReviewList();
		
		try {
			Scanner scanner =  new Scanner(FileSystem.getFile(reviewFilePath), StandardCharsets.UTF_8.name());
			int cnt = 1;
			while (scanner.hasNextLine()){
				TripAdvisorReview review = JsonToJavaUtil.getTripAdvisorReviewBean(scanner.nextLine().trim());
				
				if (hotelIds.contains(review.getOfferingId())) {
					review.replaceNonEnglishWords();
					review.transformTextToSentences();
					
					reviews.add(review);
					log("process " + cnt + " " + (review.isNonEnglish? "true" : ""));
				}
				else {
					log("skip " + cnt);
				}
				
				cnt += 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reviews;
	}
	
	public static void dump(ReviewList reviews) {
		DumpTextUtil.dumpReviewsToFolder("dump_reviews", reviews);
	}
	
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("usage: java -jar dump.jar dataset_path hotel_id1 hotel_id2 ...");
			System.exit(0);
		}
		
		String reviewFilePath = args[0];
		ArrayList<Integer> hotelIds = new ArrayList<Integer>();
		for (int i=1; i<args.length; i++) {
			hotelIds.add(Integer.parseInt(args[i]));
		}
		
		ReviewDump.dump(ReviewDump.loadReviewFiles(reviewFilePath, hotelIds));
	}
}
