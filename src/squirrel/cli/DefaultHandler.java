package squirrel.cli;

import squirrel.common.Log;

public class DefaultHandler implements Handler {
	protected String[] args;
	
	public DefaultHandler(String s) {
		args = s.split(" ");
	}
	
	public void handle() throws Exception {
		//do nothing
	}
	
	protected void checkArgs(int num) {
		if (args.length < num) {
			Log.cliArgError();
		}
	}
	
	public void emitResult() {
		//do nothing
	}
}