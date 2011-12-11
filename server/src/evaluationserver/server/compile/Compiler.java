package evaluationserver.server.compile;

import java.io.File;

public interface Compiler {
	
	void compile(File source, File destination) throws CompilationException;
}
