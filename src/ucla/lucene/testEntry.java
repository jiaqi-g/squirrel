package ucla.lucene;

public class testEntry {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		hotelReview hReview = new hotelReview();
		DB.OpenConn();
		hReview.insertHotelReview();
		DB.CloseConn();
	}

}
