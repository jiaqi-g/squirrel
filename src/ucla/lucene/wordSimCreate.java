package ucla.lucene;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class wordSimCreate {
	public static ArrayList<wordSimBean> rsList = new ArrayList<wordSimBean>(); 
	public static void packWordSimBean(String fileFolder)
	{
		
		File fd = new File(fileFolder);
        Integer reviewId;
        int sentId=0;
        String[] fileName;
        String[] word_ySim;
        Path path;
       
        String word_x, word_y;
        Double sim;
        try{
                String resourcefile[]=fd.list();   
  
                System.out.println(resourcefile.length+" files.");
               
                 for(int i=0;i<resourcefile.length;i++)
                 {           
                	 	fileName= resourcefile[i].split("\\.");
                	 	word_x = fileName[0];
                	 	path = Paths.get(fileFolder+"/"+resourcefile[i]);
                	    
                	    try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())){
                	      while (scanner.hasNextLine()){
                	    	   //System.out.println((scanner.nextLine()));
                	    	   word_ySim = scanner.nextLine().split(",");
                	    	   word_y = word_ySim[0].trim();
                	    	   sim=Double.parseDouble(word_ySim[1]);
                	    	   rsList.add(new wordSimBean(word_x, word_y, sim));
                	    	  
                	      }
                	    }
                	   // for(wordSimBean scb: rsList){
                	    		DB.insertWordSim(rsList);
                	    	//	System.out.println(scb.toString());
                	    //}
                	    rsList.removeAll(rsList);
                	   
                 }
        }catch(Exception e){
        			e.printStackTrace();
        } 
  
	    
	}
	
	public static void main(String[] args) throws IOException, SQLException {
		// TODO Auto-generated method stub
		String fileFolder = "content/scores";
		DB.OpenConn();
		packWordSimBean(fileFolder);
		DB.CloseConn();
	}
}
