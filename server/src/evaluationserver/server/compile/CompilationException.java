package evaluationserver.server.compile;

public class CompilationException extends Exception {

	public CompilationException(Throwable thrwbl) {
		super(thrwbl);
	}

	public CompilationException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

	public CompilationException(String string) {
		super(string);
	}

	public CompilationException() {
	}
}
