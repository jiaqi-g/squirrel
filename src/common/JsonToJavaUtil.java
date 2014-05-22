package common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import squirrel.parse.TripAdvisorReview;
import ucla.crawlerEngine.DiffBotBean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonToJavaUtil {

	static Gson gson = new GsonBuilder().create();
	
	public static TripAdvisorReview getTripAdvisorReviewBean(String jsonObj) {
		TripAdvisorReview p = gson.fromJson(jsonObj, TripAdvisorReview.class);
		return p;
	}
	
	public static DiffBotBean getDiffBotBean(String jsonObj) {
		Gson gson = new GsonBuilder().create();
		DiffBotBean p = gson.fromJson(jsonObj, DiffBotBean.class);
		return p;
	}
	
	public static void main(String[] args) throws IOException {
		String aFileName = "docs/hotel_93396_review.txt";
		
	    try {
			Scanner scanner =  new Scanner(FileSystem.getFile(aFileName), StandardCharsets.UTF_8.name());
	      while (scanner.hasNextLine()){
	    	  System.out.println(getTripAdvisorReviewBean(scanner.nextLine()));
	      }
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
}