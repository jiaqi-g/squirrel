package squirrel.cli;

import squirrel.common.Log;
import squirrel.parse.Record;

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
			Log.cliArgError("Too few arguments ");
		}
	}
	
	public void emitResult() {
		//do nothing
	}

	@Override
	public Record emitRecord() {
		return null;
	}
}