package squirrel.cli;

public class HelpHandler extends DefaultHandler {
	
	public HelpHandler(String s) {
		super(s);
	}

	public void handle() {
		System.out.println("Help:");
		System.out.println("find aspect/trait");
		System.out.println("set confName = confValue");
		System.out.println("view resultNum");
		System.out.println("help");
		System.out.println();
		System.out.println("Type exit/quit to exit the system.");
	}
	
}