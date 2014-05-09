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

	private static void indexDocs(IndexWriter writer) throws Exception {
		String aFileName = "docs/hotel_93396_review.txt";
		
	    Path path = Paths.get(aFileName);
	    try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())){
	      while (scanner.hasNextLine()){
	    	  TripAdvisorReview review= new TripAdvisorReview();
	    	  
	    	  	review = getTripAdvisorReview(scanner.nextLine());
	    	  	
				Document doc = new Document();

				doc.add(new LongField("ID",review.getId(),Field.Store.NO));
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
	private static void buildIndex() throws Exception {

		Directory dir = FSDirectory.open(new File(indexPath));
		

		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_48);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_48, analyzer);

		boolean create = true;

		if (create) {
			iwc.setOpenMode(OpenMode.CREATE);
		} else {
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		}

		IndexWriter writer = new IndexWriter(dir, iwc);
		indexDocs(writer);
	
		writer.close();
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		buildIndex();

	}

}
