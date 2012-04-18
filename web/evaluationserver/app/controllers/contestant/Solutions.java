package controllers.contestant;

import controllers.Security;
import java.io.ByteArrayInputStream;
import models.Contestant;
import models.Role;
import models.SolutionFile;
import models.User;
import play.mvc.Controller;
import play.mvc.With;

/**
 * List of user's solutions
 * @author Daniel Robenek <danrob@seznam.cz>
 */
@controllers.Check(Role.Check.CONTESTANT)
@With({
	controllers.Secure.class,
	controllers.contestant.with.Competition.class,
	controllers.contestant.with.Menu.class
})
public class Solutions extends Controller {

	public static void index(long competitionId) {
		renderArgs.put("solutions", models.Solution.find("competition=? AND user=? ORDER BY created DESC", renderArgs.get("competition"), Contestant.getLoggedUser()).fetch());
		render();
	}

	public static void download(long competitionId, long fileId) {
		SolutionFile file = SolutionFile.findById(fileId);
		if (file == null || file.solution.competition.id != competitionId || file.solution.user.id != User.getLoggedUser().id) {
			Security.onCheckFailed();
		}
		renderBinary(new ByteArrayInputStream(file.data.data), file.name, file.size);
	}
}
