package evaluationserver.server.sandbox;

public class NoSandboxFactoryException extends Exception {

	public NoSandboxFactoryException(Throwable thrwbl) {
		super(thrwbl);
	}

	public NoSandboxFactoryException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

	public NoSandboxFactoryException(String string) {
		super(string);
	}

	public NoSandboxFactoryException() {
	}
}
