package ucla.lucene;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import squirrel.parse.TripAdvisorReview;

public class DB {
	protected static Connection conn;
	protected static Statement stmt;
	public static void OpenConn() {
			
			String driver = "com.mysql.jdbc.Driver";

			String url=""; 
			
			String user="";        
			String password="";
			String strarr[];
			
		    Path path = Paths.get("config");
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
	
	public static void insertHotelReview(ArrayList<TripAdvisorReview> reviewList ) throws SQLException{
			Statement s;
			String sqlinsert, sqldelete;
			String titleString;
			String reviewString;
			try{
				synchronized (DB.class) {
					s = conn.createStatement();
					sqldelete=" DELETE FROM hotelReview where hotelId="+reviewList.get(0).getOffering_id();
					s.execute(sqldelete);
					
					for(TripAdvisorReview reviewBean:reviewList){
					titleString = reviewBean.getTitle().replace("'", "\\'");
					reviewString= reviewBean.getText().replace("'", "\\'");
					sqlinsert = " INSERT INTO hotelReview(hotelId, reviewId, Title, Review)" + 
					   			" VALUES ('" + reviewBean.getOffering_id() + "', '" + reviewBean.getId() + "', '"+ titleString+"', '"+ reviewString+"')";
					System.out.println(sqlinsert);
					s.executeUpdate(sqlinsert);
					}
				}
			}catch(SQLException ex){
				throw ex;
			}
	}
	
	public static void insertReviewSent(ArrayList<reviewSentBean> reviewSentList, String hotelId ) throws SQLException{
		Statement s;
		String sqlinsert, sqldelete;
		String titleString;
		String reviewString;
		try{
			synchronized (DB.class) {
				s = conn.createStatement();
				sqldelete=" DELETE FROM review_sent where reviewId="+reviewSentList.get(0).getReviewId();
				s.execute(sqldelete);
				for(reviewSentBean reviewBean:reviewSentList){
				//titleString = reviewBean.getTitle().replace("'", "\\'");
				//reviewString= reviewBean.getText().replace("'", "\\'");
				sqlinsert = " INSERT INTO review_sent(reviewId, sentId, noun, adj)" + 
				   			" VALUES ('" + reviewBean.getReviewId() + "', '" + reviewBean.getSentId() + "', '"+ reviewBean.getNoun().trim()+"', '"+ reviewBean.getAdjString().trim()+"')";
				System.out.println(sqlinsert);
				s.executeUpdate(sqlinsert);
				}
			}
		}catch(SQLException ex){
			throw ex;
		}
	}
	
	public static void insertSentCnt(ArrayList<sentCntBean> reviewSentList ) throws SQLException{
		Statement s;
		String sqlinsert, sqldelete;
		String sentString;
		try{
			synchronized (DB.class) {
				s = conn.createStatement();
				if(reviewSentList.size()>0){
					sqldelete=" DELETE FROM sent_cnt where review_Id="+reviewSentList.get(0).getReviewId();
					s.execute(sqldelete);
	
					for(sentCntBean reviewSentBean:reviewSentList){
					sentString = reviewSentBean.getSent().replace("'", "\\'");
					sqlinsert = " INSERT INTO sent_cnt(review_id, sent_id,sent)";
					sqlinsert+= " VALUES ('" + reviewSentBean.getReviewId() + "', '" + reviewSentBean.getSentId() + "', '"+sentString+"')";
					System.out.println(sqlinsert);
					s.executeUpdate(sqlinsert);
				  }
				}
			}
		}catch(SQLException ex){
			throw ex;
		}
	}
	public static void insertWordSim(ArrayList<wordSimBean> wordSimList ) throws SQLException{
		Statement s;
		String sqlinsert;
		String wordX, wordY;
		try{
			synchronized (DB.class) {
				s = conn.createStatement();
				for(wordSimBean wordSimBean:wordSimList){
				wordX = wordSimBean.getWord_x().trim().replace("'", "\\'");
				wordY = wordSimBean.getWord_y().trim().replace("'", "\\'");
				sqlinsert = " INSERT INTO wordSim(word_x, word_y, sim)";
				sqlinsert+= " VALUES ('" + wordX + "', '" + wordY + "', '"+wordSimBean.getSim()+"')";
				System.out.println(sqlinsert);
				s.executeUpdate(sqlinsert);
				}
			}
		}catch(SQLException ex){
			throw ex;
		}
	}
	public int getTotalCount() throws SQLException{
		int totalcount = 0;
		
		StringBuffer buf = new StringBuffer();        
        ResultSet rs;     
        String VarValue;
      	buf.append(" select count(*) as cnt from systemvariables ");
       
        try {
            synchronized (DB.class) {
            	 rs = stmt.executeQuery(buf.toString());    
            	 while (rs.next()) {
            		 totalcount = rs.getInt("cnt");
            	 }
            }
        }catch (SQLException ex) {
            throw ex;
        }
		return totalcount; 
	}
	
	
	public static void main(String[] args) {

		DB.OpenConn();
		Date now = new Date();
		System.out.println(new java.text.SimpleDateFormat().format(now));
		System.out.println(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
   }

}
