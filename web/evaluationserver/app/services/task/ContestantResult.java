package services.task;

import models.Contestant;

/**
 * Statistics of single user in task
 * @author Daniel Robenek <danrob@seznam.cz>
 */
public class ContestantResult implements Comparable<ContestantResult> {

	private Contestant user;
	private int time;

	public ContestantResult(Contestant user, int time) {
		this.user = user;
		this.time = time;
	}

	public int getTime() {
		return time;
	}

	public Contestant getUser() {
		return user;
	}

	public int compareTo(ContestantResult t) {
		return time - t.time;
	}
}
