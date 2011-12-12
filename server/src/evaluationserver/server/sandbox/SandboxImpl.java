package evaluationserver.server.sandbox;

import evaluationserver.server.execution.Reply;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SandboxImpl implements Sandbox {

	private static final Logger logger = Logger.getLogger(SandboxImpl.class.getPackage().getName());
	private final String command;
	private final String inputDataKey;
	private final String programKey;
	private final String solutionDataKey;
	private final String timeLimitKey;
	private final String memoryLimitKey;
	private final String outputLimitKey;

	public SandboxImpl(String command, String programKey, String inputDataKey, String solutionDataKey, String timeLimitKey, String memoryLimitKey, String outputLimitKey) {
		this.command = command;
		this.inputDataKey = inputDataKey;
		this.programKey = programKey;
		this.solutionDataKey = solutionDataKey;
		this.timeLimitKey = timeLimitKey;
		this.memoryLimitKey = memoryLimitKey;
		this.outputLimitKey = outputLimitKey;
	}

	public SandboxImpl(String command) {
		this(command, "%program%", "%inputData%", "%solutionData%", "%timeLimit%", "%memoryLimit%", "%outputLimit%");
	}

	@Override
	public ExecutionResult execute(Solution solution) throws ExecutionException {
		logger.log(Level.FINEST, ("Sandbox start"));
		final Date start = new Date();
		final String cmd = this.prepareCommand(solution);
		Process exec;
		try {
			exec = Runtime.getRuntime().exec(cmd);
		} catch (IOException ex) {
			throw new ExecutionException("Error during sandbox execution", ex);
		}

		BufferedReader input = new BufferedReader(new InputStreamReader(exec.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while ((line = input.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException ex) {
			throw new ExecutionException("Error during sandbox processing", ex);
		}
		logger.log(Level.FINEST, ("Sandbox message: '" + sb.toString() + "'"));

		try {
			int exitVal = exec.waitFor();
			if (exitVal != 0) {
				throw new ExecutionException("Sandbox process return value: " + exitVal + "(" + sb.toString() + ")");
			}
		} catch (InterruptedException ex) {
			throw new ExecutionException(ex);
		}

		return createResult(sb.toString(), start);
	}

	protected ExecutionResult createResult(String data, Date start) throws ExecutionException {
		StringTokenizer st = new StringTokenizer(data);

		// reply
		final Reply reply;
		if(!st.hasMoreTokens())
			throw new ExecutionException("Missing system reply token in sandbox result + '" + data + "'");
		try {
			reply = Reply.fromCode(st.nextToken());
		} catch(IllegalArgumentException ex) {
			throw new ExecutionException("Invalid system reply in sandbox result + '" + data + "'");
		}

		// time
		if(!st.hasMoreTokens())
			throw new ExecutionException("Missing time token in sandbox result + '" + data + "'");
		int time = Integer.parseInt(st.nextToken());
		
		// memory
		if(!st.hasMoreTokens())
			throw new ExecutionException("Missing memory token in sandbox result + '" + data + "'");
		int memory = Integer.parseInt(st.nextToken());
		
		return new ExecutionResult(reply, start, time, memory, 0);
	}

	protected String prepareCommand(Solution solution) {
		return command.replace(programKey, solution.getProgram().getAbsolutePath()).replace(inputDataKey, solution.getInputData().getAbsolutePath()).replace(solutionDataKey, solution.getSolutionData().getAbsolutePath()).replace(timeLimitKey, Integer.toString(solution.getTimeLimit())).replace(memoryLimitKey, Integer.toString(solution.getMemoryLimit())).replace(outputLimitKey, Integer.toString(solution.getOutputLimit()));
	}
}
