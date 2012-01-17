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
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 47 * hash + (this.reply != null ? this.reply.hashCode() : 0);
		hash = 47 * hash + (this.start != null ? this.start.hashCode() : 0);
		hash = 47 * hash + this.time;
		hash = 47 * hash + this.memory;
		hash = 47 * hash + this.output;
		return hash;
	}
	
	
}
