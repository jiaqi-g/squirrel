package squirrel.cli;

import squirrel.parse.TripAdvisorReview;

public class ResultHandler extends DefaultHandler {
	private Integer index;
	private TripAdvisorReview review;
	
	public ResultHandler(String s) {
		super(s);
		super.checkArgs(2);
		
		try {
			index = Integer.parseInt(args[1].trim());
		}
		catch (NumberFormatException e) {
			throw new CliArgNumException();
		}
	}

	public void handle() {
		// TODO: query db to get the result
	}
	
	public void emitResult() {
		// System.out.println(review.getText());
	}
}