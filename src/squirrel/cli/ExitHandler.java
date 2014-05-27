package squirrel.cli;

public class ExitHandler extends DefaultHandler {
	
	public ExitHandler(String s) {
		super(s);
	}

	public void handle() {
		System.exit(0);
	}
}