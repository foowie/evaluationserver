package controllers.contestant;

import models.Role;
import play.mvc.Controller;
import play.mvc.With;

@controllers.Check(Role.Check.CONTESTANT)
@With({
	controllers.Secure.class, 
	CompetitionCheck.class, 
	TaskCheck.class, 
	Menu.class
})
public class Task extends Controller {
	
	public static void index(long competitionId, long taskId) {
		render();
	}
	
}
