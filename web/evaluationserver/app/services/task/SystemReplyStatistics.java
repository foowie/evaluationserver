package services.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import models.Task;
import play.db.jpa.JPA;

/**
 * Task's statistics grouped by system reply
 * @author Daniel Robenek <danrob@seznam.cz>
 */
public class SystemReplyStatistics {

	public Collection<SystemReplyResult> getStatistics(Task task) {
		List st = JPA.em().createQuery(
				"SELECT COUNT(s), sr.name "
				+ "FROM Solution s "
				+ "JOIN s.systemReply sr "
				+ "WHERE s.task = :task "
				+ "GROUP BY sr.id").setParameter("task", task).getResultList();

		int totalCount = 0;
		for (Object item : st) {
			totalCount += (Long) ((Object[]) item)[0];
		}

		List<SystemReplyResult> result = new ArrayList<SystemReplyResult>(st.size());
		for (Object item : st) {
			result.add(new SystemReplyResult((String) ((Object[]) item)[1], (Long) ((Object[]) item)[0], (Long) ((Object[]) item)[0] / (double) totalCount));
		}
		return result;
	}
}
