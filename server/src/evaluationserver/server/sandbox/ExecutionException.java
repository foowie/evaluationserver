package evaluationserver.server.sandbox;

public class ExecutionException extends Exception {

	public ExecutionException(Throwable thrwbl) {
		super(thrwbl);
	}

	public ExecutionException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

	public ExecutionException(String string) {
		super(string);
	}

	public ExecutionException() {
	}
}
