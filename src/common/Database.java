package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import squirrel.common.Conf;
import squirrel.common.Log;
import squirrel.nlp.ADJSet;
import squirrel.nlp.NP;
import squirrel.nlp.Sentence;
import squirrel.nlp.similarity.WordSimilarityResultList;
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
 * NounAdjs(HotelId Integer, ReviewId Long, SentenceId Integer, Noun String, Adjs String) // Adjs is a String of adjs seperated by ',' Eg. good,better,best
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

	protected static Connection conn;
	protected static Statement stmt;

	public static void OpenConn() throws Exception {
		Class.forName(Conf.db_driver);
		conn = DriverManager.getConnection(Conf.db_url, Conf.db_user, Conf.db_password);           
		
		if (conn != null && !conn.isClosed()) {                
			Log.log("DB connection successful");                 
			stmt = conn.createStatement();     
		}            
	}

	public static void CloseConn(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
					review.setOffering_id(rs.getInt("hotelId"));
				}
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		return review; 
	}

	/**
	 * TODO: no ranking currently when showing the actual reviews
	 * @param sentences
	 * @return
	 */
	public static List<TripAdvisorReview> getReviewTexts(List<Sentence> sentences) {
		StringBuilder sb = new StringBuilder();
		for (Sentence sent: sentences) {
			sb.append(sent.getReviewId());
			sb.append(",");
		}

		List<TripAdvisorReview> res = new ArrayList<TripAdvisorReview>();
		StringBuffer buf = new StringBuffer();
		ResultSet rs;
		buf.append(" select hotelId,reviewId, Title, Review from hotelReview where reviewId in ( "+ sb.substring(0, sb.length()-1) +")");

		try {
			synchronized (DB.class) {
				rs = stmt.executeQuery(buf.toString());    
				while (rs.next()) {
					TripAdvisorReview review = new TripAdvisorReview();
					review.setId(rs.getLong("reviewId"));
					review.setText(rs.getString("Review"));
					review.setTitle(rs.getString("Title"));
					review.setOffering_id(rs.getInt("hotelId"));
					res.add(review);
				}
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}

		return res;
	}

	/*
	public static List<Sentence> getRankedSentenceScores(String noun, String adj, Integer hotelId, Integer topK, Double simTh) {
		ArrayList<Sentence> sentList = new ArrayList<Sentence>(); 

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
		return null;
	}*/

	public static WordSimilarityResultList getSimilarityScoresOfNoun(String noun, Double simTh) {
		WordSimilarityResultList res = new WordSimilarityResultList(noun); 

		StringBuffer buf = new StringBuffer();        
		buf.append(" select word_y, sim from wordSim where word_x='" + noun + "' and sim > " + simTh);
		try {
			synchronized (DB.class) {
				ResultSet rs = stmt.executeQuery(buf.toString());    
				while (rs.next()) {
					res.add(rs.getString("word_y"), rs.getDouble("sim"));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return res;
	}
	
	public static List<Sentence> getAllReviewSentences(Integer hotelId) {
		ArrayList<Sentence> sentList = new ArrayList<Sentence>();
		StringBuffer buf = new StringBuffer();
		ResultSet rs;

		buf.append(" SELECT hr.hotelId, hr.reviewId, rs.sentId, rs.noun, rs.adj, st.sent from");
		
		buf.append(" hotelReview hr, review_sent rs, sent_cnt st where");
		buf.append(" st.review_id = rs.reviewId and st.sent_id = rs.sentId and hr.reviewId = rs.reviewId and hotelId=" + hotelId);

		//SELECT hr.hotelId, hr.reviewId, rs.sentId, rs.noun, rs.adj from hotelReview hr, review_sent rs where hr.reviewId = rs.reviewId and hotelId=93396 limit 20;
		//SELECT hr.hotelId, hr.reviewId, rs.sentId, rs.noun, rs.adj, st.sent from hotelReview hr, review_sent rs, sent_cnt st where st.review_id = rs.reviewId and st.sent_id = rs.sentId and hr.reviewId = rs.reviewId and hotelId=93396 limit 20;

		try {
			synchronized (DB.class) {
				rs = stmt.executeQuery(buf.toString());

				long prevReviewId = 0L;
				int prevSentId = 0;
				Sentence sent = null;

				while (rs.next()) {
					long reviewId = rs.getLong("reviewId");
					int sentId = rs.getInt("sentId");

					if (reviewId != prevReviewId || sentId != prevSentId) {
						//we only construct sentence when we process a new one
						sent = new Sentence(reviewId, sentId, rs.getString("sent"));
						sentList.add(sent);
					}

					NP np = new NP(rs.getString("noun"), new ADJSet(rs.getString("adj")));
					sent.addNP(np);

					prevReviewId = reviewId;
					prevSentId = sentId;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return sentList;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
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

		//List<SentenceScore> ssrList= getRankedSentenceScores("travel", "good", 80112, 100, -1.0);
		System.out.println("test getRankedSentenceScores");
		//for(SentenceScore ssScore: ssrList) System.out.println(ssScore.toString());

		System.out.println("test getSimilarityScoresOfWord");

		WordSimilarityResultList wsr = getSimilarityScoresOfNoun("travel", 0.5);
		wsr.toString();

		System.out.println("test getSimilarityScoresOfWord");
		List<Sentence> ss = getAllReviewSentences(80112);
		for(Sentence s:ss)System.out.println(s.toString());

		Database.CloseConn();		
	}
}

