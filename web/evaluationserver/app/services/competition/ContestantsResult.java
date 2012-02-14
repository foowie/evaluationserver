package services.competition;

import java.util.Map;
import models.Contestant;
import models.Task;

public class ContestantsResult implements Comparable<ContestantsResult> {

	private Contestant contestant;
	private Map<Task, Integer> solvedTasks;
	private long totalPenalization; // [ms]
	private int position;

	public ContestantsResult(Contestant contestant, Map<Task, Integer> solvedTasks, long totalPenalization) {
		this.contestant = contestant;
		this.solvedTasks = solvedTasks;
		this.totalPenalization = totalPenalization;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Contestant getContestant() {
		return contestant;
	}

	public Map<Task, Integer> getSolvedTasks() {
		return solvedTasks;
	}

	public long getTotalPenalization() {
		return totalPenalization;
	}

	public int getSolvedTaskCount() {
		return solvedTasks.size();
	}

	public int compareTo(ContestantsResult other) {
		return (int) (getSolvedTaskCount() == other.getSolvedTaskCount() ? getTotalPenalization() - other.getTotalPenalization() : other.getSolvedTaskCount() - getSolvedTaskCount());
	}
}
