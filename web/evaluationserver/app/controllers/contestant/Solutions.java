package controllers.contestant;

import java.util.List;
import models.Contestant;
import models.Role;
import play.mvc.Controller;
import play.mvc.With;

@controllers.Check(Role.Check.CONTESTANT)
@With({
	controllers.Secure.class, 
	CompetitionCheck.class, 
	Menu.class
})
public class Solutions extends Controller {

	public static void index(long competitionId) {
		renderArgs.put("solutions", models.Solution.find("competition=? AND user=? ORDER BY created DESC", renderArgs.get("competition"), Contestant.getLoggedUser()).fetch());
		render();
	}	
	
}
