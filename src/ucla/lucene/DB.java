package ucla.lucene;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import squirrel.parse.TripAdvisorReview;

public class DB {
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
	
	public static void insertHotelReview(ArrayList<TripAdvisorReview> reviewList ) throws SQLException{
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
   }

}
