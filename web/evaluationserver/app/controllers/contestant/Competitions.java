package controllers.contestant;

import java.util.List;
import models.Competition;
import models.Contestant;
import models.Role;
import play.mvc.Controller;
import play.mvc.With;

@controllers.Check(Role.Check.CONTESTANT)
@With({
	controllers.Secure.class, 
	Menu.class
})
public class Competitions extends Controller {
	
	public static void index() {
		List<Competition> competitions = models.Competition.findContestantsCompetitions(Contestant.getLoggedUser());
		render(competitions);
	}
	
}
