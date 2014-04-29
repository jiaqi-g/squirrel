package ucla.tripadvisor;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class TripAdvisorReviewObj {
	private String title;
	private String text;
	//private String author;
	private String date_stayed;
	private Integer offering_id;
	
	// Getters and setters are not required for this example.
	// GSON sets the fields directly using reflection.
	@Override
	public String toString() {
		return offering_id + " " + title + " " + text + " " + " " + date_stayed;
	}
}

public class JsonToJava {

	public static String test(String jsonObj) {
		Gson gson = new GsonBuilder().create();
		TripAdvisorReviewObj p = gson.fromJson(jsonObj, TripAdvisorReviewObj.class);
		return p.toString();
	}
	
	public static void main(String[] args) throws IOException {
		String aFileName = "hotel_93396_review.txt";
	    Path path = Paths.get(aFileName);
	    try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())){
	      while (scanner.hasNextLine()){
	    	  System.out.println(test(scanner.nextLine()));
	      }
	    }
	}
}
