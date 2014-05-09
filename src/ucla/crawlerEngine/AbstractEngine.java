package ucla.crawlerEngine;

import java.io.IOException;

public abstract class AbstractEngine {
	public abstract String search(String url) throws IOException;
	
	public abstract String name();
}
