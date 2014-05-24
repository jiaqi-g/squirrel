package ucla.lucene;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class reviewSentCreate {
	public static ArrayList<reviewSentBean> rsList = new ArrayList<reviewSentBean>(); 
	public static void packReviewSentBean(String fileName) throws IOException
	{
	    Path path = Paths.get(fileName);
	    String line="";
	    try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())){
	      while (scanner.hasNextLine()){
	    	   System.out.println((scanner.nextLine()));
	    	   splitSemiColon(scanner.nextLine());
	      }
	    }
	}
	
	public static void splitSemiColon(String line){
		String[] names = line.split(";");
		String[] nAdjs;
		String noun;
		String adjString;
		Long reviewId;
		Long sentId;
		if(names.length>2){
			reviewId=Long.parseLong(names[0].trim());
			sentId = Long.parseLong(names[1].trim());
			for(int i=2;i<names.length;i++){
				nAdjs=names[i].split("\\|");
				//titleString = reviewBean.getTitle().replace("'", "\\'");
				//reviewString= reviewBean.getText().replace("'", "\\'");
				noun = nAdjs[0].replace("'", "\\'");
				adjString = nAdjs[1].replace("'", "\\'");
				//System.out.println(reviewId+" "+sentId+" "+noun+" "+adjString);
				//System.out.println(names[i]);
				rsList.add(new reviewSentBean(reviewId, sentId,noun, adjString));
			}
			//System.out.println(line);
		}
	}
	
	public static void main(String[] args) throws IOException, SQLException {
		// TODO Auto-generated method stub
		DB.OpenConn();
		
		String fileFolder = "content/review_sent";
		File fd = new File(fileFolder);
		String resourcefile[]=fd.list();   
		String[] fileName;
		String[] hotelId;  
        System.out.println(resourcefile.length+" files.");
       
         for(int i=0;i<resourcefile.length;i++)
         {           
        	 	fileName= resourcefile[i].split("\\.");
        	 	System.out.println(fileFolder+"/"+resourcefile[i]);
        	 	if(resourcefile[i].contains(".txt")){
        	 		hotelId = fileName[0].split("_");
        	 		packReviewSentBean(fileFolder+"/"+resourcefile[i]);

        				DB.insertReviewSent(rsList, hotelId[1]);

        	 	}
         }

		DB.CloseConn();
		
	}

}
