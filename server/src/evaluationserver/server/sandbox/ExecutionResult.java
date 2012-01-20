package evaluationserver.server.sandbox;

import evaluationserver.server.execution.Reply;
import java.util.Date;

public class ExecutionResult {

	private final Reply reply;
	private final Date start;
	private final int time;
	private final int memory;
	private final int output;
	private final String log;

	public ExecutionResult(Reply reply, Date start, int time, int memory, int output, String log) {
		this.reply = reply;
		this.start = start;
		this.time = time;
		this.memory = memory;
		this.output = output;
		this.log = log;
	}

	public int getMemory() {
		return memory;
	}

	public int getOutput() {
		return output;
	}

	/**
	 * Result of execution. If set (except of AC), means error
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

	public String getLog() {
		return log;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ExecutionResult other = (ExecutionResult) obj;
		if (this.reply != other.reply) {
			return false;
		}
		if (this.start != other.start && (this.start == null || !this.start.equals(other.start))) {
			return false;
		}
		if (this.time != other.time) {
			return false;
		}
		if (this.memory != other.memory) {
			return false;
		}
		if (this.output != other.output) {
			return false;
		}
		if ((this.log == null) ? (other.log != null) : !this.log.equals(other.log)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		return hash;
	}
}
