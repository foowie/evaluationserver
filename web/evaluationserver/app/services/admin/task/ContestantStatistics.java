package services.admin.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import models.Contestant;
import models.Solution;
import models.Task;
import play.db.jpa.JPA;

public class ContestantStatistics {

	public static class ContestantResult {

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
	}

	public Collection<ContestantResult> getStatistics(Task task, int limit) {
		List query = JPA.em().createQuery(
				"SELECT u, MIN(s.timeLength) " +
				"FROM Solution s " +
					"JOIN s.systemReply sr " +
					"JOIN s.user u " +
				"WHERE s.task = :task " +
					"AND sr.accepting = true " +
				"GROUP BY u " +
				"ORDER BY s.timeLength"
		).setParameter("task", task)
				.setMaxResults(limit)
				.getResultList();

		List<ContestantResult> result = new ArrayList<ContestantResult>(limit);
		for (Object item : query) {
			result.add(new ContestantResult((Contestant)((Object[])item)[0], (Integer)((Object[])item)[1]));
		}
		return result;
	}
}