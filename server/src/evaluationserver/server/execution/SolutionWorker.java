package evaluationserver.server.execution;

import evaluationserver.server.entities.Solution;
import evaluationserver.server.entities.SystemReply;
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
import evaluationserver.server.sandbox.NoSandboxException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SolutionWorker extends Thread {
	private static final Logger logger = Logger.getLogger(SolutionWorker.class.getPackage().getName());	

	protected final DataSource dataSource;
	protected final SandboxResolver sandboxResolver;
	protected final CompilerResolver compilerResolver;
	protected final FileManager fileManager;
	protected final BlockingQueue<Solution> solutions;

	public SolutionWorker(DataSource dataSource, SandboxResolver sandboxResolver, CompilerResolver compilerResolver, FileManager fileManager, BlockingQueue<Solution> solutions) {
		this.dataSource = dataSource;
		this.sandboxResolver = sandboxResolver;
		this.compilerResolver = compilerResolver;
		this.fileManager = fileManager;
		this.solutions = solutions;
	}

	@Override
	public void run() {
		try {
			logger.log(Level.FINER, ("SolutionWorker '" + getName() + "' starts"));
			while (true) {
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
				} catch(CompilationException e) {
					// error during compilation
					dataSource.setResult(solution, new ExecutionResult(SystemReply.COMPILATION_ERROR, new Date(), new Date(), 0));
					continue;
				}

				// prepare solution
				evaluationserver.server.sandbox.Solution sandboxSolution = new evaluationserver.server.sandbox.Solution(
					solution.getLanguage().getKey(),
					program,
					solution.getTask().getInputData() == null ? null : fileManager.createFile(solution.getTask().getInputData()),
					solution.getTask().getOutputData() == null ? null : fileManager.createFile(solution.getTask().getOutputData()),
					fileManager.createFile(solution.getTask().getResultResolver()),
					solution.getTask().getTimeLimit(),
					solution.getTask().getMemoryLimit()
				);

				// get sandbox
				final ExecutionResult result = execute(sandboxSolution);
				dataSource.setResult(solution, result);


				// remove temp files
				logger.log(Level.FINER, ("Cleanup files"));
				fileManager.releaseFile(program);
				fileManager.releaseFile(sandboxSolution.getEvaluationProgram());
				fileManager.releaseFile(sandboxSolution.getInputData());
				fileManager.releaseFile(sandboxSolution.getOutputData());
				//todo:
			}

		} catch (Exception ex) {
			logger.log(Level.SEVERE, null, ex);
		}
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
			sourceCode = fileManager.createFile(solution.getFile());
			program = fileManager.createFile();
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
	 * @throws NoSandboxFactoryException
	 * @throws ExecutionException 
	 */
	protected ExecutionResult execute(evaluationserver.server.sandbox.Solution sandboxSolution) throws NoSandboxException, ExecutionException {
		logger.log(Level.FINER, "Executing sandbox");
		final Sandbox sandbox = sandboxResolver.getSandbox(sandboxSolution.getLanguageKey());
		final ExecutionResult result = sandbox.execute();
		return result;
	}
}
