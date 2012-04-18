package controllers.contestant;

import controllers.Security;
import java.io.ByteArrayInputStream;
import java.util.List;
import javax.persistence.Query;
import models.Contestant;
import models.Role;
import models.Solution;
import models.SolutionFile;
import models.User;
import play.data.validation.Min;
import play.db.jpa.JPA;
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

	public static void index(long competitionId, int page) {
		if(page < 1)
			page = 1;
		Contestant loggedUser = Contestant.getLoggedUser();
		List<Solution> solutions = models.Solution.find("competition=? AND user=? ORDER BY created DESC", renderArgs.get("competition"), loggedUser)
				.fetch(page, 30);
		
		Query countQuery = JPA.em().createQuery("SELECT COUNT(s) FROM Solution s WHERE s.competition=:competition AND s.user=:user");
		countQuery.setParameter("competition", renderArgs.get("competition"));
		countQuery.setParameter("user", loggedUser);
		long pages = Math.round(Math.ceil((Long)countQuery.getSingleResult() / 30.0));
		
		renderArgs.put("solutions", solutions);
		renderArgs.put("pages", pages);
		renderArgs.put("page", page);
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
