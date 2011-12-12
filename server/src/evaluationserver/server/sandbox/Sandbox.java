package evaluationserver.server.sandbox;

public interface Sandbox {
	ExecutionResult execute(Solution solution) throws ExecutionException;
}
