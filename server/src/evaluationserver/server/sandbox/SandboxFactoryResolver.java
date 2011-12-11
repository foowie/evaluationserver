package evaluationserver.server.sandbox;

public interface SandboxFactoryResolver {
	SandboxFactory getSandboxFactory(String languageKey) throws NoSandboxFactoryException;
}
