package evaluationserver.server.inspection;

import evaluationserver.server.execution.Reply;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InspectorImpl implements Inspector {
	private static final Logger logger = Logger.getLogger(InspectorImpl.class.getPackage().getName());	

	private final String command;
	private final String inputDataKey;
	private final String outputDataKey;
	private final String resolverKey;
	private final String solutionDataKey;
	
	/**
	 * @param command Abstract command to execute
	 * @param inputDataKey Input data of task
	 * @param outputDataKey Output data of task
	 * @param resolverKey Resolver file
	 * @param solutionKey Solution of task
	 */
	public InspectorImpl(String command, String inputDataKey, String outputDataKey, String resolverKey, String solutionDataKey) {
		this.command = command;
		this.inputDataKey = inputDataKey;
		this.outputDataKey = outputDataKey;
		this.resolverKey = resolverKey;
		this.solutionDataKey = solutionDataKey;
	}

	public InspectorImpl(String command) {
		this(command, "%inputData%", "%outputData%", "%resolver%", "%solutionData%");
	}
	
	@Override
	public InspectionResult execute(Solution solution) throws InspectionException {
		logger.log(Level.FINEST, ("Inspection start"));
		final String cmd = this.prepareCommand(solution);
		Process exec;
		try {
			exec = Runtime.getRuntime().exec(cmd);
		} catch (IOException ex) {
			throw new InspectionException("Error during inspector execution", ex);
		}
		
		BufferedReader input = new BufferedReader(new InputStreamReader(exec.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while ((line = input.readLine()) != null)
				sb.append(line);
		} catch (IOException ex) {
			throw new InspectionException("Error during inspector processing", ex);
		}
		logger.log(Level.FINEST, ("Inspection message: '" + sb.toString() + "'"));

		try {
			int exitVal = exec.waitFor();
			if (exitVal != 0) {
				throw new InspectionException("Inspection process return value: " + exitVal + "(" + sb.toString() + ")");
			}
		} catch (InterruptedException ex) {
			throw new InspectionException(ex);
		}		
		
		try {
			final Reply reply = Reply.fromCode(sb.toString());
			return new InspectionResult(reply);
		} catch (IllegalArgumentException ex) {
			throw new InspectionException("Invalid system reply '" + sb.toString() + "'", ex);
		}
	}
	
	protected String prepareCommand(Solution solution) {
		return command
				.replace(inputDataKey, solution.getInputData().getAbsolutePath())
				.replace(outputDataKey, solution.getOutputData() == null ? "" : solution.getOutputData().getAbsolutePath())
				.replace(resolverKey, solution.getEvaluationProgram().getAbsolutePath())
				.replace(solutionDataKey, solution.getSolutionData().getAbsolutePath());
	}
	
}
