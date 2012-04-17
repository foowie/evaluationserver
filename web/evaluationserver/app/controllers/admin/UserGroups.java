package controllers.admin;

import controllers.CRUD;
import controllers.Check;
import models.Role;
import models.UserGroup;
import play.mvc.Before;
import play.mvc.With;

@Check(Role.Check.ADMIN)
@With({
	controllers.Secure.class,
	controllers.admin.with.CRUDSearch.class,
	controllers.admin.with.Menu.class
})
public class UserGroups extends CRUD {

	/**
	 * Check for delete constraints
	 * @param id 
	 */
	@Before(only = {"delete"})
	public static void beforeDelete(Long id) {
		UserGroup team = UserGroup.findById(id);
		notFoundIfNull(team);
		if (!team.competitions.isEmpty()) {
			flash.error("Can't delete team! Is used in " + team.competitions.size() + " competition" + (team.competitions.size() > 1 ? "s" : "") + ": " + team.competitions.get(0).name + (team.competitions.size() > 1 ? ", ..." : ""));
			redirect(request.controller + ".show", id);
		}
	}
}
