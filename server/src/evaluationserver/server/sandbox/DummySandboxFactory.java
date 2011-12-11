package evaluationserver.server.sandbox;

import java.util.logging.Logger;

public class DummySandboxFactory implements SandboxFactory {
	private static final Logger logger = Logger.getLogger(DummySandboxFactory.class.getPackage().getName());	


	public DummySandboxFactory() {
	}
	
	
	@Override
	public Sandbox createSandbox(Solution solution) {
		return new DummySandbox(solution);
	}
	
}
