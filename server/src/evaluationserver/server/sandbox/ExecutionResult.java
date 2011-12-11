package evaluationserver.server.sandbox;

import java.util.Date;

public class ExecutionResult {

	private final String resultKey;
	private final Date start;
	private final Date stop;
	private final int memory;

	public ExecutionResult(String resultKey, Date start, Date stop, int memory) {
		this.resultKey = resultKey;
		this.start = start;
		this.stop = stop;
		this.memory = memory;
	}

	public int getMemory() {
		return memory;
	}

	public String getResultKey() {
		return resultKey;
	}

	public Date getStart() {
		return start;
	}

	public Date getStop() {
		return stop;
	}
}
