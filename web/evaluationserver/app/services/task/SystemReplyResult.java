package services.admin.task;

public class SystemReplyResult {
	private String name;
	private long count;
	private double relativeCount;

	public SystemReplyResult(String name, long count, double relativeCount) {
		this.name = name;
		this.count = count;
		this.relativeCount = relativeCount;
	}

	public long getCount() {
		return count;
	}

	public String getName() {
		return name;
	}

	public double getRelativeCount() {
		return relativeCount;
	}
	
}
