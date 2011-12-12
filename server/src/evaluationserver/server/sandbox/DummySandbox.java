package evaluationserver.server.sandbox;

import java.util.Date;
import java.util.logging.Logger;

public class DummySandbox implements Sandbox {
	private static final Logger logger = Logger.getLogger(DummySandbox.class.getPackage().getName());	

	public DummySandbox() {
	}
	
	@Override
	public ExecutionResult execute(Solution solution) {
		return new ExecutionResult(null, new Date(), 50, 100, 1000);
	}
}
