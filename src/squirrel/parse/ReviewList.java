package squirrel.parse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



/**
 * Use Hashmap for default implementation, could be modified to list + binary search, since append only
 * 
 * @author victor
 *
 */
public class ReviewList implements Iterable<TripAdvisorReview> {
	private static Map<Long, TripAdvisorReview> reviews;
	
	public ReviewList() {
		reviews = new HashMap<Long, TripAdvisorReview>();
	}
	
	public void add(TripAdvisorReview review) {
		reviews.put(review.getId(), review);
	}
	
	public TripAdvisorReview get(Long reviewId) {
		return reviews.get(reviewId);
	}

	@Override
	public Iterator<TripAdvisorReview> iterator() {
		return reviews.values().iterator();
	}
}
