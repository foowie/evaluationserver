package controllers.contestant.with;

import controllers.Security;
import play.mvc.Before;
import play.mvc.Controller;

/**
 * Task helper
 * @author Daniel Robenek <danrob@seznam.cz>
 */
public class Task extends Controller {

	/**
	 * Check user's authorization to this task
	 * @param competitionId
	 * @param taskId 
	 */
	@Before
	public static void checkTask(Long competitionId, Long taskId) {
		if (taskId == null) {
			Security.onCheckFailed();
		}

		models.Task task = models.Task.findById(taskId);
		if (task == null || !task.isInCompetition(competitionId)) {
			Security.onCheckFailed();
		}

		renderArgs.put("task", task);
	}
}
