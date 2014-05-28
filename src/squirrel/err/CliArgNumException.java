package squirrel.err;

@SuppressWarnings("serial")
public class CliArgNumException extends RuntimeException {
	String msg;
	
	public CliArgNumException(String msg) {
		this.msg = msg;
	}
	
	public String getMessage() {
		return msg;
	}
}