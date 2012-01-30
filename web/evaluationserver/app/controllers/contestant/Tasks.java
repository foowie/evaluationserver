package controllers.contestant;

import models.Role;
import play.mvc.Controller;
import play.mvc.With;

@controllers.Check(Role.Check.CONTESTANT)
@With({
	controllers.Secure.class, 
	CompetitionCheck.class, 
	Menu.class
})
public class Tasks extends Controller {

	public static void index(long competitionId) {
		render();
	}	
	
}
