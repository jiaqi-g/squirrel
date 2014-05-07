package ucla.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import ucla.engine.DiffBotBean;
import ucla.tripadvisor.TripAdvisorReview;

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
	    Path path = Paths.get(aFileName);
	    try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())){
	      while (scanner.hasNextLine()){
	    	  System.out.println(getTripAdvisorReviewBean(scanner.nextLine()));
	      }
	    }
	}

}