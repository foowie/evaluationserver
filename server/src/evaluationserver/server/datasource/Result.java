package evaluationserver.server.datasource;

import evaluationserver.server.execution.Reply;
import java.util.Date;

public class Result {

	private final Reply reply;
	private final Date start;
	private final int time;
	private final int memory;
	private final String log;

	public Result(Reply reply, Date start, int time, int memory, String log) {
		this.reply = reply;
		this.start = start;
		this.time = time;
		this.memory = memory;
		this.log = log;
	}

	public int getMemory() {
		return memory;
	}

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
}
