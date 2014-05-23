package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import squirrel.common.ReviewUtil;
import squirrel.parse.ReviewList;
import squirrel.parse.SentenceScore;
import squirrel.parse.TripAdvisorReview;
import ucla.lucene.DB;

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
	
	public static void writeReviews() {
		//TODO: write reviews into db
		
		ReviewList reviewList = ReviewUtil.getReviews();
		try {
			insertHotelReview(reviewList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Integer getHotelId(String hotelName) {
		int hotelId = 0;
		
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
            		 review.setText(rs.getString("Reviw"));
            		 review.setTitle(rs.getString("Title"));
            		 review.setOffering_id(rs.getInt("reviewId"));
            		 
            	 }
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
		return review; 
	}
	
	public static List<SentenceScore> getRankedSentenceScores(String noun, String adj) {
		//TODO
		return new ArrayList<SentenceScore>();
	}
	
	protected static Connection conn;
	protected static Statement stmt;
	public static void OpenConn() {
			
			String driver = "com.mysql.jdbc.Driver";

			String url = "jdbc:mysql://127.0.0.1:3306/test";
			
			String user = "root";         
			String password = "1234"; 
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
	

	public static void insertHotelReview(ReviewList reviewList ) throws SQLException{
			Statement s;
			String sqlinsert;
			String titleString;
			String reviewString;
			try{
				synchronized (DB.class) {
					s = conn.createStatement();
					for(TripAdvisorReview reviewBean:reviewList){
					titleString = reviewBean.getTitle().replace("'", "\\'");
					reviewString= reviewBean.getText().replace("'", "\\'");
					sqlinsert = " INSERT INTO hotelReview(hotelId, reviewId, Title, Review)" + 
					   			" VALUES ('" + reviewBean.getOfferingId() + "', '" + reviewBean.getId() + "', '"+ titleString+"', '"+ reviewString+"')";
					System.out.println(sqlinsert);
					s.executeUpdate(sqlinsert);
					}
				}
			}catch(SQLException ex){
				throw ex;
			}
	}
	
	
}
