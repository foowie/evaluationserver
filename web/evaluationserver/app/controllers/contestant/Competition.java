package controllers.contestant;

import controllers.contestant.with.Menu;
import models.Role;
import play.mvc.Controller;
import play.mvc.With;

@controllers.Check(Role.Check.CONTESTANT)
@With({
	controllers.Secure.class, 
	controllers.contestant.with.Competition.class, 
	Menu.class
})
public class Competition extends Controller {
	
	public static void index(long competitionId) {
		render();
	}
	
}
