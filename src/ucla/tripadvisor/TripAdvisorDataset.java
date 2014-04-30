package ucla.tripadvisor;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import ucla.util.FileUtil;
import ucla.util.JsonToJavaUtil;

public class TripAdvisorDataset {

	public static final String reviewRoot = "reviews";
	public static final String hotelPrefix = "hotel";
	public static final String reviewPrefix = "review";
	
	public String datasetPath = "docs/hotel_93396_review.txt";
	
	public TripAdvisorDataset() {
	}
	
	public TripAdvisorDataset(String datasetPath) {
		this.datasetPath = datasetPath;
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
	 * Warning: This method may take quite a long time to execute and overwrite any existing files!
	 */
	public void split() throws Exception {
		File theDir = new File(reviewRoot);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + reviewRoot);
			boolean result = theDir.mkdir();  

			if(result) {    
				System.out.println("DIR created");  
			}
		}
		
	    Path path = Paths.get(datasetPath);
	    try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())){
	      while (scanner.hasNextLine()){
	    	  createReview(JsonToJavaUtil.getTripAdvisorReviewBean(scanner.nextLine()));
	      }
	    }
	}
	
	private void createReview(TripAdvisorReviewBean review) throws Exception {
		Integer hotelId = review.getOfferingId();
		Long reviewId = review.getId();
		
		String folderPath = reviewRoot + "/" + hotelPrefix + "_" + hotelId;
		File dir = new File(folderPath);
		if (!dir.exists()) {
			System.out.println("creating directory: " + folderPath);
			dir.mkdir();  
		}
		
		String filePath = folderPath + "/" + reviewPrefix + "_" + reviewId;
		File reviewFile = new File(filePath);
		if (reviewFile.exists()) {
			reviewFile.delete();
		}
		
		reviewFile.createNewFile();
		
		FileUtil.write(reviewFile, review.getText());
		
	}

	public static void main(String[] args) {
		try {
			new TripAdvisorDataset().split();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

