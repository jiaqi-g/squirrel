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

public class JsonToJava {

	static Gson gson = new GsonBuilder().create();
	
	public static TripAdvisorReviewBean test(String jsonObj) {
		TripAdvisorReviewBean p = gson.fromJson(jsonObj, TripAdvisorReviewBean.class);
		return p;
	}
	
	public static void main(String[] args) throws IOException {
		String aFileName = "docs/hotel_93396_review.txt";
	    Path path = Paths.get(aFileName);
	    try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())){
	      while (scanner.hasNextLine()){
	    	  System.out.println(test(scanner.nextLine()));
	      }
	    }
	}

}