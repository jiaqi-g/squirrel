package squirrel.tripadvisor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import common.FileSystem;
import common.JsonToJavaUtil;

import squirrel.parse.TripAdvisorReview;

public class Dataset {
	public static final String hotelPrefix = "hotel";
	public static final String reviewPrefix = "review";
	//public static final String regex = "\\.|!|,|\\-";
	public static final String regex = "\\.|!";
	
	public String reviewRoot;
	public String datasetPath;
	public boolean splitBySentence;
	
	private List<TripAdvisorReview> data;
	
	public Dataset() {
		this("docs/review_first_1000.txt", "reviews", false);
		//"docs/hotel_93396_review.txt";
	}

	public Dataset(String datasetPath, String reviewRoot, boolean splitBySentence) {
		this.datasetPath = datasetPath;
		this.reviewRoot = reviewRoot;
		this.splitBySentence = splitBySentence;
		this.data = new ArrayList<TripAdvisorReview>();
		FileSystem.createFolder(reviewRoot);
	}
	
	/**
	 * Split the raw data set into folders and files hierachy. Currently supports content of reviews and UTF-8 encoding
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
	public void split() throws Exception {
		for (TripAdvisorReview review: data) {
			Integer hotelId = review.getOfferingId();
			Long reviewId = review.getId();

			String folderPath = reviewRoot + "/" + hotelPrefix + "_" + hotelId;
			FileSystem.createFolder(folderPath);

			String filePath = folderPath + "/" + reviewPrefix + "_" + reviewId;
			
			String text = review.getText();
			if (splitBySentence) {
				String[] tokens = text.split(regex);
				StringBuilder builder = new StringBuilder();
				for (String token: tokens) {
					if (token.length() > 5) {
						builder.append(token.trim() + "\n");
					}
				}
				text = builder.toString();
			}
			
			FileSystem.writeFile(
					FileSystem.createNewFile(filePath), text);
		}
	}
	
	/**
	 * Dump all review text into a single file
	 */
	public void dumpAllText(String outputFilename) {
		try {
			StringBuilder builder = new StringBuilder();
			for (TripAdvisorReview review: data) {
				builder.append(review.getText());
				builder.append("\n");
			}
			FileSystem.writeFile(FileSystem.createFile(outputFilename), builder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void log(String s) {
		System.out.println("[Dataset] " + s);
	}
	
	public static void main(String[] args) {
		try {
			Dataset dataset = new Dataset();
			//new Dataset("docs/hotel_93396_review.txt", "sample", true).split();
			dataset.dumpAllText("reviews/first_1000_text.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
