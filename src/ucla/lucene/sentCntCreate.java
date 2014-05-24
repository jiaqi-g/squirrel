package ucla.lucene;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sentCntCreate {
	public static ArrayList<sentCntBean> rsList = new ArrayList<sentCntBean>(); 
	public static void packSentCntBean(String fileFolder)
	{
		
		File fd = new File(fileFolder);
        Integer reviewId;
        int sentId=0;
        String[] fileName;
        Path path;
        String sent;
        try{
                String resourcefile[]=fd.list();   
  
                System.out.println(resourcefile.length+" files.");
               
                 for(int i=0;i<resourcefile.length;i++)
                 {           
                	 	fileName= resourcefile[i].split("_");
                	 	reviewId = Integer.parseInt(fileName[1]);
                	 	path = Paths.get(fileFolder+resourcefile[i]);
                	    
                	    try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())){
                	      while (scanner.hasNextLine()){
                	    	   //System.out.println((scanner.nextLine()));
                	    	   sent = scanner.nextLine().trim();
                	    	   if(!sent.equals(null))
                	    	   {
                	    		   rsList.add(new sentCntBean(reviewId, sentId, sent));
                	    		   sentId++;
                	    	   }
                	    	   
                	      }
                	    }
                	    //for(sentCntBean scb: rsList){
                	    		DB.insertSentCnt(rsList);
                	    //}
                	    rsList.removeAll(rsList);
                	    sentId=0;
                 }
        }catch(Exception e){
        			e.printStackTrace();
        } 
  
	    
	}
	
	public static void main(String[] args) throws IOException, SQLException {
		// TODO Auto-generated method stub
		
		DB.OpenConn();
		
		String fileFolder = "content/sent_cnt";
		File fd = new File(fileFolder);
		String resourcefile[]=fd.list();   
		String[] fileName;
		  
        System.out.println(resourcefile.length+" files.");
       
         for(int i=0;i<resourcefile.length;i++)
         {                   	 	 
        	 	System.out.println(fileFolder+"/"+resourcefile[i]+"/");
        	 	if(resourcefile[i].contains("hotel")){        	 		
        	 		packSentCntBean(fileFolder+"/"+resourcefile[i]+"/");
        	 	}
         }

		DB.CloseConn();
				
	}
}
