package controllers.contestant;

import models.Role;
import play.mvc.Controller;
import play.mvc.With;

@controllers.Check(Role.Check.CONTESTANT)
@With({
	controllers.Secure.class, 
	controllers.contestant.with.Competition.class, 
	controllers.contestant.with.Menu.class
})
public class Tasks extends Controller {

	public static void index(long competitionId) {
		render();
	}	
	
}
