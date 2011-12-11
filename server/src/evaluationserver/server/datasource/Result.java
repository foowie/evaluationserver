package evaluationserver.server.datasource;

import java.util.Date;

public class Result {

	private final String resultKey;
	private final Date start;
	private final int time;
	private final int memory;

	public Result(String resultKey, Date start, int time, int memory) {
		this.resultKey = resultKey;
		this.start = start;
		this.time = time;
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

	public int getTime() {
		return time;
	}
}
