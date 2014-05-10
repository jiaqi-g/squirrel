package squirrel.common;

import java.io.File;
import java.io.IOException;
import squirrel.parse.TripAdvisorReview;
import common.FileSystem;

/**
 * Utility class for dumping text into files for testing
 * @author victor
 *
 */
public class DumpTextUtil {
	
	static {
		try {
			Class.forName(ReviewUtil.class.getCanonicalName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Dump all review text into a single file
	 */
	public static void dumpReviewsToFile(String outputFilename) {
		try {
			StringBuilder builder = new StringBuilder();
			for (TripAdvisorReview review: ReviewUtil.getReviews()) {
				builder.append(review.getFormattedSentences());
				builder.append("\n");
			}
			FileSystem.writeFile(FileSystem.createFile(outputFilename), builder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Dump all reviews into folders and files hierachy. Currently supports content of reviews and UTF-8 encoding
	 * 
	 * --reviews
	 * ----hotel_2
	 * ------review_27
	 * ------review_64
	 * ----hotel_7
	 * ------review_86
	 * 
	 * Warning: This method may overwrite any existing files!
	 */
	public static void dumpReviewsToFolder(String rootPath) {
		try {
			FileSystem.createFolder(rootPath);
			
			for (TripAdvisorReview review: ReviewUtil.getReviews()) {
				String hotelFolderPath = rootPath + "/hotel_" + review.getOfferingId();
				FileSystem.createFolder(hotelFolderPath);
				
				String filePath = hotelFolderPath + "/review_" + review.getId();
				File reviewFile = FileSystem.createNewFile(filePath);
				
				FileSystem.writeFile(reviewFile, review.getFormattedSentences());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
