package common;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import squirrel.common.ReviewUtil;
import squirrel.parse.BasicSentence;
import squirrel.parse.ReviewList;
import squirrel.parse.SentenceScore;
import squirrel.parse.TripAdvisorReview;
import ucla.lucene.DB;
import ucla.lucene.hotelReviewCreate;

/**
 * Abstraction of Database, common utils for db access.
 * 
 * Table Schemas:
 * 
 * Base Tables
 * Hotel(HotelId Integer, HotelName String)
 * Review(ReviewId Long, ReviewTitle String, ReviewText String)
 * Sentence(ReviewId Long, SentenceId Integer, SentenceText String)
 * 
 * Supplementary Tables
 * NounSimilarity(Noun1 String, Noun2 String, Score Double)
 * NounAdjs(ReviewId Long, SentenceId Integer, Noun String, Adjs String) // Adjs is a String of adjs seperated by ',' Eg. good,better,best
 * 
 * 
 * Result Set:
 * 
 * QueryResult(ReviewId Long, SentenceId Integer, SentenceText String, ReviewText String, Score Double)

 * For a given hotel, and given Query(Noun, Adj), return a list of sentences that
 * 1. belongs to reviews of this hotel
 * 2. contains the Adj provided in its Adjs String
 * 3. with sentenceScore > 0.5 // currently sentenceScore is defined as: highest NounSimilarity Score of pairs (Query_Noun, Sentence_Noun) 
 * 4. ranked as Score from High to Low
 * 
 * 
 * Welcome any modifications if the schema design is hard for computing the result set.
 * 
 * @author victor
 *
 */
public class Database {

	public static Integer getHotelId(String hotelName) {
		Integer hotelId=0;
		
		StringBuffer buf = new StringBuffer();        
        ResultSet rs;     
      	buf.append(" select hotelId from hotel where hotelName ='"+hotelName+"'");
       
        try {
            synchronized (DB.class) {
            	 rs = stmt.executeQuery(buf.toString());    
            	 while (rs.next()) {
            		 hotelId = rs.getInt("hotelId");
            	 }
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
		return hotelId; 
	}
	
	public static TripAdvisorReview getReview(Long reviewId) {
		//TODO
		
		
		TripAdvisorReview review = new TripAdvisorReview();
		
		StringBuffer buf = new StringBuffer();        
        ResultSet rs;     
      	buf.append(" select hotelId,reviewId, Title, Review from hotelReview where reviewId ="+reviewId);
       
        try {
            synchronized (DB.class) {
            	 rs = stmt.executeQuery(buf.toString());    
            	 while (rs.next()) {
            		 review.setId(reviewId);
            		 review.setText(rs.getString("Review"));
            		 review.setTitle(rs.getString("Title"));
            		 review.setOffering_id(rs.getInt("reviewId"));
            		 
            	 }
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
		return review; 
	}
	
	/**
	 * core function
	 * @param noun
	 * @param adj
	 * @return
	 */
	public static List<SentenceScore> getRankedSentenceScores(String noun, String adj, Integer hotelId, Integer topK, Double simTh) {
		//TODO
		ArrayList<SentenceScore> sentList = new ArrayList<SentenceScore>(); 
		
		
		StringBuffer buf = new StringBuffer();        
        ResultSet rs;     
      	buf.append(" SELECT hr.hotelId, hr.reviewId, sc.sent_id,sc.sent, ws.sim ");
      	buf.append(" from hotelReview hr, review_sent rs, sent_cnt sc, ");
      	buf.append(" (select word_y, sim from wordSim where word_x='"+noun+"' and sim>"+simTh+") as ws");
      	buf.append(" where ");
      	buf.append(" hr.reviewId = rs.reviewId ");
      	buf.append(" and hr.reviewId = sc.review_Id ");
      	buf.append(" and rs.sentId = sc.sent_id ");
      	buf.append(" and hotelId="+hotelId);
      	buf.append(" and rs.adj='"+adj+"'");
      	buf.append(" and rs.noun= ws.word_y ");
      	buf.append(" order by ws.sim desc ");
      	buf.append(" limit 0,"+topK+";");
       
        try {
            synchronized (DB.class) {
            	 rs = stmt.executeQuery(buf.toString());    
            	 while (rs.next()) {
            		 BasicSentence bs=new BasicSentence(rs.getLong("reviewId"), rs.getInt("sent_id"), rs.getString("sent"));
            		 SentenceScore ss=new SentenceScore(bs, rs.getDouble("sim"));
            		 sentList.add(ss);
            	 }
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
		
		return sentList;
	}
	
	protected static Connection conn;
	protected static Statement stmt;
	public static void OpenConn() {
			
			String driver = "com.mysql.jdbc.Driver";

			String url=""; 
			
			String user="";        
			String password="";
			String strarr[];
			
		    Path path = Paths.get("content/config");
		    try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())){
		      while (scanner.hasNextLine()){
		    	    TripAdvisorReview review= new TripAdvisorReview();
		    	    strarr=scanner.nextLine().split("=");
		    	    if(strarr[0].equals("url")) url = strarr[1].trim();
		    	    else if(strarr[0].equals("user"))user = strarr[1].trim();
		    	    else if(strarr[0].equals("password"))password =strarr[1].trim();
		    	    else{}
		    	    //System.out.println()
		      }
		    } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {             
				Class.forName(driver);
				conn = DriverManager.getConnection(url,user, password);           
				                 
				if(conn != null && !conn.isClosed()) 
				    {                
					System.out.println("DB connection successful");                 
					stmt = conn.createStatement();     
					}                      
				
				} catch(ClassNotFoundException e) 
				{             
					System.out.println("Driver class is missing");             
					e.printStackTrace();         
				} catch(SQLException e) 
				{             
					e.printStackTrace();         
				} 

		}
	public static void CloseConn(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		hotelReviewCreate hReview = new hotelReviewCreate();
//		DB.OpenConn();
		//hReview.insertHotelReview();
		
//		DB.CloseConn();
		Integer hotelId;
		Database.OpenConn();
		hotelId = Database.getHotelId("hotel_90036");
		System.out.println("hotel Id:"+hotelId);
		Long rid = (long) 147058704;
		TripAdvisorReview tr= Database.getReview(rid);
		System.out.println(tr.toString());
		Database.CloseConn();
		
		
	}
}
