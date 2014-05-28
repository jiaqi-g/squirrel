package squirrel.cli;

import squirrel.parse.Record;

public interface Handler {
	public void handle() throws Exception;
	public void emitResult();
	public Record emitRecord();
}