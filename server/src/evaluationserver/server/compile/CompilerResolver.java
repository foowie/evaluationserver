package evaluationserver.server.compile;

public interface CompilerResolver {
	Compiler getCompiler(String languageKey) throws NoCompilerException;
}
