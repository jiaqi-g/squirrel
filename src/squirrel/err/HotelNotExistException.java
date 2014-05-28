package squirrel.err;

@SuppressWarnings("serial")
public class HotelNotExistException extends RuntimeException {
	String msg;
	
	public HotelNotExistException(String msg) {
		this.msg = msg;
	}
	
	public String getMessage() {
		return msg;
	}
}