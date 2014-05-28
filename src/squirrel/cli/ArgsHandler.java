package squirrel.cli;

import squirrel.common.ConfUtil;

public class ArgsHandler extends DefaultHandler {
	
	public ArgsHandler(String s) {
		super(s);
	}

	public void handle() throws Exception {
		ConfUtil.printArgs();
	}
}