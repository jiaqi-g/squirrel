package ucla.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import squirrel.parse.TripAdvisorReview;

public class BuildIndex {
	public static final String indexPath = "lucene_index";
	static Gson gson = new GsonBuilder().create();
	
	public static TripAdvisorReview getTripAdvisorReview(String jsonObj) {
		TripAdvisorReview p = gson.fromJson(jsonObj, TripAdvisorReview.class);
		return p;
	}

	private static void indexDocs(IndexWriter writer, String aFileName) throws Exception {
		
		
	    Path path = Paths.get(aFileName);
	    try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())){
	      while (scanner.hasNextLine()){
	    	  TripAdvisorReview review= new TripAdvisorReview();
	    	  
	    	  	review = getTripAdvisorReview(scanner.nextLine());
	    	  	
				Document doc = new Document();
				doc.add(new LongField("HotelID", review.getOffering_id(), Field.Store.YES));
				doc.add(new LongField("ReviewID",review.getId(),Field.Store.YES));
				doc.add(new StringField("Title", review.getTitle(), Field.Store.YES));
				doc.add(new TextField("Review",review.getText(),Field.Store.YES));
				System.out.println("add doc: "+doc.getField("Review"));
				if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
					System.out.println("adding " + review.getId());
					writer.addDocument(doc);
				} else {
					System.out.println("updating " + review.getId());
					//writer.updateDocument(doc);
				}
	      }
	      
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	}
	private static void buildIndex(String aFileName, String destDir) throws Exception {

		//Directory dir = FSDirectory.open(new File(indexPath));
		Directory dir = FSDirectory.open(new File(destDir));

		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_48);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_48, analyzer);

		boolean create = true;

		if (create) {
			iwc.setOpenMode(OpenMode.CREATE);
		} else {
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		}

		IndexWriter writer = new IndexWriter(dir, iwc);
		
		indexDocs(writer, aFileName);
	
		writer.close();
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String srcFolder = "content/hotelReview";
		String destDir = "content/lucene_index";
		File fd = new File(srcFolder);
		String resourcefile[]=fd.list();   
		String[] fileName;
		
		  
        System.out.println(resourcefile.length+" files.");
       
         for(int i=0;i<resourcefile.length;i++)
         {                   	 	 
        	 	System.out.println(srcFolder+"/"+resourcefile[i]);
        	 	if(resourcefile[i].contains("json")){   
        	 		fileName = resourcefile[i].split("\\.");
        	 	//	System.out.println("!"+srcFolder+"/"+resourcefile[i]);
        	 	//	System.out.println(destDir+"/"+fileName[0]+"/");
        	 		buildIndex(srcFolder+"/"+resourcefile[i], destDir+"/"+fileName[0]+"/");
        	 		
        	 	}
         }

	}

}
