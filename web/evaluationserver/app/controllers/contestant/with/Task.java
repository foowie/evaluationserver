package controllers.contestant;

import controllers.Security;
import play.mvc.Before;
import play.mvc.Controller;

public class TaskCheck extends Controller {
	
	@Before
	public static void checkTask(Long competitionId, Long taskId) {
		if(taskId == null)
			Security.onCheckFailed();
		
		models.Task task = models.Task.findById(taskId);
		if(task == null || !task.isInCompetition(competitionId))
			Security.onCheckFailed();
		
		renderArgs.put("task", task);
	}
	
}
