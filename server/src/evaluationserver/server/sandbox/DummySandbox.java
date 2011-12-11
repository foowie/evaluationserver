package evaluationserver.server.sandbox;

import java.util.Date;
import java.util.logging.Logger;

public class DummySandbox implements Sandbox {
	private static final Logger logger = Logger.getLogger(DummySandbox.class.getPackage().getName());	

	protected final Solution solution;

	public DummySandbox(Solution solution) {
		this.solution = solution;
	}
	
	@Override
	public ExecutionResult execute() {
		return new ExecutionResult(null, new Date(), 50, 100, 1000);
	}
}
