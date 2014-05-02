package ucla.tripadvisor;

import java.util.Iterator;

import ucla.util.FileUtil;
import ucla.util.JsonToJavaUtil;

public class TripAdvisorDataset {

	public static final String reviewRoot = "reviews";
	public static final String hotelPrefix = "hotel";
	public static final String reviewPrefix = "review";

	public String datasetPath = "docs/review.txt";
			//"docs/hotel_93396_review.txt";
	public int cnt = 0;
	
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
		FileUtil.createFolder(reviewRoot);

		Iterator<String> lines = FileUtil.readByLines(datasetPath).iterator();
		while (lines.hasNext()){
			createReview(JsonToJavaUtil.getTripAdvisorReviewBean(lines.next()));
		}
	}

	private void createReview(TripAdvisorReviewBean review) throws Exception {
		System.out.println(cnt++);
		
		Integer hotelId = review.getOfferingId();
		Long reviewId = review.getId();

		String folderPath = reviewRoot + "/" + hotelPrefix + "_" + hotelId;
		FileUtil.createFolder(folderPath);

		String filePath = folderPath + "/" + reviewPrefix + "_" + reviewId;
		FileUtil.writeFile(
				FileUtil.deleteAndCreateNewFile(filePath), review.getText());
	}

	public static void main(String[] args) {
		try {
			new TripAdvisorDataset().split();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

