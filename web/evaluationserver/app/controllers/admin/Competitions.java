package controllers.admin;

import controllers.CRUD;
import controllers.Check;
import java.util.Collection;
import models.Competition;
import models.Role;
import models.User;
import play.mvc.Before;
import play.mvc.Router;
import play.mvc.With;
import services.competition.ContestantsResult;
import services.competition.ContestantsStatistics;

/**
 * CRUD controller for model Competition
 * @author Daniel Robenek <danrob@seznam.cz>
 */
@Check(Role.Check.ADMIN)
@With({
	controllers.Secure.class,
	controllers.admin.with.CRUDSearch.class,
	controllers.admin.with.Menu.class
})
public class Competitions extends CRUD {

	@Before(only = "create")
	public static void beforeCreate() {
		// set current admin as creator of competition
		params.put("object.creator.id", User.getLoggedUser().getId().toString());
	}

	/**
	 * Show competition's statistics
	 * @param id 
	 */
	public static void statistics(Long id) {
		Competition competition = Competition.findById(id);
		if (competition == null) {
			redirect(Router.getFullUrl("admin.Competitions.list"));
		}
		ContestantsStatistics cs = new ContestantsStatistics();
		Collection<ContestantsResult> contestantsStatistics = cs.getStatistics(competition, true);
		render(contestantsStatistics);
	}
}
