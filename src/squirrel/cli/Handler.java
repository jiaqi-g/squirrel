package squirrel.cli;

public interface Handler {
	public void handle() throws Exception;
	public void emitResult();
}