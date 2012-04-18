package controllers.contestant;

import controllers.contestant.with.Menu;
import models.Role;
import play.mvc.Controller;
import play.mvc.With;

/**
 * Help
 * @author Daniel Robenek <danrob@seznam.cz>
 */
@controllers.Check(Role.Check.CONTESTANT)
@With({
	controllers.Secure.class,
	Menu.class
})
public class Help extends Controller {

	public static void index(Long competitionId) {
		render();
	}
}
