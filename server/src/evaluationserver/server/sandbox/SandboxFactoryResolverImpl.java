package evaluationserver.server.sandbox;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SandboxFactoryResolverImpl implements SandboxFactoryResolver {
	private static final Logger logger = Logger.getLogger(SandboxFactoryResolverImpl.class.getPackage().getName());	

	private final Map<String, SandboxFactory> factories;

	public SandboxFactoryResolverImpl(Map<String, SandboxFactory> factories) {
		this.factories = factories;
	}

	@Override
	public SandboxFactory getSandboxFactory(String languageKey) throws NoSandboxFactoryException {
		if (factories.containsKey(languageKey)) {
			logger.log(Level.FINER, ("Sandbox factory found for language key " + languageKey));
			return factories.get(languageKey);
		}
		throw new NoSandboxFactoryException("Sandbox factory not found for language key " + languageKey);
	}
}
