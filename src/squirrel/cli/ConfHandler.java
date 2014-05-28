package squirrel.cli;

import squirrel.common.ConfUtil;
import squirrel.common.Log;

public class ConfHandler extends DefaultHandler  {

	String confName;
	String confValue;
	boolean isSuccess = false;

	public ConfHandler(String s) {
		super(s);
		super.checkArgs(2);

		String confArg = "";
		for (int i=1; i<args.length; i++) {
			if (args.equals(";") || args.equals(".")) {
				continue;
			}
			confArg += args[i];
		}

		String[] confArgs = confArg.split("=");
		if (confArgs.length != 2) {
			Log.cliArgError();
		}

		confName = confArgs[0].trim();
		confValue = confArgs[1].trim();
	}

	public void handle() throws Exception {
		isSuccess = ConfUtil.setConf(confName, confValue);
	}

	public void emitResult() {
		if (isSuccess) {
			System.out.println(confName + " = " + confValue);
		}
		else {
		}
	}
}