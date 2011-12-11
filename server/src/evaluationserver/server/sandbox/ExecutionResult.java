package evaluationserver.server.sandbox;

import java.util.Date;

public class ExecutionResult {

	private final String resultKey;
	private final Date start;
	private final int time;
	private final int memory;
	private final int output;

	public ExecutionResult(String resultKey, Date start, int time, int memory, int output) {
		this.resultKey = resultKey;
		this.start = start;
		this.time = time;
		this.memory = memory;
		this.output = output;
	}

	public int getMemory() {
		return memory;
	}

	public int getOutput() {
		return output;
	}

	/**
	 * Result of execution. If set, means error
	 * @return 
	 */
	public String getResultKey() {
		return resultKey;
	}

	public Date getStart() {
		return start;
	}

	public int getTime() {
		return time;
	}
}
