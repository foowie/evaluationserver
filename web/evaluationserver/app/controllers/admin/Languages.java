package controllers.admin;

import controllers.CRUD;
import controllers.Check;
import models.Role;
import play.mvc.With;

@Check(Role.Check.ADMIN)
@With({
	controllers.Secure.class,
	controllers.admin.with.CRUDSearch.class,
	controllers.admin.with.Menu.class
})
public class Languages extends CRUD {
	
}
