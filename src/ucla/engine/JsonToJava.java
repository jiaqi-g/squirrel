package ucla.engine;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class DiffBotObj {
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

public class JsonToJava {

	public static String test(String jsonObj) throws IOException {
		Gson gson = new GsonBuilder().create();
		DiffBotObj p = gson.fromJson(jsonObj, DiffBotObj.class);
		return p.toString();
	}
}
