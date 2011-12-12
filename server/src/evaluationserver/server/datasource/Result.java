package evaluationserver.server.datasource;

import evaluationserver.server.execution.Reply;
import java.util.Date;

public class Result {

	private final Reply reply;
	private final Date start;
	private final int time;
	private final int memory;

	public Result(Reply reply, Date start, int time, int memory) {
		this.reply = reply;
		this.start = start;
		this.time = time;
		this.memory = memory;
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
}
