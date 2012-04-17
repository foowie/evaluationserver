package controllers.admin;

import controllers.CRUD;
import controllers.Check;
import models.Admin;
import models.Role;
import play.mvc.Before;
import play.mvc.With;

/**
 * CRUD controller for model Admin
 * @author Daniel Robenek <danrob@seznam.cz>
 */
@Check(Role.Check.ADMIN)
@With({
	controllers.Secure.class,
	controllers.admin.with.CRUDSearch.class,
	controllers.admin.with.Menu.class
})
public class Admins extends CRUD {

	/**
	 * Check for delete constraints
	 * @param id 
	 */
	@Before(only = {"delete"})
	public static void beforeDelete(Long id) {
		Admin admin = Admin.findById(id);
		notFoundIfNull(admin);
		if (!admin.competitions.isEmpty()) {
			flash.error("Can't delete admin! Its creator of " + admin.competitions.size() + " competition" + (admin.competitions.size() > 1 ? "s" : "") + ": " + admin.competitions.get(0).name + (admin.competitions.size() > 1 ? ", ..." : ""));
			redirect(request.controller + ".show", id);
		}
		if (!admin.tasks.isEmpty()) {
			flash.error("Can't delete admin! Its creator of " + admin.tasks.size() + " task" + (admin.tasks.size() > 1 ? "s" : "") + ": " + admin.tasks.get(0).name + (admin.tasks.size() > 1 ? ", ..." : ""));
			redirect(request.controller + ".show", id);
		}
		if (admin.id == Admin.getLoggedUser().id) {
			flash.error("Yout can't delete yourself!");
			redirect(request.controller + ".show", id);
		}
	}
}
