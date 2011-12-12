package evaluationserver.server.sandbox;

import evaluationserver.server.execution.Reply;
import java.util.Date;

public class ExecutionResult {

	private final Reply reply;
	private final Date start;
	private final int time;
	private final int memory;
	private final int output;

	public ExecutionResult(Reply reply, Date start, int time, int memory, int output) {
		this.reply = reply;
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
	public Reply getReply() {
		return reply;
	}

	public Date getStart() {
		return start;
	}

	public int getTime() {
		return time;
	}
}
