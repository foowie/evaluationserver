package controllers.admin;

import controllers.CRUD;
import controllers.Check;
import models.Role;
import models.User;
import play.mvc.Before;
import play.mvc.With;

@Check(Role.Check.ADMIN)
@With({
	controllers.Secure.class,
	controllers.admin.with.CRUDSearch.class,
	controllers.admin.with.Menu.class
})
public class Competitions extends CRUD {
	
	@Before(only="create")
	public static void beforeCreate() {
		params.put("object.creator.id", User.getLoggedUser().getId().toString());
	}	
	
}
