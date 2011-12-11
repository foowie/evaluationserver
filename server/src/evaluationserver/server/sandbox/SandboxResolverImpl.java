package evaluationserver.server.sandbox;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SandboxResolverImpl implements SandboxResolver {
	private static final Logger logger = Logger.getLogger(SandboxResolverImpl.class.getPackage().getName());	

	private final Map<String, Sandbox> factories;

	public SandboxResolverImpl(Map<String, Sandbox> factories) {
		this.factories = factories;
	}

	@Override
	public Sandbox getSandbox(String languageKey) throws NoSandboxException {
		if (factories.containsKey(languageKey)) {
			logger.log(Level.FINER, ("Sandbox factory found for language key " + languageKey));
			return factories.get(languageKey);
		}
		throw new NoSandboxException("Sandbox factory not found for language key " + languageKey);
	}
}
