package evaluationserver.server.sandbox;

public class NoSandboxException extends Exception {

	public NoSandboxException(Throwable thrwbl) {
		super(thrwbl);
	}

	public NoSandboxException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

	public NoSandboxException(String string) {
		super(string);
	}

	public NoSandboxException() {
	}
}
