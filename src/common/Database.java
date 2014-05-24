package common;

import java.util.ArrayList;
import java.util.List;

import squirrel.common.ReviewUtil;
import squirrel.nlp.Sentence;
import squirrel.nlp.similarity.WordSimilarityResultSet;
import squirrel.parse.ReviewList;
import squirrel.parse.TripAdvisorReview;

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
	
	public static void writeReviews() {
		//TODO: write reviews into db
		ReviewList reviewList = ReviewUtil.getReviews();
	}

	public static Integer getHotelId(String hotelName) {
		//TODO
		return 0;
	}
	
	public static TripAdvisorReview getReview(Long reviewId) {
		//TODO
		return new TripAdvisorReview();
	}
	
	/**
	 * core function
	 * @param noun
	 * @param adj
	 * @return
	 */
	public static List<Sentence> getRankedSentenceScores(String noun, String adj) {
		//TODO
		return new ArrayList<Sentence>();
	}
	
	public static WordSimilarityResultSet getSimilarityScoresOfWord(String word) {
		//TODO
		return new WordSimilarityResultSet(word);
	}
	
	public static List<Sentence> getAllReviewSentences(Integer hotelId) {
		//TODO
		
/*		Sentence sent = new Sentence(reviewId, sentenceId);
		NP np = new NP(noun, new ADJSet());
		sent.addNP(np);*/
		return new ArrayList<Sentence>();
	}
}