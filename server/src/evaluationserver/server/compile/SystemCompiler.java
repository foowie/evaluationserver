package evaluationserver.server.compile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemCompiler implements Compiler {
	private static final Logger logger = Logger.getLogger(SystemCompiler.class.getPackage().getName());	

	private final String command;
	private final String inputKey;
	private final String outputKey;

	public SystemCompiler(String command, String inputKey, String outputKey) {
		this.command = command;
		this.inputKey = inputKey;
		this.outputKey = outputKey;
	}

	public SystemCompiler(String command) {
		this(command, "%input%", "%output%");
	}

	@Override
	public void compile(File source, File destination) throws CompilationException {
		try {
			String cmd = command.replaceAll(inputKey, source.getAbsolutePath()).replaceAll(outputKey, destination.getAbsolutePath());
			logger.log(Level.FINER, ("Start compilling file " + source.getAbsolutePath() + " ..."));
			Process exec = Runtime.getRuntime().exec(cmd);
			BufferedReader input = new BufferedReader(new InputStreamReader(exec.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = input.readLine()) != null)
				sb.append(line);
			logger.log(Level.FINEST, ("Compilator message: '" + sb.toString() + "'"));
			
			try {
				int exitVal = exec.waitFor();
				if (exitVal != 0) {
					throw new CompilationException("Compilation process return value: " + exitVal + "(" + sb.toString() + ")");
				}
			} catch (InterruptedException ex) {
				throw new CompilationException(ex);
			}
		} catch (IOException ex) {
			throw new CompilationException(ex);
		}
		logger.log(Level.FINER, ("Compiled successfully into " + destination.getAbsolutePath() + ""));
	}
}
