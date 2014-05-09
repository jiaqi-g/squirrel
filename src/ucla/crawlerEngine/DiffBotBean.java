package ucla.crawlerEngine;

public class DiffBotBean {
	//private String url;
	//private String title;
	//private String text;
	private String html;

	// Getters and setters are not required for this example.
	// GSON sets the fields directly using reflection.
	@Override
	public String toString() {
		return html;
	}
}
