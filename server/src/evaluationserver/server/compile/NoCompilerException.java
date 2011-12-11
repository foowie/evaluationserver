package evaluationserver.server.compile;

public class NoCompilerException extends Exception {

	public NoCompilerException(Throwable thrwbl) {
		super(thrwbl);
	}

	public NoCompilerException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

	public NoCompilerException(String string) {
		super(string);
	}

	public NoCompilerException() {
	}
}
