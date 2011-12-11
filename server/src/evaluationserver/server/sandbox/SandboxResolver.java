package evaluationserver.server.sandbox;

public interface SandboxResolver {
	Sandbox getSandbox(String languageKey) throws NoSandboxException;
}
