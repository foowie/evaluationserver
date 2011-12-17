package evaluationserver.server.compile;

import evaluationserver.server.util.SystemCommand;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemCompiler implements Compiler {

	private static final Logger logger = Logger.getLogger(SystemCompiler.class.getPackage().getName());
	private final String command;
	private final String inputKey;
	private final String outputKey;
	private final SystemCommand systemCommand;

	public SystemCompiler(String command, String inputKey, String outputKey, SystemCommand systemCommand) {
		this.command = command;
		this.inputKey = inputKey;
		this.outputKey = outputKey;
		this.systemCommand = systemCommand;
	}

	public SystemCompiler(String command, String inputKey, String outputKey) {
		this(command, inputKey, outputKey, new SystemCommand());
	}

	public SystemCompiler(String command) {
		this(command, "%input%", "%output%");
	}

	@Override
	public void compile(File source, File destination) throws CompilationException {
		if (!source.exists()) {
			throw new CompilationException("Source file doesnt exists " + source.getAbsolutePath());
		}

		try {
			String cmd = this.formatCommand(source, destination);
			logger.log(Level.FINER, ("Start compilling file " + source.getAbsolutePath() + " ..."));
			
			if(systemCommand.exec(cmd) != 0)
				throw new CompilationException("Compilation process return value: " + systemCommand.getReturnCode() + "(" + systemCommand.getOutput() + ") (" + systemCommand.getError() + ")");
				
			logger.log(Level.FINEST, ("Compilator message: '" + systemCommand.getOutput() + "'"));

		} catch (InterruptedException ex) {
			throw new CompilationException(ex);
		} catch (IOException ex) {
			throw new CompilationException(ex);
		}
		logger.log(Level.FINER, ("Compiled successfully into " + destination.getAbsolutePath() + ""));
	}

	protected String formatCommand(File source, File destination) {
		return command.replace(inputKey, source.getAbsolutePath()).replace(outputKey, destination.getAbsolutePath());
	}
}
