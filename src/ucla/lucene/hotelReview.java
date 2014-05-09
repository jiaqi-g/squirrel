package ucla.lucene;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import squirrel.parse.TripAdvisorReview;

public class hotelReview {

	static Gson gson = new GsonBuilder().create();
	
	public TripAdvisorReview getTripAdvisorReview(String jsonObj) {
		TripAdvisorReview p = gson.fromJson(jsonObj, TripAdvisorReview.class);
		return p;
	}
	public void insertHotelReview() throws Exception {
		String aFileName = "docs/hotel_93396_review.txt";
		ArrayList<TripAdvisorReview> reviewList = new ArrayList<TripAdvisorReview>();
	    Path path = Paths.get(aFileName);
	    try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())){
	      while (scanner.hasNextLine()){
	    	  TripAdvisorReview review= new TripAdvisorReview();
	    	  
	    	  	review = getTripAdvisorReview(scanner.nextLine());
	    	  	reviewList.add(review);
	      }
	      
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   DB.insertHotelReview(reviewList);
	    
	}
	

}
