package controllers.admin;

import controllers.CRUD;
import controllers.Check;
import models.ResolverFile;
import models.Role;
import play.mvc.Before;
import play.mvc.With;

/**
 * CRUD controller for model ResolverFile
 * @author Daniel Robenek <danrob@seznam.cz>
 */
@Check(Role.Check.ADMIN)
@With({
	controllers.Secure.class,
	controllers.admin.with.Files.class,
	controllers.admin.with.CRUDSearch.class,
	controllers.admin.with.Menu.class
})
public class ResolverFiles extends CRUD {

	/**
	 * Check for delete constraints
	 * @param id 
	 */
	@Before(only = {"delete"})
	public static void beforeDelete(Long id) {
		ResolverFile file = ResolverFile.findById(id);
		notFoundIfNull(file);
		if (!file.tasks.isEmpty()) {
			flash.error("Can't delete this file! Is used in " + file.tasks.size() + " task" + (file.tasks.size() > 1 ? "s" : "") + ": " + file.tasks.get(0).name + (file.tasks.size() > 1 ? ", ..." : ""));
			redirect(request.controller + ".show", id);
		}
	}
}
