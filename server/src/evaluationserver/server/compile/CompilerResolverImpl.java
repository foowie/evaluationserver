package evaluationserver.server.compile;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompilerResolverImpl implements CompilerResolver {
	private static final Logger logger = Logger.getLogger(CompilerResolverImpl.class.getPackage().getName());	
	
	private final Map<String, Compiler> compilers;

	public CompilerResolverImpl(Map<String, Compiler> compilers) {
		this.compilers = compilers;
	}
	
	@Override
	public Compiler getCompiler(String languageKey) throws NoCompilerException {
		if(compilers.containsKey(languageKey)) {
			Compiler compiler = compilers.get(languageKey);
			logger.log(Level.FINER, ("Compiler found for language key " + languageKey));
			return compiler;
		}
		
		throw new NoCompilerException("No compiler for language key " + languageKey);
	}
	
}
