package controllers.admin;

import controllers.CRUD;
import controllers.Check;
import models.Role;
import play.mvc.Before;
import play.mvc.With;

@Check(Role.Check.ADMIN)
@With({
	controllers.Secure.class,
	controllers.admin.with.CRUDSearch.class,
	controllers.admin.with.Menu.class
})
public class Solutions extends CRUD {

	@Before(only = "list")
	public static void sortList() {
		if(!params._contains("orderBy")) {
			params.put("orderBy", "id");
			params.put("order", "DESC");
		}
	}
	
}
