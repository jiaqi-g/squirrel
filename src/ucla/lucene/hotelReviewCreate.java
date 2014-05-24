package ucla.lucene;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import squirrel.parse.TripAdvisorReview;

public class hotelReviewCreate {

	static Gson gson = new GsonBuilder().create();
	
	public TripAdvisorReview getTripAdvisorReview(String jsonObj) {
		TripAdvisorReview p = gson.fromJson(jsonObj, TripAdvisorReview.class);
		return p;
	}
	
	public void insertHotelReview(String aFileName) throws Exception {
		//String aFileName = "docs/hotel_93396_review.txt";
		ArrayList<TripAdvisorReview> reviewList = new ArrayList<TripAdvisorReview>();
	    Path path = Paths.get(aFileName);
	    try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())){
	      while (scanner.hasNextLine()){
	    	  TripAdvisorReview review= new TripAdvisorReview();
	    	  
	    	  	review = getTripAdvisorReview(scanner.nextLine());
	    	  	reviewList.add(review);
	    	  	//System.out.println(review.getTitle());
	      }
	      
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   DB.insertHotelReview(reviewList);
	    
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		hotelReviewCreate hReview = new hotelReviewCreate();
		DB.OpenConn();
		
		String fileFolder = "content/hotelReview";
		File fd = new File(fileFolder);
		String resourcefile[]=fd.list();   
		String[] fileName;
		  
        System.out.println(resourcefile.length+" files.");
       
         for(int i=0;i<resourcefile.length;i++)
         {           
        	 	fileName= resourcefile[i].split("\\.");
        	 	System.out.println(fileFolder+"/"+resourcefile[i]);
        	 	if(resourcefile[i].contains(".txt")){
        	 		hReview.insertHotelReview(fileFolder+"/"+resourcefile[i]);
        	 	}
         }

		DB.CloseConn();
		
	}
	

}
