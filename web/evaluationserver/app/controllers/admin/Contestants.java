package controllers.admin;

import controllers.CRUD;
import controllers.Check;
import models.Contestant;
import models.Role;
import models.User;
import play.mvc.Before;
import play.mvc.With;

/**
 * CRUD controller for model Contestant
 * REQ-4.1, REQ-4.2, REQ-4.3
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
	 * Detect duplicite login
	 * @param object
	 * @param id 
	 */
	@Before(only = {"create", "save"})
	public static void beforeCreate(Contestant object, Long id) {
		boolean exists = User.loginExists(object.login, id);
		if(exists) {
			renderArgs.put("customerror", "This login allready exists!");
		}
		validation.equals(exists, false);
	}
	
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
