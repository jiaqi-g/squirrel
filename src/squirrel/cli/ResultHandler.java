package squirrel.cli;

import common.Database;

import squirrel.err.CliArgNumException;
import squirrel.nlp.Sentence;
import squirrel.parse.Record;
import squirrel.parse.TripAdvisorReview;

public class ResultHandler extends DefaultHandler {
	private Integer index;
	private TripAdvisorReview review;
	private Record record;

	public ResultHandler(String s, Record record) {
		super(s);
		super.checkArgs(2);

		this.record = record;

		try {
			index = Integer.parseInt(args[1].trim());
		}
		catch (NumberFormatException e) {
			throw new CliArgNumException("Rank Index not valid");
		}
	}

	public void handle() {
		if (record != null) {
			Sentence sent = record.getRankedSentence(index-1);
			if (sent != null) {
				review = Database.getReview(sent.getReviewId());
			}
		}
	}

	public void emitResult() {
		if (review != null) {
			System.out.println();
			System.out.println("Title: " + review.getTitle().replace("?", "") + "\n");
			System.out.println("Text: " + review.getText());
		}
	}
}