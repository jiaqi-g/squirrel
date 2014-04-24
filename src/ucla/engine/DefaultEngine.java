package ucla.engine;

import java.io.IOException;

public class DefaultEngine extends AbstractEngine {

	@Override
	public String search(String url) throws IOException {
		try {
			return Parse.callDiffBot(url);
			//return Parse.callBoiler(url);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}

	@Override
	public String name() {
		return "Default Engine";
	}
}
