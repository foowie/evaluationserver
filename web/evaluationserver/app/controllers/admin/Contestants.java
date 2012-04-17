package controllers.admin;

import controllers.CRUD;
import controllers.Check;
import models.Contestant;
import models.Role;
import play.mvc.Before;
import play.mvc.With;

/**
 * CRUD controller for model Contestant
 * @author Daniel Robenek <danrob@seznam.cz>
 */
@Check(Role.Check.ADMIN)
@With({
	controllers.Secure.class,
	controllers.admin.with.CRUDSearch.class,
	controllers.admin.with.Menu.class
})
public class Contestants extends CRUD {

	/**
	 * Check for delete constraints
	 * @param id 
	 */
	@Before(only = {"delete"})
	public static void beforeDelete(Long id) {
		Contestant contestant = Contestant.findById(id);
		notFoundIfNull(contestant);
		if (!contestant.groups.isEmpty()) {
			flash.error("Can't delete contestant! Is in " + contestant.groups.size() + " team" + (contestant.groups.size() > 1 ? "s" : "") + ": " + contestant.groups.get(0).name + (contestant.groups.size() > 1 ? ", ..." : ""));
			redirect(request.controller + ".show", id);
		}
	}
}
