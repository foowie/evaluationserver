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

	protected final DataSource dataSource;
	protected final SandboxResolver sandboxResolver;
	protected final CompilerResolver compilerResolver;
	protected final FileManager fileManager;
	protected final BlockingQueue<Solution> solutions;
	protected final Inspector inspector;

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
					logger.log(Level.FINE, ("Compilation error " + e.getMessage()));
					dataSource.setResult(solution, new Result(Reply.COMPILE_ERROR, new Date(), 0, 0));
					continue;
				}

				// file for solution of data
				final File solutionData = fileManager.createFile();
				
				// prepare solution
				evaluationserver.server.sandbox.Solution sandboxSolution = new evaluationserver.server.sandbox.Solution(
					solution.getLanguage().getKey(),
					program,
					solution.getTask().getInputData() == null ? null : fileManager.createFile(solution.getTask().getInputData()),
					solutionData,
					solution.getTask().getTimeLimit(),
					solution.getTask().getMemoryLimit(),
					solution.getTask().getOutputLimit()
				);

				// get sandbox
				final ExecutionResult executionResult = execute(sandboxSolution);
				if(executionResult.getReply() != null && executionResult.getReply() != Reply.ACCEPTED) {
					// error during execution sandbox
					logger.log(Level.FINE, ("Error during sandbox execution, system reply: " + executionResult.getReply().getName()));
					dataSource.setResult(solution, new Result(executionResult.getReply(), executionResult.getStart(), executionResult.getTime(), executionResult.getMemory()));
				} else {
					// sandbox successfully executed
					evaluationserver.server.inspection.Solution inspectionSolution = new evaluationserver.server.inspection.Solution(
						solutionData, 
						sandboxSolution.getInputData(), 
						solution.getTask().getOutputData() == null ? null : fileManager.createFile(solution.getTask().getOutputData()),
						fileManager.createFile(solution.getTask().getResultResolver())
					);
					inspectionSolution.getEvaluationProgram().setExecutable(true);
					
					final InspectionResult inspectionResult = inspect(inspectionSolution);
					fileManager.releaseFile(inspectionSolution.getEvaluationProgram());
					fileManager.releaseFile(inspectionSolution.getOutputData());
					
					dataSource.setResult(solution, new Result(inspectionResult.getReply(), executionResult.getStart(), executionResult.getTime(), executionResult.getMemory()));
				}

				// remove temp files
				logger.log(Level.FINER, ("Cleanup files"));
				fileManager.releaseFile(solutionData);
				fileManager.releaseFile(program);
				fileManager.releaseFile(sandboxSolution.getInputData());
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
