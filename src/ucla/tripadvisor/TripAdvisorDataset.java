package ucla.tripadvisor;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import ucla.util.FileUtil;
import ucla.util.JsonToJavaUtil;

public class TripAdvisorDataset {
	public static final String hotelPrefix = "hotel";
	public static final String reviewPrefix = "review";
	//public static final String regex = "\\.|!|,|\\-";
	public static final String regex = "\\.|!";
	
	public String reviewRoot = "reviews";
	public String datasetPath = "docs/review.txt";
	public boolean splitBySentence = false;
	//"docs/hotel_93396_review.txt";
	public int cnt = 0;
	
	public TripAdvisorDataset() {
	}

	public TripAdvisorDataset(String datasetPath, String reviewRoot, boolean splitBySentence) {
		this.datasetPath = datasetPath;
		this.reviewRoot = reviewRoot;
		this.splitBySentence = splitBySentence;
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
		FileUtil.createFolder(reviewRoot);

		/**
		 * for memory issues, we process every time we read one line
		 */
		//List<String> lst = new ArrayList<String>();
		Path path = Paths.get(datasetPath);

		try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())){
			while (scanner.hasNextLine()){
				createReview(JsonToJavaUtil.getTripAdvisorReviewBean(scanner.nextLine().trim().toLowerCase()));
			}
		}
	}

	private void createReview(TripAdvisorReviewBean review) throws Exception {
		System.out.println(cnt++);

		Integer hotelId = review.getOfferingId();
		Long reviewId = review.getId();

		String folderPath = reviewRoot + "/" + hotelPrefix + "_" + hotelId;
		FileUtil.createFolder(folderPath);

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
		
		FileUtil.writeFile(
				FileUtil.deleteAndCreateNewFile(filePath), text);
	}

	public static void main(String[] args) {
		try {
			//new TripAdvisorDataset().split();
			new TripAdvisorDataset("docs/hotel_93396_review.txt", "sample", true).split();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

