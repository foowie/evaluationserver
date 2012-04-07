package evaluationserver.server.execution;

import evaluationserver.server.entities.Solution;
import evaluationserver.server.compile.CompilationException;
import evaluationserver.server.compile.CompilerResolver;
import evaluationserver.server.compile.NoCompilerException;
import evaluationserver.server.filemanagment.FileManager;
import evaluationserver.server.sandbox.ExecutionException;
import evaluationserver.server.sandbox.ExecutionResult;
import evaluationserver.server.sandbox.Sandbox;
import evaluationserver.server.sandbox.SandboxResolver;
import evaluationserver.server.datasource.DataSource;
import evaluationserver.server.compile.Compiler;
import evaluationserver.server.datasource.Result;
import evaluationserver.server.inspection.Inspector;
import evaluationserver.server.inspection.InspectionException;
import evaluationserver.server.inspection.InspectionResult;
import evaluationserver.server.sandbox.NoSandboxException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SolutionWorker extends Thread {

	private static final Logger logger = Logger.getLogger(SolutionWorker.class.getPackage().getName());
	protected static final Object counterLock = new Object();
	protected static volatile long counter = 0;
	protected final DataSource dataSource;
	protected final SandboxResolver sandboxResolver;
	protected final CompilerResolver compilerResolver;
	protected final FileManager fileManager;
	protected final BlockingQueue<Solution> solutions;
	protected final Inspector inspector;
	protected long tag = -1;

	public SolutionWorker(DataSource dataSource, SandboxResolver sandboxResolver, CompilerResolver compilerResolver, FileManager fileManager, BlockingQueue<Solution> solutions, Inspector inspector) {
		this.dataSource = dataSource;
		this.sandboxResolver = sandboxResolver;
		this.compilerResolver = compilerResolver;
		this.fileManager = fileManager;
		this.solutions = solutions;
		this.inspector = inspector;
	}

	@Override
	public void run() {
		logger.log(Level.FINER, ("SolutionWorker '" + getName() + "' starts"));
		while (true) {
			synchronized(counterLock) {
				tag = counter++;
			}
			final Solution solution;
			try {
				solution = solutions.take();
				logger.log(Level.FINER, ("Taking solution number " + solution.getId()));
			} catch (InterruptedException ex) {
				logger.log(Level.FINER, ("Ending..."));
				return;
			}

			// compilation
			final File program;
			try {
				program = compile(solution);
			} catch (CompilationException ex) {
				// error during compilation
				logError(solution, Reply.COMPILE_ERROR, ex, Level.FINE, "Compilation error");
				continue;
			} catch (NoCompilerException ex) {
				logError(solution, Reply.INTERNAL_ERROR, ex, Level.SEVERE, "Compilation error - no compiler found for language '" + solution.getLanguage().getKey());
				continue;
			} catch (IOException ex) {
				logError(solution, Reply.INTERNAL_ERROR, ex, Level.SEVERE, "Compilation error - IOException");
				continue;
			}
			
			// file for solution of data
			final File solutionData;
			try {
				solutionData = fileManager.createFile(tag);
			} catch (IOException ex) {
				logError(solution, Reply.INTERNAL_ERROR, ex, Level.SEVERE, "File manager error - IOException");
				continue;
			}

			// prepare solution
			final evaluationserver.server.sandbox.Solution sandboxSolution;
			try {
				sandboxSolution = new evaluationserver.server.sandbox.Solution(
					solution.getLanguage().getKey(),
					program,
					solution.getTask().getInputData() == null ? null : fileManager.createFile(solution.getTask().getInputData(), tag),
					solutionData,
					solution.getTask().getTimeLimit(),
					solution.getTask().getMemoryLimit(),
					solution.getTask().getOutputLimit());
			} catch (IOException ex) {
				logError(solution, Reply.INTERNAL_ERROR, ex, Level.SEVERE, "File manager error - IOException");
				continue;
			}

			// get sandbox
			final ExecutionResult executionResult;
			try {
				executionResult = execute(sandboxSolution);
			} catch (NoSandboxException ex) {
				logError(solution, Reply.INTERNAL_ERROR, ex, Level.SEVERE, "Sandbox error - no sandbox found for language '" + solution.getLanguage().getKey() + "'");
				continue;
			} catch (ExecutionException ex) {
				logError(solution, Reply.INTERNAL_ERROR, ex, Level.SEVERE, "Sandbox error - execution exception");
				continue;
			}
			if (executionResult.getReply() != null && executionResult.getReply() != Reply.ACCEPTED) {
				// error during execution sandbox
				final String log = "Error during sandbox execution, system reply: " + executionResult.getReply().getName();
				logger.log(Level.FINE, log);
				dataSource.setResult(solution, new Result(executionResult.getReply(), executionResult.getStart(), executionResult.getTime(), executionResult.getMemory(), (log + "\n" + executionResult.getLog())));
			} else {
				// sandbox successfully executed
				evaluationserver.server.inspection.Solution inspectionSolution = null;
				try {
					inspectionSolution = new evaluationserver.server.inspection.Solution(
					solutionData,
					sandboxSolution.getInputData(),
					solution.getTask().getOutputData() == null ? null : fileManager.createFile(solution.getTask().getOutputData(), tag),
					fileManager.createFile(solution.getTask().getResultResolver(), tag));
				} catch (IOException ex) {
					logError(solution, Reply.INTERNAL_ERROR, ex, Level.SEVERE, "File manager exception IO exception");
					continue;
				}
				if(!inspectionSolution.getEvaluationProgram().setExecutable(true)) {
					logError(solution, Reply.INTERNAL_ERROR, new IOException("Cannot set executable flat to " + inspectionSolution.getEvaluationProgram().getName()), Level.SEVERE, "Cannot set executable flag to inspection program on task " + solution.getTask().getId() + " !");
					continue;
				}

				final InspectionResult inspectionResult;
				try {
					inspectionResult = inspect(inspectionSolution);
				} catch (InspectionException ex) {
					logError(solution, Reply.INTERNAL_ERROR, ex, Level.SEVERE, "Inspection exception");
					continue;
				}
				dataSource.setResult(solution, new Result(inspectionResult.getReply(), executionResult.getStart(), executionResult.getTime(), executionResult.getMemory(), ""));
			}

			// remove temp files
			logger.log(Level.FINER, ("Cleanup files"));
			fileManager.releaseFiles(tag);
		}

	}

	protected void logError(Solution solution, Reply type, Exception ex, Level level, String log) {
		final String msg = log + "\n" + ex.getMessage();
		logger.log(level, msg);
		dataSource.setResult(solution, new Result(type, new Date(), 0, 0, msg));
		fileManager.releaseFiles(tag);
	}
	
	/**
	 * Compile solution source program into file
	 * @param solution Solution to compile
	 * @return Compiled program
	 * @throws NoCompilerException
	 * @throws IOException
	 * @throws CompilationException 
	 */
	protected File compile(Solution solution) throws NoCompilerException, IOException, CompilationException {
		logger.log(Level.FINER, ("Compiling program of language " + solution.getLanguage().getKey()));
		final Compiler compiler = compilerResolver.getCompiler(solution.getLanguage().getKey());
		File sourceCode = null, program = null;
		try {
			sourceCode = fileManager.createFile(solution.getFile(), tag);
			program = fileManager.createFile(tag);
			compiler.compile(sourceCode, program);
		} finally {
			fileManager.releaseFile(sourceCode);
		}
		return program;
	}

	/**
	 * Execute solution and return result of execution
	 * @param sandboxSolution
	 * @return
	 * @throws NoSandboxException
	 * @throws ExecutionException 
	 */
	protected ExecutionResult execute(evaluationserver.server.sandbox.Solution sandboxSolution) throws NoSandboxException, ExecutionException {
		logger.log(Level.FINER, "Executing sandbox");
		final Sandbox sandbox = sandboxResolver.getSandbox(sandboxSolution.getLanguageKey());
		final ExecutionResult result = sandbox.execute(sandboxSolution);
		return result;
	}

	/**
	 * Inspect solution by evaluator
	 * @param inspectionSolution
	 * @return
	 * @throws InspectionException 
	 */
	protected InspectionResult inspect(evaluationserver.server.inspection.Solution inspectionSolution) throws InspectionException {
		logger.log(Level.FINER, "Inspecting result");
		final InspectionResult inspectionResult = inspector.execute(inspectionSolution);
		return inspectionResult;
	}
}
